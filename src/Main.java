import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		System.out.println("Start");
        FTPUploader ftpUploader = null;
        try {
			ftpUploader = new FTPUploader("sportsphotos.com", "jason", "jason");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if (ftpUploader != null){
	        File folder = new File("/home/jason/Desktop/photos/");
	        File[] listOfFiles = folder.listFiles();
	
	            for (int i = 0; i < listOfFiles.length; i++) {
	              if (listOfFiles[i].isFile()) {
	                File file = listOfFiles[i];
	    	        ftpUploader.uploadFile(file.getAbsolutePath(), file.getName(), "/");
	                System.out.println("Done");
              	}
        	}
        }
        ftpUploader.disconnect();
	}
}
