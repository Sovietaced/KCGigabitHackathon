import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.file.StandardWatchEventKinds;

import util.FormatLong;

class Main {

	static int THREADS = Runtime.getRuntime().availableProcessors();
	static ExecutorService executor = Executors.newFixedThreadPool(THREADS);
	static Login loginFrame;
	static Running runningFrame;
	static int filesUploaded = 0;
	static List<String> logs = new ArrayList<String>();
	static long bandwidth = 0;
	static String username;
	static String password;
	static String path;

	public static void main(String[] args) throws Exception {
		
		if(args.length > 0){
			THREADS = Integer.valueOf(args[0]);
		}
		System.out.println("Start");

		loginFrame = new Login();
		loginFrame.setVisible(true);
		while (loginFrame.isVisible()) {
			Thread.sleep(1000);
		}
		runningFrame = new Running();
		runningFrame.setVisible(true);

		listen(username, password, path);
	}

	public static void listen(String username, String password, String path) {

		Path dir = Paths.get(path);

		try {
			WatchService watcher = dir.getFileSystem().newWatchService();
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);

			for (;;) {
				WatchKey watchKey = watcher.take();

				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent event : events) {
					if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("Created: "
								+ event.context().toString());
						
						String filename = event.context().toString();
						if(!filename.contains(".tmp")){
						File file = new File(path + "/"
								+ event.context().toString());
						
						upload(file, username, password);
					}
						if (!watchKey.reset())
							break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	public static void upload(final File file, final String username,
			final String password) {
		executor.submit(new Callable<String>() {
			public String call() {
				FTPUploader ftpUploader = null;
				try {
					ftpUploader = new FTPUploader("sportsphotos.com", username,
							password);
					ftpUploader.uploadFile(file.getAbsolutePath(),
							file.getName(), "/");
					ftpUploader.disconnect();
					Running.lblFilesUploaded.setText("Files Uploaded : "
							+ String.valueOf(++filesUploaded));
					bandwidth += file.length();
					Running.lblBandwidth.setText("Bandwidth : "
							+ String.valueOf(FormatLong.formatBytes(bandwidth,
									true, false)));
					process("fileName=" + file.getName());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public static void process(String fileName) {
		URL url = null;
			try {
				url = new URL(
						"http://www.sportsphotos.com/desktopUploader/jsonTest/processPhoto.php?"
								+ fileName);
				url.openStream();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
