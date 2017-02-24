package ie.uoccou.util;

import java.util.UUID;

public class SimpleUuidGenerator {

	public static String getUUID(){
		UUID x = UUID.randomUUID();
		return x.toString();
		
	}
	
	public static boolean matches(String returned, String stored){
		
		boolean rc = false;
		UUID u = UUID.fromString(stored);
		UUID c = UUID.fromString(returned);
		
		if (u.equals(c) )
			rc = true;
		
		return rc;
	}
}
