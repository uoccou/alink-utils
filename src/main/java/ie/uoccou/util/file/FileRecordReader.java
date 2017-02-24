package ie.uoccou.util.file;

import static ie.uoccou.util.file.FileRecordPrinter.TAB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class FileRecordReader {
	protected Logger logger = LoggerFactory.getLogger( getClass() );
	
	
	public FileRecordReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	private String delim = String.valueOf(TAB);
	private String inputFile = null; 
	//private File file = null;
	private BufferedReader readbuffer = null;
	public FileRecordReader(String delim, String inputFile) throws FileNotFoundException  {
		this.delim = delim;
		this.inputFile = inputFile;
		createReader();
		
	}
	public FileRecordReader(char delim, String inputFile) throws FileNotFoundException {
		
		this.delim = String.valueOf(delim);
		this.inputFile = inputFile;
		createReader();

		
	}
	
	private void createReader() throws FileNotFoundException{
		File f = new File(this.inputFile);
		FileReader reader = null;
		try {
			reader = new FileReader(this.inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error( "Cant find file : " + this.inputFile + " : " + e.toString() );
			throw e;
		}
		
		readbuffer = new BufferedReader( reader );
	}
	
	public String readLine(){
		String strRead = null;
		String tmp = null;
		try {
			if ( ( tmp=readbuffer.readLine() )!=null ){
				strRead = tmp;
//			String splitarray[] = strRead.split("\t");
//			String firstentry = splitarray[0];
//			String secondentry = splitarray[1];		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("Couldnt read line : " + e.toString() );
			e.printStackTrace();
		}
		return strRead;
	}
	
	public void cleanup(){
		
		try {
			readbuffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.warn("Couldnt close file : " + e.toString() );
		}
	}

	public char getDelim() {
		return delim.charAt(0);
	}

	public void setDelim(char delim) {
		this.delim = String.valueOf(delim);
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

}
