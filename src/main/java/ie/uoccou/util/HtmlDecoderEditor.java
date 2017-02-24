package ie.uoccou.util;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;
/**
 * HTML Decode strings values in params, eg "Fischer &amp;amp; Paykel" to "Fischer & Paykel"
 * <p>
 * use in a Controller with initBinder, eg.
 * <p>
 * <pre>
 * <code>@InitBinder</code>
 *	public void initBinder(WebDataBinder binder) {	    
 *	     // decode html encoded string values, eg "Fischer &amp; Paykel" to "Fischer & Paykel"
 *	    binder.registerCustomEditor(String.class, new HtmlDecoderEditor(true));
 *	}
 *
 * </pre>
 * 
 * @author Ultan
 * 
 */
public class HtmlDecoderEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) {
		if (null != text ){
			setValue(HtmlUtils.htmlUnescape(text));
		}
	}
}
