package ie.uoccou.util.file;

import java.io.FileNotFoundException;

public interface FileRecordPrinter {
	
	public static final String DEF_OUTPUT_FILE="output.csv";
	
	public static final char COMMA = ',';
	public static final char TAB = '\t';
	public static final char NEWLINE = '\n';
	public static final char PIPE = '|';
	public void appendField(Object field, StringBuilder sb );
	
	public void output(String line);
	public void output(char c);
	/**
	 * return the FQ name of the output file
	 *
	 * @return
	 */
	public String getOutputFileName();
	/**
	 * set the name of the output file
	 *
	 * @param filename
	 * @throws FileNotFoundException 
	 */
	public void setOutputFileName(String filename) throws FileNotFoundException;
	/**
	 * output an arbitrary message 
	 *
	 * @param msg
	 */
	public void printMessage(String msg);
	//private String getOutputFileContent();
	public int getNumOutputLines();
}
