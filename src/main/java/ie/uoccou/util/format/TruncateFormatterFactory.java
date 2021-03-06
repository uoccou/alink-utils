package ie.uoccou.util.format;

import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
/**
 * gets registered with FormattingConversionServiceFactoryBean to allow annotation based truncation formatting
 * 
 * @author ultan
 *
 */
public class TruncateFormatterFactory implements AnnotationFormatterFactory<TruncateFormat> {

	//@Override
    public Set<Class<?>> getFieldTypes() {
		Set<Class<?>> set = new HashSet<Class<?>>();
	    set.add(String.class);
	    return set;
    }

	//@Override
    public Printer<String> getPrinter(TruncateFormat annotation, Class<?> fieldType) {
	    // TODO Auto-generated method stub
		if ( annotation.length() != null )
			return new StringTruncater( annotation.length() );
		else
			return new StringTruncater();
    }

	//@Override
    public Parser<String> getParser(TruncateFormat annotation, Class<?> fieldType) {
		 // TODO Auto-generated method stub
		if ( annotation.length() != null )
			return new StringTruncater( annotation.length() );
		else
			return new StringTruncater();
    }

}
