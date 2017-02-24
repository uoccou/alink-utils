package ie.uoccou.util.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TruncateFormat {
	
	/**
	 * The max length of the input string to be truncated.
	 */
	String length() default DbColumns.LEN_DEFAULT;
	
	
}
