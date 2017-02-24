package ie.uoccou.util;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
/**
 * MailSender for plain smtp mail hosts
 * <p>
 * See also {@link SmtpsMailSender}
 * @author ultan
 *
 */
public class SmtpMailSender implements MailSender {
	
	protected static final Logger logger = LoggerFactory.getLogger(SmtpMailSender.class);
	private String host ="localhost";
	private int port = 25;
	private String username;
	private String password;
	private String protocol;
	
	private boolean doAuth = false;
	private String mailDomain = "@newcorp.com";
	@Autowired
	private JavaMailSenderImpl mailSender;
	
	@PostConstruct 
	public void init(){
		if (null != mailSender ){
			if ( null != host )
				mailSender.setHost(host);

			if ( isDoAuth() ){
				if ( null != username )
					mailSender.setUsername(username);
				if ( null != password )
					mailSender.setPassword(password);
//			if ( null != mailDomain )
//				mailSender.setMailDomain(mailDomain);
				
				
			}
			
			mailSender.setPort( port );
			mailSender.setProtocol(getProtocol());
		}
	}
	
	public  String getHost() {
		return this.host;
	}

	
	public void setHost(String host) {
		this.host = host;
	}

	
	public  int getPort() {
		return this.port;
	}

	
	public  void setPort(int port) {
		this.port = port;
	}

	
	public String getUsername() {
		return username;
	}

	
	/**
	 * the From part of the message
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	
	public boolean send(final String recipients, final String from, final String subject, final String body)
			throws Exception {
		// TODO Auto-generated method stub
		return send(recipients,from, null,null,subject,body, null);
		
	}
	public boolean send(final String recipients, final String from, final String[] ccList, final String[] bccList, 
			final String subject, final String body, final MailService.AttachmentRef attachmentPath )
			throws Exception {
		// TODO Auto-generated method stub
		boolean rc = true;
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
		         public void prepare(MimeMessage mimeMessage) throws Exception {
		        	 	//mimeMessage.get
			            MimeMessageHelper message; 
			            //need multipart helper if there is an attachment
			            if (null != attachmentPath )
			            	message = new MimeMessageHelper(mimeMessage,true);
			            else
			            	message = new MimeMessageHelper(mimeMessage);
			            
			            message.setValidateAddresses(true);
			            message.setTo(recipients);
			            if ( null != ccList && ccList.length > 0 )
			            	message.setCc(ccList);
			            if ( null != bccList && bccList.length > 0 )
			            	message.setBcc(bccList);
			            
			            if ( null == from ){
			            	message.setFrom(getUsername()); 
			            	logger.debug("Set 'from' using mail config username '" + getUsername() + "'");
			            }
			            else{
			            	
			            	message.setFrom(from);
			            	logger.debug("Set 'from' using template fromAddr '" +from + "'");
			            }
			            
			            message.setSubject(subject);
			            message.setText(body, true);
			            //add attachement if a file is named
			            if ( null != attachmentPath ){
			            	
			            	    try{
			            	     // let's attach the infamous windows Sample file (this time copied to c:/)
			            	     FileSystemResource file = new FileSystemResource(new File(attachmentPath.sourceFileName));
			            	     String name = (null != attachmentPath.attachmentName ? 
			            	    		 attachmentPath.attachmentName : 
			            	    			 attachmentPath.sourceFileName.substring(attachmentPath.sourceFileName.lastIndexOf( System.getProperty("file.separator") )+1) );
			            	
			            	     if (file.exists() )
			            	    	 message.addAttachment(name, file);
			            	     else
			            	    	 logger.warn("Attachment doesnt exist : " + file.getFilename()  );
			            	     
			            	     } catch (Exception e){
			            	    	 logger.error("Problem adding attachment '" + attachmentPath + "' to email, continuing : " + e.toString() );
			            	     }
			                
			            }
			         }
			      };
			      
		      
	    	  this.mailSender.send(preparator);
	      } catch (Exception e){
	    	  logger.error("Couldnt prepare or send email msg : " + e.toString() );
	    	  rc = false;
	      }
		return rc;
	}
	public String getMailDomain() {
		return mailDomain;
	}
	public void setMailDomain(String mailDomain) {
		this.mailDomain = mailDomain;
	}

	public boolean isDoAuth() {
		return doAuth;
	}

	public void setDoAuth(boolean doAuth) {
		this.doAuth = doAuth;
	}

	public String getProtocol() {
    	return protocol;
    }

	public void setProtocol(String protocol) {
    	this.protocol = protocol;
    }
	
}
