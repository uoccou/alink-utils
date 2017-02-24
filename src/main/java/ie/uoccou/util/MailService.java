package ie.uoccou.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * resolve mail template ids from mailtemplates db table, then use templated to render tokens and placeholders and send mail
 * 
 * @author ultan
 *
 */
public class MailService {
	/**
	 * Simple class to encapuslate a mail attachement. Supply the full path filename and the name that file should be attached as. Mail sender should take care of situation where attachment name is not supplied.
	 * 
	 * @author ultan
	 *
	 */
	public class AttachmentRef {
		String sourceFileName;
		String attachmentName;
		public AttachmentRef(String sourceFile, String mailName){
			sourceFileName=sourceFile;
			attachmentName=mailName;
		}
	}
	protected static final Logger logger = LoggerFactory.getLogger(MailService.class);	
	@Autowired
	private MailTemplateMapper templateMapper;	
	
	@Autowired
	private MailTemplater templater;	
	/**
	 * send mail given a mailTemplate name. Rendered using configured MailTemplater. 
	 * 
	 * @param channelId - the channel name
	 * @param templateName - the ID of the template
	 * @param toAddress - who to send to
	 * @param subject - optional subject to override what is in the template record
	 * @param props - Map of possible name values for template token replacement. May have more than one value to accomodate multiple http params of same name: item=value1&item=value2&item=value3. These are resolved in the template using $item1, $item2, $item3 (ie base 1)
	 * @param attachmentPath - path to file to attach to mail (or null)
	 * @return true on success, logging at error level otherwise
	 */
	public boolean sendMail(
			String channelId,
			String templateName,
			String toAddress,
			String subject,
			Map<String,String[]> props,
			AttachmentRef attachmentPath
			){
 
	    boolean rc = false;
	    MailTemplate  template = templateMapper.readMailTemplate(templateName);
		
	    rc = checkAndSend(channelId, toAddress, subject, props, template,attachmentPath);
	   
	  
	   return rc;
	}

