package ie.uoccou.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	//test comment, another
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	public static final int DEF_TIMEOUT=20000;
	public static final int DEF_TIMEOUT_MAX=120000;
	private int timeout = DEF_TIMEOUT;
	
	
	/**
	 * from: http://stackoverflow.com/questions/3584210/preferred-java-way-to-ping-an-http-url-for-availability
	 * 
	 * Pings a HTTP URL. This effectively sends a GET request and returns <code>true</code> if the response code is in 
	 * the 200-399 range.
	 * @param url The HTTP URL to be pinged.
	 * @param timeout The timeout in millis for both the connection timeout and the response read timeout. Note that
	 * the total timeout is effectively two times the given timeout.
	 * @return <code>true</code> if the given HTTP URL has returned response code 200-399 on a HEAD request within the
	 * given timeout, otherwise <code>false</code>.
	 */
	public boolean pingURL(String url, int timeout) {
	    //url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
		boolean rc = false;
		
	    try {
	    	if ( isHttpUrl(url) ) {
	    		
		        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		        
		        connection.setConnectTimeout(timeout);
		        connection.setReadTimeout(timeout);
		        //connection.setRequestMethod("HEAD");
		        int responseCode = connection.getResponseCode();
		        rc = (200 <= responseCode && responseCode <= 399);
	    	} else {
	    		//dodge testing and sending false response for non http-urls
	    		//@TODO factory for internet connection types
	    		rc = true;
	    	}
	    } catch (IOException e) {
	        logger.warn("IOException pinging URL("+url+") [" + e.toString() +"]");
	    } finally {

	    }
	    return rc;
	}
	
	
	
	private  boolean isHttpUrl(String url) {
		// TODO Auto-generated method stub
		boolean rc = false;
		if ( null != url && url.startsWith("http"))
			rc = true;
		return rc;
	}
	public  boolean pingURL(String url) {
		return pingURL( url, DEF_TIMEOUT );
	}



	public int getTimeout() {
		return timeout;
	}



	/**
	 * @TODO: test < max timeout
	 * @param timeout
	 */
	public void setTimeout(int timeout) {
		if ( timeout>0 && timeout <= DEF_TIMEOUT_MAX)
			this.timeout = timeout;
		else if ( timeout>0 && timeout >=DEF_TIMEOUT_MAX)
			this.timeout = DEF_TIMEOUT_MAX;
		else
			this.timeout = DEF_TIMEOUT;
			
	}



	
}
