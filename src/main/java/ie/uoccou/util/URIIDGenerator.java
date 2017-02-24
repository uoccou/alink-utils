package ie.uoccou.util;

import java.net.URI;

public interface URIIDGenerator extends IDGenerator {

	public URI getUri(String basename);
	public URI getUri(String basename, String typename);
	public String getId(String basename);
	public String getId(String principalName, String typename);
	public String getSafeXMLString(String input);
	public String getDomainPrincipalName(String s);
	public String getXmlReplaceChar();
}
