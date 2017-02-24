package ie.uoccou.util;

import org.apache.ibatis.annotations.Param;



public interface MailTemplateMapper {
	
	MailTemplate readMailTemplate(@Param("name") String template);
	
}
