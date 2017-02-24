package ie.uoccou.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class PlainURIIDGenerator implements URIIDGenerator {

	public static final String DEF_URI_BASE = "http://uoccou";
	public static final String DEF_XML_REPLACE_SPACE_CHAR="_";	
	public static final String DEF_REPLACE_CHARS="[ \"\\[\\]]";
	public static final String DEF_ENCODING ="UTF-8";
	private String replaceTargetChars = DEF_REPLACE_CHARS;
	private String IDURIBase = DEF_URI_BASE;
	private String XmlReplaceChar = DEF_XML_REPLACE_SPACE_CHAR;
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	private String encoding = DEF_ENCODING;
	
	public PlainURIIDGenerator(){
		
	}
	public PlainURIIDGenerator(String uribase, String replacechar){
		setIDURIBase(uribase);
		setXmlReplaceChar(replacechar);
	}
	
	public String getId(String basename) {
		// TODO Auto-generated method stub
		return getId(basename, null);
	}
	
	public URI getUri(String principalName){
		return getUri(principalName, null);
	}
	
	public URI getUri(String principalName, String typename){
		
		String id = getId(principalName,typename);
		URI u = createUri(id);
		return u;
	}
	public String getId(String principalName, String typename){
		
		URI u = null;
		StringBuilder sb = new StringBuilder();
		
		//if ( !isURI( principalName ) ){
		
			String s = getIDURIBase().endsWith("/") || getIDURIBase().length()==0 || getIDURIBase().endsWith("#") ? getIDURIBase() : getIDURIBase()+"/";
			sb.append( s) ;
			
			if ( null != typename ){
				String t = typename.endsWith("/") || typename.length()==0 || typename.endsWith("#")? typename : typename +"/";
				sb.append( t);
			}
			
			
			String domainPrincipalName = getDomainPrincipalName(principalName);
			if ( null != domainPrincipalName && domainPrincipalName.length() > 0 ) {
				sb.append(domainPrincipalName);
				//u = getUri(sb);
				if ( _logger.isDebugEnabled())
					_logger.debug("Generated id : " +  sb.toString() );
				
			} else {
				if ( _logger.isErrorEnabled())
					_logger.debug("Couldnt generate ID principal name : " +  principalName );
				
			}
//		} else {
//			sb.append(principalName);
//		}
		
			
		
		return sb.toString();
	}
	private URI createUri(String sb) {
		URI u = null;
		try {
			u = new URI( sb.toString() );
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
//					if ( log.isErrorEnabled() )
//						log.error("Couldnt get URI for : " + sb.toString() );
		}
		return u;
	}
	public String getIDURIBase() {
		return IDURIBase;
	}
	public void setIDURIBase(String iDURIBase) {
		if (null != iDURIBase )
			IDURIBase = iDURIBase;
	}
	/**
	 * should use a cryptographic function here to create a digest of the id so that keys based on the id
	 * are unique and cant be decompiled to establish identity.
	 * @see http://en.wikipedia.org/wiki/Cryptographic_hash_function etc
	 * @param principalName
	 * @return
	 */
	public String getDomainPrincipalName(String principalName) {
		// TODO Auto-generated method stub
		Assert.notNull(principalName);
		String PROTOCOL_LOCATOR = "://";
		int pos = principalName.indexOf( PROTOCOL_LOCATOR );
		String pName = principalName;
		String encName = null;
//		if ( pos >0 )
//			pName = getSafeXMLString( principalName.substring( pos+PROTOCOL_LOCATOR.length() ) );
//		else
//			pName = getSafeXMLString( principalName );
		
		//if pname is like uoccou.wordpress.com#asdf leave as is, but if like uocou.wordpress.com attach a trailing /
		//find last / then look for # char in remainder
		String safePName = "";
		int lastPos = pName.lastIndexOf('/');
		if ( lastPos > -1 ){
			String remainder = pName.substring(lastPos); 
			safePName = pName;
			if (!(remainder.indexOf('#') >= 0) ){
				if ( !remainder.endsWith("/"))
					safePName = pName + "/";
			}
		} else {
			safePName = pName + "/";
		}
				
		try {
			encName = URLEncoder.encode(safePName, getEncoding() );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encName;
	}
	
	private boolean isURI(String name){
		Assert.notNull(name);
		boolean rc = false;
		
		String PROTOCOL_LOCATOR = "://";
		int pos = name.indexOf( PROTOCOL_LOCATOR );
		if ( pos > -1 ) rc = true;
		
		return rc ;
	}
	
	public String getSafeXMLString(String input){
		Assert.notNull(input);
		String s = input.replaceAll(getReplaceTargetChars(), getXmlReplaceChar() );
		return XML.simpleEscape(s);
	}
	

	public String getXmlReplaceChar() {
		return XmlReplaceChar;
	}

	public void setXmlReplaceChar(String xmlReplaceChar) {
		if ( null != xmlReplaceChar )
		XmlReplaceChar = xmlReplaceChar;
	}
	public String getReplaceTargetChars() {
		return replaceTargetChars;
	}
	public void setReplaceTargetChars(String replaceTargetChars) {
		this.replaceTargetChars = replaceTargetChars;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	

}
