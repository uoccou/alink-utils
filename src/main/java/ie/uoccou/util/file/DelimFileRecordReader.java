package ie.uoccou.util.file;
import static ie.uoccou.util.file.FileRecordPrinter.COMMA;

import java.io.FileNotFoundException;
/**
 * allows reading a line of delimited data from input
 * each line should contain at least the number of fields in the first line, if the first line is a header
 * <p>
 * Note there is no header detection or constructor capability : header reading and expectation is up to the user of this class
 * If fields are 
 * @author ultan
 *
 */
public class DelimFileRecordReader extends FileRecordReader {

	
    private CSVParser csvParser = new CSVParser();
	
   
	public DelimFileRecordReader() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DelimFileRecordReader(String inputFile) throws FileNotFoundException {
		super( COMMA, inputFile);
		//init(COMMA);
		
	}
	private void init(char delim){
		if (COMMA == getDelim() ){
			csvParser = new CSVParser();
		}
	}
	public DelimFileRecordReader(char delim, String outputFile)throws FileNotFoundException  {
		super(delim, outputFile);
		//init(delim);
		// TODO Auto-generated constructor stub
	}
	public DelimFileRecordReader(String delim, String outputFile) throws FileNotFoundException {
		super(delim, outputFile);
		// TODO Auto-generated constructor stub
	}
	/**
	 * read a line of data
	 * 
	 * @return
	 */
	public String[] readFields(){
		
	
		String splitarray[] = null;
		String tmp = super.readLine();
		if (null != tmp && tmp.length() > 0){
			logger.debug("Parsing : " + tmp);
			
			if (COMMA == getDelim() ) {
				splitarray = csvParser.parse(tmp);
			} else {
				
				
				if ( null != tmp )
				 splitarray = tmp.split(String.valueOf(getDelim()));
			}	
		}
		
		return splitarray;
	}
	public void setQuoteResults(boolean isQuoted){
		this.csvParser.setQuoteResult(isQuoted);
	}
}
