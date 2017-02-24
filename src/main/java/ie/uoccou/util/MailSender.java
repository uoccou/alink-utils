package ie.uoccou.util;

public interface MailSender {

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUsername(String username);

	public abstract String getUsername();

	public abstract void setPort(int port);

	public abstract int getPort();

	public abstract void setHost(String host);

	public abstract String getHost();
	
	public abstract boolean isDoAuth();
	public void setDoAuth(boolean doAuthentication);
	
	public abstract boolean send(			 
			  final String recipients,
			  final String from,
			  final String subject,
			  final String body)  throws Exception;
	public abstract boolean send(			 
			  final String recipients,
			  final String from,
			  final String[] ccList,
			  final String[] bccList,
			  final String subject,
			  final String body,
			  final MailService.AttachmentRef attachmentPath)  throws Exception;
	public abstract void setMailDomain(String mailDomain);

	public String getMailDomain();
}
