package ie.uoccou.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Delimited rfile record printer. Can set delimiter and filename. Prints String[], List<String>, String, char.
 * 
 * Null constructor instantiation assumes a default file name of @see {@link FileRecordPrinter.DEF_OUTPUT_FILE}.{datetime}.txt. See other contructors for variants.
 *  
 * @author ultan
 *
 */
public class DelimPrinter implements DelimFileRecordPrinter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private int nLines = 0;
	
	public DelimPrinter() {
		super();
		nLines=0;
		setDefaultOutputFileName();
		// TODO Auto-generated constructor stub
	}

	public void appendField(Object field, StringBuilder sb ) {
		if ( null != sb ){
			sb.append((String)field);
			sb.append(getDelim());
		}
	}
	
	
	public DelimPrinter(char delim, String outputFile) throws FileNotFoundException {
		super();
		this.delim = delim;
		nLines=0;
		setOutputFileName( outputFile );
		
		//setDefaultOutputFileName();
	}

	public DelimPrinter(String outputFile) throws FileNotFoundException {
		super();
		this.delim = COMMA;
		nLines=0;
		setOutputFileName( outputFile );
		
		//setDefaultOutputFileName();
	}

	private char delim = TAB;
//	public static final char COMMA = ',';
//	public static final char TAB = '\t';
//	public static final char NEWLINE = '\n';
//	public static final String DEF_OUTPUT_FILE="output.txt";
	private String outputFile = DEF_OUTPUT_FILE;
	
	//private boolean customOutput = false;
	
	
	private void setDefaultOutputFileName() {
		
		// TODO Auto-generated method stub
		//insert a timestamp before the suffix
		int pos = getOutputFileName().indexOf(".");
		if ( pos > -1){
			String basename = getOutputFileName().substring(0,pos);
			String suffix = getOutputFileName().substring(pos+1);
			String newPath = basename + 
					"." + 
					System.nanoTime() + 
					"." + 
					suffix;
			this.outputFile = newPath;
		}
			
	}
	

	/**
	 * write a string to the output file
	 * @see #output(String[])
	 * @see #output(List)
	 * @see #output(String)
	 * @see #output(char)
	 */
	public void output(String line){
		
		if  (null != line && line.length() > 0){
		
			try {
				//get an appending file
				File outf = new File(getOutputFileName());
			    FileWriter fw = new FileWriter( outf, true );
			    //buffer it up
			  //write
				BufferedWriter out = new BufferedWriter( fw );
				if ( line.charAt(line.length()-1)==getDelim() ){
					String s = line.substring(0,line.length()-1); 
					out.write(s);
				}
				else
					out.write(line);
				
			    out.write(NEWLINE);
			    out.close();
			    nLines++;
			    
			} catch (IOException e) {
				System.err.println("Couldnt write to output file : " + e.toString() );
			}
		}
		
		
	}
	/**
	 * Write a plain char the output file
	 * @see #output(String[])
	 * @see #output(List)
	 * @see #output(String)
	 * 
	 */
	public void output(char c){
		output(String.valueOf( c ));
	}
	
	/**
	 * answer the current file delimiter
	 *
	 * @return
	 */
	public char getDelim() {
		return delim;
	}

	/**
	 * set the delimeter to use in output file, default is {@link JDBCPrintable.TAB}
	 *
	 * @param delim
	 */
	public void setDelim(char delim) {
		this.delim = delim;
	}
	public String getOutputFileName() {
		return outputFile;
	}
	/**
	 * set the output file name and create the file - delete if it exists already. 
	 * If successful update the filename property, if not default name remains - output may still get created.
	 * 
	 *@param outputFile Fully qualified file name
	 */
	public void setOutputFileName(String outputFile) throws FileNotFoundException {
		if ( null != outputFile){
			
			try{
			
				File f = new File(outputFile);
				if ( !f.exists() ){
				
					f.createNewFile();
					
				}else{
					
					f.delete();
					f.createNewFile();
				}
				
				//if its all worked then set the filename, otherwise leave at default - that 
				//may still have a chance of success;
				this.outputFile = outputFile;
				//customOutput = true;
				nLines=0;
				
			} catch (FileNotFoundException fnfe){
				System.err.println("Couldnt find output file (" + outputFile + "), error : " + fnfe.toString() );
				throw fnfe;
			} 
			catch (Exception e){
				System.err.println("Couldnt create output file (" + outputFile + "), error : " + e.toString() );
			}
			
		}
		
		
	}


	
	public void printMessage(String msg) {
		output(msg);
		
	}

	//@Override
	/**
	 * @see #output(String[])
	 * 
	 * @see #output(String)
	 * @see #output(char)
	 */
	public void output(List<String> fields) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for(String key : fields){
			appendField(key,sb);
		}
		output(sb.toString() );
	}
	//@Override
/**
 * 
	 * @see #output(List)
	 * @see #output(String)
	 * @see #output(char)
 */
	public void output(String[] fields) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for(String field : fields){
			appendField(field,sb);
		}		
		output(sb.toString() );
	}

	public int getNumOutputLines() {
    	return nLines;
    }
}
