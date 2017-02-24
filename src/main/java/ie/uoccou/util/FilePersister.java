package ie.uoccou.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilePersister<Ob extends Serializable> implements Persister<Serializable>{

	private static final Logger logger = LoggerFactory.getLogger(FilePersister.class);
	
	private String filename;
	
	public void persist(Serializable ob) {

		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try
		{
			File file = new File(filename);
			
			if(!file.exists())
			{
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			out = new ObjectOutputStream(fos);
			out.writeObject(ob);
			out.close();
		}
		catch(IOException ex)
		{
			logger.info("There was a problem writing to file " + filename + ". Aborting save." );
		}
	}

	@SuppressWarnings("unchecked")
	public Ob restore()
	{
		Ob ob = null;
		try {
			File file = new File(filename);
			
			if(file.exists())
			{
				FileInputStream fis = null;
				ObjectInputStream in = null;
				fis = new FileInputStream(filename);
				in = new ObjectInputStream(fis);
				ob = (Ob)in.readObject();
				in.close();
			}
			else
			{
				logger.info(filename + " does not exist." );
			}
		} catch (Exception e) {
			logger.info("There was a problem reading from file " + filename + ". Aborting restoration." );
			
			File file = new File(filename);
			
			if(file.exists())
			{
				File newFile = new File(file.getAbsolutePath()+"."+System.currentTimeMillis());
				file.renameTo(newFile);
			}
		}
		return ob;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
}
