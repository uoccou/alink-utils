// Copyright (c) <2001> by America Online, Inc.  All rights reserved.
// This software is proprietary to America Online, Inc.  Unauthorized
// use, copying, and/or disclosure is strictly prohibited.

package ie.uoccou.util;

import java.io.IOException;
import java.io.Writer;

/**
 * XML utility methods for character classification and encoding of
 * CDATA.
 *
 */
public  class XML
{
	
	public static String simpleEscape(String input){
		String s = input.replaceAll("&", "&amp;");
	    s = s.replaceAll("<", "&lt;");
	    return s;
	}
	public static String getCData(String input){
		String s = "<![CDATA[" + input.replaceAll("]]>", "]]>]]><![CDATA[")
		+ "]]>";
		return s;
	}
    /**
     * Encodes the special characters '&amp;' and '<' to "&amp;amp;"
     * and "&amp;lt;" and writes the results to out.  Any characters
     * determined to be "illegal" based on the IsIllegal() method
     * below will be ignored and not output.
     * @throws IOException from Writer out.
     */
    public static void EncodeText( String in, Writer out )
        throws IOException
    {
        int i = 0;
        int last = 0;
        int end = in.length();
        
        while( i < end ) {
            if( IsIllegal( in.charAt(i) ) ) {
                if( last < i ) out.write( in, last, i - last );
                last = ++i;
            }
            else if( in.charAt(i) == '<' ) {
                if( last < i ) out.write( in, last, i - last );
                out.write( "&lt;" );
                last = ++i;
            }            
            else if( in.charAt(i) == '&' ) {
                if( last < i ) out.write( in, last, i - last );
                out.write( "&amp;" );
                last = ++i;
            }                          
            else ++i;
        }
        if( last < i ) out.write( in, last, i - last );        
    }

    /**
     * Encodes the special characters '&amp;', '<' , and '"' to
     * "&amp;amp;", "&amp;lt;", and "&amp;quot;" and writes the
     * results to out.  Any characters determined to be "illegal"
     * based on the IsIllegal() method below will be ignored and not
     * output.
     * @throws IOException from Writer out.
     */
    public static void EncodeAttr( String in, Writer out )
        throws IOException
    {
        int i = 0;
        int last = 0;
        int end = in.length();
        
        while( i < end ) {
            if( IsIllegal( in.charAt(i) ) ) {
                if( last < i ) out.write( in, last, i - last );
                last = ++i;
            }
            else if( in.charAt(i) == '<' ) {
                if( last < i ) out.write( in, last, i - last );
                out.write( "&lt;" );
                last = ++i;
            }            
            else if( in.charAt(i) == '&' ) {
                if( last < i ) out.write( in, last, i - last );
                out.write( "&amp;" );
                last = ++i;
            }                          
            else if( in.charAt(i) == '"' ) {
                if( last < i ) out.write( in, last, i - last );
                out.write( "&quot;" );
                last = ++i;
            }                          
            else ++i;
        }
        if( last < i ) out.write( in, last, i - last );        
    }

    /**
     * Return true if a character is an illegal XML character based on
     * section 2.2 of the XML Recommendation.  This is all the ASCII
     * range characters less than SPACE other than TAB, NEWLINE, or
     * CR.  The additional UNICODE surrogate blocks are not
     * considered.
     */
    public static boolean IsIllegal( char c )
    {
        if( ( c < ' ' ) &&
            ( ( c != '\t' ) &&
              ( c != '\n' ) &&
              ( c != '\r' ) ) ) return true;
        return false;
    }
}
