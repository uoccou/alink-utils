package ie.uoccou.util;

import java.util.List;
import java.util.Map;

public interface MailTemplater {

//	/** render the map of Strings using the template and send the mail */
//	public abstract String sendEmail(  Map<String,String[]> model,  
//			final String subject, 
//			String template,  
//			final String to);
	public abstract String sendEmail(  Map<String,String[]> model,  
			final String subject, 
			String template,  
			final String to,			
			final String from,
			 final String ccList, 
			 final String bccList,
			 final MailService.AttachmentRef attachmentPath);
	/** render the List of Objects using the template and send the mail */
	public abstract String sendEmail( List<Object> model,  
			final String subject, 
			String template,  
			final String to,
			final String from,
			 final String ccList, 
			 final String bccList,
			 final MailService.AttachmentRef attachmentPath);
//	public abstract String sendEmail( List<Object> model,  
//			final String subject, 
//			String template,  
//			final String to);
	public abstract void setMailSender(MailSender mailSender);

	
}
