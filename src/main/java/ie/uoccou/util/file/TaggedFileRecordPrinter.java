package ie.uoccou.util.file;


public interface TaggedFileRecordPrinter extends FileRecordPrinter {
	public void outputRaw(String line);
	public void outputTaggedText(String line, String tag);
	public void outputRow(char c);
	public void outputRow(String line);
	public void outputStartTable();
	public void outputEndTable();
	public void outputEndTag(String tag);
	public void outputParagraph(String content);
	public void appendHeaderField(Object field, StringBuilder sb ); 
}
