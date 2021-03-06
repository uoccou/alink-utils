package ie.uoccou.util;
/**
 * Signature for those classes that transfer file content by various protocols to a destination.
 * 
 * @author ultan
 *
 */
public interface FileSender {

	public static final int DEF_CONNECTION_TIMEOUT=3000;
	public static final int DEF_CONNECTION_READ_TIMEOUT=10000;
	public static final int DEF_CONNECTION_SO_TIMEOUT=10000;
	
	public boolean send(String filename);
	public boolean read(String filename);
	public String getHost();
	public void setHost(String hostname);
	public String getUsername();
	public void setUsername(String user);
	/**
	 * Answer the remote path at which sent file should be stored
	 * @param remotePath
	 * @return
	 */
	public String getPath();
	/**
	 * Register the remote path at which sent file should be stored
	 * @param remotePath
	 * @return
	 */
	public void setPath(String remotePath);
	public String getPassword();
	public void setPassword(String pwd);
	public void setRemoteFilename(String rFile);
	public String getRemoteFilename();
	
	/**
	 * 
	 */
	public void setLocalPath(String localPath);
	
	/**
	 * 
	 */
	public String getLocalPath();
	
	public boolean canLogin();
	boolean readDirectory(Filter filePattern);
	
	public interface Filter
	{
		public boolean accept(String str);
	}
	
}