	private boolean checkAndSend(String channelId, String toAddress,
			String subject, Map<String, String[]> props,
			MailTemplate template, AttachmentRef attachmentPath) {
		
		boolean rc = false;
		if(template==null || toAddress==null)
		{
		   logger.error("No template or email address available ? '" + toAddress + "' with template '" 
				   + ( (null != template) ? template.getName() : "NULL") +"' and channelId '" + channelId + "'"  );
			
		} else	{
			 //SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
			 try {
				 
				 rc = renderMail(props, template, subject, toAddress, attachmentPath);
				 
			 } catch (Exception e){
				 //dont send mail on error
				logger.error("Problem sending email to '" + toAddress + "' with template '" + template.getName() +"' and channelId '" + channelId + "' : " + e.toString() );
				
			 }
			    	
		}
		return rc;
	}
	private boolean checkAndSend(String channelId, String toAddress, 
			String subject,List<Object> props,
			MailTemplate template, AttachmentRef attachmentPath) {
		
		boolean rc = false;
		if(template==null || toAddress==null)
		{

			   logger.error("No template or email address available ? '" + toAddress + "' with template '" 
					   + ( (null != template) ? template.getName() : "NULL") +"' and channelId '" + channelId + "'"  );
			
		} else	{
			 //SimpleMailMessage message = new SimpleMailMessage(simpleMailMessage);
			 try {
				 
				 rc = renderMail(props, template, subject, toAddress, attachmentPath);
				 
			 } catch (Exception e){
				 //dont send mail on error
				logger.error("Problem sending email to '" + toAddress + "' with template '" + template.getName() +"' and channelId '" + channelId + "' : " + e.toString() );
				
			 }
			    	
		}
		return rc;
	}
	/**
	 * send mail given a mailTemplate content body. Rendered using configured MailTemplater. 
	 * 
	 * @param channelId - the channel name
	 * @param templateName - the ID of the template
	 * @param toAddress - who to send to
	 * @param subject - optional subject to override what is in the template record
	 * @param props - Map of possible name values for template token replacement. May have more than one value to accomodate multiple http params of same name: item=value1&item=value2&item=value3. These are resolved in the template using $item1, $item2, $item3 (ie base 1)
	 * @param attachmentPath - path to file to attach to mail (or null)
	 * @return true on success, logging at error level otherwise
	 */
	public boolean sendLocalTemplateMail(
			String channelId,
			String templateBody,
			String toAddress,
			String ccList, String bccList,
			String subject,
			Map<String,String[]> props,
			AttachmentRef attachmentPath
			){
 
	    boolean rc = false;
	    
	    MailTemplate  template = new MailTemplate();
	    template.setTemplate(templateBody);
	    template.setSubject(subject);
	    template.setCc(ccList);
	    template.setBcc(bccList);
		
	    rc = checkAndSend(channelId, toAddress, subject, props, template, attachmentPath );
	    
	   
	  
	   return rc;
	}
	/**
	 * send mail given a mailTemplate name. Rendered using configured MailTemplater. 
	 * 
	 * @param channelId - the channel name
	 * @param templateName - the ID of the template
	 * @param toAddress - who to send to
	 * @param subject - optional subject to override what is in the template record
	 * @param props - Map of possible name values for template token replacement. May have more than one value to accomodate multiple http params of same name: item=value1&item=value2&item=value3. These are resolved in the template using $item1, $item2, $item3 (ie base 1)
	 * @param attachmentPath - path to file to attach to mail (or null)
	 * @return true on success, logging at error level otherwise
	 */
	public boolean sendMail(
			String channelId,
			String templateName,
			String toAddress,
			String subject,
			List<Object> props,
			AttachmentRef attachmentPath
			){
 
	    boolean rc = false;
	    
	    MailTemplate  template = templateMapper.readMailTemplate(templateName);
	    rc = checkAndSend(channelId, toAddress, subject, props, template,attachmentPath);
	  
	   return rc;
	}
	/**
	 * send mail given a mailTemplate content body. Rendered using configured MailTemplater. 
	 * 
	 * @param channelId - the channel name
	 * @param templateName - the ID of the template
	 * @param toAddress - who to send to
	 * @param subject - optional subject to override what is in the template record
	 * @param props - Map of possible name values for template token replacement. May have more than one value to accomodate multiple http params of same name: item=value1&item=value2&item=value3. These are resolved in the template using $item1, $item2, $item3 (ie base 1)
	 * @param attachmentPath - path to file to attach to mail (or null)
	 * @return true on success, logging at error level otherwise
	 */
	public boolean sendLocalTemplateMail(
			String channelId,
			String templateBody,
			String toAddress,
			String ccList, 
			String bccList,
			String subject,
			List<Object> props,
			AttachmentRef attachmentPath
			){
 
	    boolean rc = false;
	    MailTemplate  template = new MailTemplate();
	    template.setTemplate(templateBody);
	    template.setSubject(subject);
	    template.setCc(ccList);
	    template.setBcc(bccList);
	    
	    rc = checkAndSend(channelId, toAddress, subject, props, template, attachmentPath);
	    
	     
	   return rc;
	}
	
	private boolean renderMail(List<Object> props, MailTemplate template, String subject, String toAddress, AttachmentRef attachmentPath) {
		
		boolean rc = false;
		//use request subject if present else template subject
		String mail = templater.sendEmail(props,template.getTemplate(), 
				subject != null ? subject : template.getSubject(), 
						toAddress,
						template.getFromAddr(),
						template.getCc(),
						template.getBcc(),
						attachmentPath
						);
		 //model.addAttribute("email", mail); 
		   
		if ( null != mail )
			rc = true;
		
		return rc;
	}	
private boolean renderMail(Map<String,String[]> props, MailTemplate template, 
		String subject, 
		String toAddress,
		AttachmentRef attachmentPath
		) {
	
    boolean rc = false;
	//use request subject if present else template subject
	String mail = templater.sendEmail(props,template.getTemplate(), 
			subject != null ? subject : template.getSubject(), 
					toAddress,	
					template.getFromAddr(),
					template.getCc(),
					template.getBcc(),
					attachmentPath);
	 //model.addAttribute("email", mail); 
	if ( null != mail )
		rc = true;
	
	return rc;
	
}	
	
}
