package ie.uoccou.util;

import java.util.Locale;

public class GenLocaleConstants {

	public static void main(String[] args) {
		System.out.println("***********LANGUAGES************************");
		  String[] codes = java.util.Locale.getISOLanguages();
		  for (String isoCode: codes) {
			   Locale locale = new Locale(isoCode);
			   System.out.println(isoCode.toUpperCase() + "(\"" + locale.getDisplayLanguage(locale) + "\"),");
		
			  }
		  System.out.println("**********COUNTRIES*************************");
		  Locale l = Locale.getDefault();
		  String[] countries = java.util.Locale.getISOCountries();
		  for (String c: countries) {
			   //Locale locale = new Locale(isoCode);		
			   //System.out.println("lang : " + locale.toString());
			  Locale locale = new Locale(l.getLanguage(), c);
			   System.out.println( c + "(\"" + locale.getDisplayCountry() + "\"),");
		
			  }
		
	}

}
