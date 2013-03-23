import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.file.StandardWatchEventKinds;

class Main {

	final static int THREADS = Runtime.getRuntime().availableProcessors();
    static ExecutorService executor = Executors.newFixedThreadPool(THREADS);
    
	public static void main(String[] args) throws Exception {
		System.out.println("Start");
	        File folder = new File("/home/jason/Desktop/photos/");
	        
	        Path dir = Paths.get("/home/jason/Desktop/photos/");
	        
	        try{
	        	WatchService watcher = dir.getFileSystem().newWatchService();
	        	dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
	        	
	        	for(;;){
		        	WatchKey watchKey = watcher.take();
		        	
		        	List<WatchEvent <?>> events = watchKey.pollEvents();
		        	for(WatchEvent event : events){
		        		 if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
		                     System.out.println("Created: " + event.context().toString());
		                     File file = new File(folder.getAbsolutePath() + "/" + event.context().toString());
		                     upload(file);
		                     if(!watchKey.reset())
		                    	 break;
		                 }
		        	}
	        	}
	        }
	        catch(Exception e){
	        	System.out.println("Error: " + e.toString());
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
					System.out.println("File Uploaded : " + file.getName());
        		} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
        	}
        });
	}
}
