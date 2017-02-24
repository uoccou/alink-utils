package ie.uoccou.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpFileSender implements FileSender {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private int connectionTimeout = DEF_CONNECTION_TIMEOUT;
	private int connectionReadTimeout = DEF_CONNECTION_READ_TIMEOUT;
	private int connectionSocketTimeout = DEF_CONNECTION_SO_TIMEOUT;

	private String host;
	private String username = "ftp";
	private String password = "noreply@repair.com";
	private String path;
	private String remoteFilename=null;
	private String localPath;
	//private String localPath;


	
	public boolean send(String filename) {
		boolean rc = false;
		if (null != getHost()) {
			logger.info("About to ftp file '" + filename + "' to : " + getUsername() + "@" + getHost() + ":" + (null !=getPath() ? getPath()+"/" : "") +(null !=getRemoteFilename() ? getRemoteFilename() : "") );
			rc = sendFile(filename);
		} else {
			logger.warn("Not sending file : host not defined");
		}
		return rc;
	}

	
	public boolean read(String filename) {
		boolean rc = false;
		if (null != getHost()) {
			logger.info("About to read ftp '" + filename + "' from : " + getUsername() + "@" + getHost() + ":" + getPath() + (null !=getPath() ? "/" : "") + getRemoteFilename() );
			rc = readFile(filename);
		} else {
			logger.warn("Not reading file : host not defined");
		}
		return rc;
	}

	public boolean readDirectory(Filter ff) {
		boolean rc = true;
		if (null != getHost()) {
			//logger.info("About to read ftp '" + filename + "' from : " + getUsername() + "@" + getHost() + ":" + getPath() + (null !=getPath() ? "/" : "") + getRemoteFilename() );
			
			String[] fileList = readDirectoryFiles();
			
			if(ff!=null)
			{
				
				List<String> files = new ArrayList<String>();
				if(fileList!=null)
				{
					for(String file : fileList)
					{
						if(ff.accept(file))
						{
							files.add(file);
						}
					}
				}
				
				fileList = files.toArray(new String[]{});
			}
			
			
			
			if(fileList!=null && fileList.length > 0)
			{
				rc = accessFile(fileList, false);
			}
		
		} else {
			logger.warn("Not reading file : host not defined");
			rc = false;
		}
		return rc;
	}

	private String[] readDirectoryFiles() {
		String[] fileList = null;
		FTPClient client = new FTPClient();
		//if (null != filename) {
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {

			logger.debug("Setting client timeouts");
			client.setConnectTimeout( getConnectionTimeout()) ;
			client.setDataTimeout( getConnectionReadTimeout() );
			client.setDefaultTimeout( getConnectionSocketTimeout() );

			logger.debug("Connecting to " + getHost() );
			client.connect(getHost());
			//do this for phoenix.newcorp.com - otherwise will hang or give ACCEPT_TIMEOUT
			logger.debug("Setting binary passive transfer. "  );
			client.setFileType(FTPClient.BINARY_FILE_TYPE);
			client.enterLocalPassiveMode();

			//
			// When login success the login method returns true.
			//
			logger.debug("Using credentials : " + getUsername() + " / "
					+ getPassword());
			boolean login = client.login(getUsername(), getPassword());

			if (login) {
				logger.debug("Logged in OK ");

				logger.info("Reading directory : "
						+ getPath());
				logger.debug("Connection t/o : " + getConnectionTimeout() );
				logger.debug("Connection Read t/o : " + getConnectionReadTimeout() );
				logger.debug("Connection SO t/o : " + getConnectionSocketTimeout() );

				logger.debug("Changing to remote dir :  " + getPath() );

				boolean changedOk = true;
				if ( null != getPath() ){
					changedOk = client.changeWorkingDirectory(getPath());
				}

				logger.debug("Attempting to read directory." );
				if (changedOk) {
						fileList = client.listNames();

					if (fileList != null)
						logger.info("Read remote directory OK");
					else
						logger.error("Could not read remote directory " );
				} else {
					logger.error("Couldnt change to remote dir :" + getPath() );
					fileList = null;
				}
				//
				// When logout success the logout method returns true.
				//
				boolean logout = client.logout();
				if (logout) {
					// System.out.println("Logout from FTP server...");
					logger.debug("Logged out OK ");
				}
			} else {
				logger.error("Couldnt login to ftp server with credentials "
						+ getUsername() + " / " + getPassword());
			}

		} catch (Exception e) {
			logger.error("Problem during ftp directory read " + e.toString());
			// e.printStackTrace();
		} finally {
			try {
				if (null != fis)
					fis.close();

				if (null != fos)
					fos.close();
				//
				// Closes the connection to the FTP server
				//
				if (null != client)
					client.disconnect();
			} catch (IOException e) {
				logger.error("Could not disconnect from ftp server. ");
				// e.printStackTrace();
			}
		}
		return fileList;
	}

	private boolean accessFile(String[] filenames, boolean write) {
		boolean rc = false;
		FTPClient client = new FTPClient();
		if (null != filenames && filenames.length > 0) {
			FileInputStream fis = null;
			FileOutputStream fos = null;

			try {

				logger.debug("Setting client timeouts");
				client.setConnectTimeout( getConnectionTimeout()) ;
				client.setDataTimeout( getConnectionReadTimeout() );
				client.setDefaultTimeout( getConnectionSocketTimeout() );

				logger.debug("Connecting to " + getHost() );
				client.connect(getHost());
				//do this for phoenix.newcorp.com - otherwise will hang or give ACCEPT_TIMEOUT
				logger.debug("Setting binary passive transfer. "  );
				client.setFileType(FTPClient.BINARY_FILE_TYPE);
				client.enterLocalPassiveMode();

				//
				// When login success the login method returns true.
				//
				logger.debug("Using credentials : " + getUsername() + " / "
						+ getPassword());
				boolean login = client.login(getUsername(), getPassword());

				if (login) {
					logger.debug("Logged in OK ");

					logger.debug("Connection t/o : " + getConnectionTimeout() );
					logger.debug("Connection Read t/o : " + getConnectionReadTimeout() );
					logger.debug("Connection SO t/o : " + getConnectionSocketTimeout() );

					logger.debug("Changing to remote dir :  " + getPath() );

					boolean changedOk = true;
					if ( null != getPath() ){
						changedOk = client.changeWorkingDirectory(getPath());
					}

					logger.debug("Attempting to " + (write ? "store" : "retrieve") +" file. " );
					if (changedOk) {
						
						boolean success = true;
						for(String filename : filenames)
						{
							logger.info( (write ? "Sending" : "Retrieving") +" file : " + filename + " to path : "
									+ getPath());
							if(write)
							{
								fis = new FileInputStream(filename);
								String remoteFileName = getRemoteFilename()!=null ? getRemoteFilename() : filename;
								rc = client.storeFile(remoteFileName, fis);
							}
							else
							{
								String localFile = (getLocalPath() !=null ? getLocalPath()+File.separator : "") + filename;
								fos = new FileOutputStream(localFile);
								rc = client.retrieveFile(filename, fos);
							}
							
							if (rc)
								logger.info((write ? "Stored" : "Retrieved") +" remote file OK");
							else
							{
								success = false;
								logger.error("Could not " + (write ? "store" : "retrieve") +" remote file " );
							}
						}
						
						rc = success;
						
					} else {
						logger.error("Couldnt change to remote dir, cant " + (write ? "send" : "read") +" file : " + getPath() );
						rc = false;
					}
					//
					// When logout success the logout method returns true.
					//
					boolean logout = client.logout();
					if (logout) {
						// System.out.println("Logout from FTP server...");
						logger.debug("Logged out OK ");
					}
				} else {
					logger.error("Couldnt login to ftp server with credentials "
							+ getUsername() + " / " + getPassword());
				}

			} catch (Exception e) {
				logger.error("Problem during ftp file " + (write ? "send" : "read") +" : " + e.toString());
				// e.printStackTrace();
			} finally {
				try {
					if (null != fis)
						fis.close();

					if (null != fos)
						fos.close();
					//
					// Closes the connection to the FTP server
					//
					if (null != client)
						client.disconnect();
				} catch (IOException e) {
					logger.error("Could not disconnect from ftp server. ");
					// e.printStackTrace();
				}
			}
		} else {
			logger.error("Filename of file to send is null ! ");

		}
		return rc;
	}

	private boolean readFile(String filename)
	{
		return accessFile(new String[]{filename}, false);
	}

	private boolean sendFile(String filename) {
		return accessFile(new String[]{filename}, true);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemoteFilename() {
		return remoteFilename;
	}

	/**
	 * the filename (unqualified) of the file when sent to the remote location
	 * <p>
	 * this is combined with the path during the send process to place the file, perhaps in a subdirectory 
	 */
	public void setRemoteFilename(String remoteFilename) {
		this.remoteFilename = remoteFilename;
	}
	
	
	public boolean canLogin() {
		// TODO Auto-generated method stub
		FTPClient client = new FTPClient();
		boolean rc = false;
		try {

			client.connect(getHost());
			rc = client.login(getUsername(), getPassword());
		} catch  (Exception e){

		} finally {
			try{
				if (null != client)
					client.disconnect();
			}catch(Exception e){}
		}
		return rc;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getConnectionReadTimeout() {
		return connectionReadTimeout;
	}

	public void setConnectionReadTimeout(int connectionReadTimeout) {
		this.connectionReadTimeout = connectionReadTimeout;
	}

	public int getConnectionSocketTimeout() {
		return connectionSocketTimeout;
	}

	public void setConnectionSocketTimeout(int connectionSocketTimeout) {
		this.connectionSocketTimeout = connectionSocketTimeout;
	}
}