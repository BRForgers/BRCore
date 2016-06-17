package cf.brforgers.core.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

/**
 * IO Helping classes
 * @author TheFreeHigh
 */
public class IOHelper {
	/**
	 * Get the content from a InputStream and outputs a String
	 * @param stream the InputStream going to be used
	 * @return Content from InputStream
	 * @throws IOException
	 */
	public static String toString(InputStream stream) throws IOException {
    	return IOUtils.toString(stream, "UTF-8");
    }
    
	/**
	 * Get the Content from a File and outputs a String
	 * @param file the File going to be read
	 * @return Content from File
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
    public static String toString(File file) throws FileNotFoundException, IOException {
    	return toString(toStream(file));
    }
    
    public static InputStream toStream(File file) throws FileNotFoundException {
    	return new FileInputStream(file);
    }
    
    public static InputStream toStream(URL url) throws IOException {
    	URLConnection c = url.openConnection();
        c.setRequestProperty("User-Agent", System.getProperty("java.version"));
        c.connect();
        return c.getInputStream();
    }
    
    /**
     * Get the Content from a URL and outputs a String
     * @param url the URL to Get Content to
     * @return Content from the URL
     * @throws IOException
     */
    public static String toString(URL url) throws IOException {
        return toString(toStream(url));
    }
    
    public static String toString(String url) throws IOException
    {
    	return toString(new URL(url));
    }
}
