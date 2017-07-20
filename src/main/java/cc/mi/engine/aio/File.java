package cc.mi.engine.aio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class File {
	private static final Charset DEFAULT_CHARSET = Charset.forName("utf-8");
	
	public File() {}
	
	private static String ajustDir(String fileDir) {
		return fileDir.replace("\\\\", "/");
	}
	
	
	/**
	 * 向目标文档写入文件
	 * 把filePath, 分开来成fileDir和fileName;
	 */
	public void writeString(String filePath, String str) {
		filePath = ajustDir(filePath);
		if (!fileExist(filePath)) {
			writeContent(filePath, str);
			return;
		}
		writeContent(filePath, str);
	}
	
	/**
	 * 
	 **/
	private void write(	String filePath, String content, boolean append) throws Exception {
		
		Path path = Paths.get(filePath);
		AsynchronousFileChannel asynchronousFileChannel = 
								AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
		
        Future<FileLock> featureLock = asynchronousFileChannel.lock();  
        FileLock lock = featureLock.get();
        if (lock.isValid()) {
        	long writeIndex = asynchronousFileChannel.size();
    		if (!append) {
    			writeIndex = 0L;
    			asynchronousFileChannel.truncate(writeIndex);
    		}
    		
    		byte[] bytes = content.getBytes(DEFAULT_CHARSET);
    		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
    		buffer.put(bytes);
    		buffer.flip();
    		bytes = null;
    		
            Future<Integer> featureWrite = asynchronousFileChannel.write(  
                    buffer, writeIndex);
            while (!featureWrite.isDone()) {
            }
            int written = featureWrite.get();
            log("I’ve written " + written + " bytes into "  
                    + path.getFileName() + " locked file!");
            log("current size is:" + asynchronousFileChannel.size());
            lock.release();  
        }
	}
	
	private void writeContent(String filePath, String content) {
		
		try {
			checkAndCreateFile(filePath);
			write(filePath, content, false);
        } catch (Exception e) {
	        e.printStackTrace();
        }
	}
	
	public String read(String filePath) throws Exception {
		
		Path path = Paths.get(filePath);
		AsynchronousFileChannel asynchronousFileChannel = 
								AsynchronousFileChannel.open(path);
		int bufferSize = (int) asynchronousFileChannel.size();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);

		Future<Integer> future = asynchronousFileChannel.read(buffer, 0);
		while (!future.isDone()) {
		}
		
		byte[] bytes = new byte[buffer.limit()];
		buffer.flip();
		buffer.get(bytes);
		buffer.clear();
		buffer = null;
		
		String string = new String(bytes, DEFAULT_CHARSET);
		bytes = null;
		return string;
	}
	
	private void checkAndCreateFile(String filePath) {
		
		Path path = Paths.get(filePath);
		try {
			if (!fileExist(filePath)) {
				Files.createFile(path);
			}
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	
	/** 这个只有在accessible 为true时才有用 */
	public boolean fileExist(String filePath) {
		
		Path path = Paths.get(filePath);
		boolean vist = false;
		if (isAccessible(path)) {
			vist = Files.exists(path,
                				new LinkOption[] { LinkOption.NOFOLLOW_LINKS }); 
		}
		else {
			vist = filePathExist(filePath);
		}
		
		return vist;
	}
	
	private boolean isAccessible(Path path) {
		
		return 	Files.isReadable(path) && 
				Files.isWritable(path) && 
				Files.isExecutable(path) && 
				Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS);
	}
	
	public boolean filePathExist(String filePath) {
		
		boolean vist = false;
		java.io.File file = new java.io.File(filePath);
		if (file.exists() && file.isFile()) {
			vist = true;
		}
		file = null;
		
		return vist;
	}

	public void deleteFile(String filePath) {
		Path path = Paths.get(filePath);
		try {
	        Files.deleteIfExists(path);
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
	
	
	private void log(Object msg) {
		
		System.out.println(msg);
	}
}
