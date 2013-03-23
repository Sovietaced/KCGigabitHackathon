import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	final static int THREADS = Runtime.getRuntime().availableProcessors();
    static ExecutorService executor = Executors.newFixedThreadPool(THREADS);
    
	public static void main(String[] args) throws Exception {
		System.out.println("Start");
	        File folder = new File("/home/jason/Desktop/photos/");
	        File[] listOfFiles = folder.listFiles();
	            for (int i = 0; i < listOfFiles.length; i++) {
	              if (listOfFiles[i].isFile()) {
	                File file = listOfFiles[i];
	                upload(file);
        	}
        }
	}
	
	public static void upload(final File file){
		executor.submit(new Callable<String>(){
        	public String call(){
        		FTPUploader ftpUploader = null;
        		try {
        			ftpUploader = new FTPUploader("sportsphotos.com", "jason", "jason");
					ftpUploader.uploadFile(file.getAbsolutePath(), file.getName(), "/" );
					ftpUploader.disconnect();
        		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "File Uploaded : " + file.getName();
        	}
        });
	}
}
