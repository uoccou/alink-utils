package ie.uoccou.util.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Like DelimPrinter but for html. Use various output methods to output tables, rows and cells.
 * getNumOutputLines returns number of lines in body of html - doesnt include header and footer if any
 * 
 * @author ultan
 *
 */
public class HtmlPrinter implements TaggedFileRecordPrinter {
	
	private char delim = TAB;
	private static final String TAG_ROW="tr";
	private static final String TAG_CELL_HEADER="th";
	private static final String TAG_CELL="td";
	private static final String TAG_TABLE="table";
	private static final String TAG_PARAGRAPH="p";
	private String outputFile = DEF_OUTPUT_FILE;
	private boolean customOutput = false;
	private int nLines = 0;
	
	public HtmlPrinter() {
		super();
		nLines=0;
		setDefaultOutputFileName();
		// TODO Auto-generated constructor stub
	}


	public HtmlPrinter(char delim, String outputFile) {
		super();
		this.delim = delim;
		nLines=0;
		setOutputFileName( outputFile );
		
		//setDefaultOutputFileName();
	}



	
	
	
	
	public void appendHeaderField(Object field, StringBuilder sb ) {
		if ( null != sb ){
			sb.append("<" + TAG_CELL_HEADER + ">");
			sb.append((String)field);
			sb.append("</" + TAG_CELL_HEADER + ">");
		}
	}
	public void appendField(Object field, StringBuilder sb ) {
		if ( null != sb ){
			sb.append((String)field);
			sb.append(getDelim());
		}
	}
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
	 * write a row (to an assummed existing table)
	 * @param line
	 */
	public void outputRow(String line){
		
		outputTaggedText(line,TAG_ROW);
		
//		try {
//			//get an appending file
//			File outf = new File(getOutputFileName());
//		    FileWriter fw = new FileWriter( outf, true );
//		    //buffer it up
//			BufferedWriter out = new BufferedWriter( fw );
//			//write
//			out.write("<tr>");
//		    out.write(line);
//		    out.write("</tr>");
//		    out.close();
//		    
//		} catch (IOException e) {
//			System.err.println("Couldnt write to output file : " + e.toString() );
//		}
		
		
	}
	/**
	 * write a row (to an assummed existing table)
	 * @param line
	 */
	public void outputRaw(String line){
		
		outputTaggedText(line,null);
		
//		try {
//			//get an appending file
//			File outf = new File(getOutputFileName());
//		    FileWriter fw = new FileWriter( outf, true );
//		    //buffer it up
//			BufferedWriter out = new BufferedWriter( fw );
//			//write
//			out.write("<tr>");
//		    out.write(line);
//		    out.write("</tr>");
//		    out.close();
//		    
//		} catch (IOException e) {
//			System.err.println("Couldnt write to output file : " + e.toString() );
//		}
		
		
	}
	/**
	 * write a tagged row (to an assummed existing table)
	 * @param line
	 */
	public void outputTaggedText(String line, String tag){
		
		
		
		try {
			//get an appending file
			File outf = new File(getOutputFileName());
		    FileWriter fw = new FileWriter( outf, true );
		    //buffer it up
			BufferedWriter out = new BufferedWriter( fw );
			//write
			if(null != tag){
			  out.write("<" + tag + ">");
			}
		    out.write(line);
		    if ( null != tag){
		      out.write("</" + tag +">");
		    }
		    out.close();
		    nLines++;
		    
		} catch (IOException e) {
			System.err.println("Couldnt write to output file : " + e.toString() );
		}
		
		
	}
	public void outputRow(char c){
		outputRow(String.valueOf( c ));
	}
	public void outputStartTable(){
		outputRaw("<table border='1'>");
	}
	public void outputEndTable(){
		outputEndTag(TAG_TABLE);
	}
	public void outputEndTag(String tag){
		outputRaw("</" + tag  + ">");
	}
	public void outputParagraph(String content){
		outputTaggedText(content,TAG_PARAGRAPH);
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
	public void setOutputFileName(String outputFile) {
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
				customOutput = true;
				
			} catch (Exception e){
				System.err.println("Couldnt create output file (" + outputFile + "), error : " + e.toString() );
			}
			
		}
		
		
	}

	public void createHtmlHeader() {
	// TODO Auto-generated method stub
	outputRaw("<html>");
	outputRaw("<head><title>Results</title></head>");
	outputRaw("<body>");
	//outputRaw("<table border='1'>");
	}
	public void createHtmlFooter() {
	// TODO Auto-generated method stub
	outputRaw("</body>");
	outputRaw("</html>");
	
	//outputRaw("<table border='1'>");
	}
	
	public void printMessage(String msg) {
		output(msg);
		
	}


	//@Override
	public void output(String line) {
		// TODO Auto-generated method stub
		outputRaw(line);
	}


	//@Override
	public void output(char c) {
		// TODO Auto-generated method stub
		output(String.valueOf( c ));
	}


	//@Override
    public int getNumOutputLines() {
	    // TODO Auto-generated method stub
	    return nLines;
    }
}
