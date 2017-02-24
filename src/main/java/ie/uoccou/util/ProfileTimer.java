package ie.uoccou.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileTimer {
	
	protected final Logger _logger = LoggerFactory.getLogger(getClass());
	private long tick = 0;
	
	public long logProfileTime(String msg, long startTime){
		
		if( _logger.isDebugEnabled() ){
			_logger.debug( "TIMING (" + tick++ + ") " + msg + " : " + (System.currentTimeMillis() - startTime) );
		}
		return System.currentTimeMillis();
	}
	public long logProfileTime(long startTime ){
		return logProfileTime( null, startTime );
	}
}
