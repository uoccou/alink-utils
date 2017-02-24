package ie.uoccou.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.agent.PowerMockAgent;
import org.powermock.modules.junit4.rule.PowerMockRule;

//PowerMockRunner doesn't work with EclEmma.
//@RunWith(PowerMockRunner.class).
@PrepareForTest(HttpUtils.class) 
public class TestHttpUtil {
	@Rule
	public PowerMockRule rule = new PowerMockRule();
	static {
	       PowerMockAgent.initializeIfNeeded();
	   }
	private static final String TEST_URL="http://www.google.com";
	private static final String TEST_URL_FTP="ftp://www.google.com";
	private static final String TEST_URL_BAD_DNS="http://www.google.iii";
	private static final String TEST_TIMEOUT_URL = "http://example.com";
	@Test
	public void testInstantiation() {
		
		HttpUtils util = new HttpUtils();
		util.setTimeout(100);
	
	}
	
	@Test
	public void testGoodUrl(){
		HttpUtils util = new HttpUtils();
		boolean rc = util.pingURL(TEST_URL);
		assertTrue( rc == true );
	}

	@Test
	public void testBadUrl(){
		HttpUtils util = new HttpUtils();
		boolean rc = util.pingURL(TEST_URL_BAD_DNS);
		assertTrue( rc == false );
	}
	
	@Test
	public void testFtp(){
		HttpUtils util = new HttpUtils();
		boolean rc = util.pingURL(TEST_URL_FTP);
		assertTrue( rc == true );
	}
	
	@Test
	public void testTimeout(){
		HttpUtils util = new HttpUtils();
		assertTrue(util.getTimeout()>0 && util.getTimeout()==HttpUtils.DEF_TIMEOUT);
		
		util.setTimeout(-1);
		assertTrue(util.getTimeout()>0 && util.getTimeout()==HttpUtils.DEF_TIMEOUT);
		util.setTimeout(0);
		assertTrue(util.getTimeout()>0 && util.getTimeout()==HttpUtils.DEF_TIMEOUT);		
		util.setTimeout(100);
		assertTrue(util.getTimeout()==100);
		util.setTimeout(HttpUtils.DEF_TIMEOUT_MAX);
		assertTrue(util.getTimeout()==HttpUtils.DEF_TIMEOUT_MAX);
		util.setTimeout(HttpUtils.DEF_TIMEOUT_MAX+1);
		assertTrue(util.getTimeout()==HttpUtils.DEF_TIMEOUT_MAX);
		
	}
	
	@Test
	/**
	 * From http://stackoverflow.com/questions/25495773/simulate-http-server-time-out-for-http-client-request
	 * @TODO: test other HttpReturn codes
	 */
	public void testTimeoutReturn() throws Exception {
		//create a mock URL and mock HttpURLConnection objects
	    //that will be our simulated server
	    URL mockURL = PowerMockito.mock(URL.class);
	    HttpURLConnection mockConnection = PowerMockito.mock(HttpURLConnection.class);

	    //powermock will intercept our call to new URL( url) 
	    //and return our mockURL object instead!
	    PowerMockito.whenNew(URL.class).withArguments(TEST_TIMEOUT_URL).thenReturn(mockURL);
	    //This tells our mockURL class to return our mockConnection object when our client
	    //calls the open connection method
	    PowerMockito.when(mockURL.openConnection()).thenReturn(mockConnection);

	    //this is our exception to throw to simulate a timeout
	    SocketTimeoutException expectedException = new SocketTimeoutException();

	    //tells our mockConnection to throw the timeout exception instead of returnig a response code
	    PowerMockito.when(mockConnection.getResponseCode()).thenThrow(expectedException);

	    //now we are ready to actually call the client code
	    HttpUtils util = new HttpUtils();

	    //our code should catch the timeoutexception and return false
	    assertFalse(util.pingURL(TEST_TIMEOUT_URL));

	   // tells mockito to expect the given void methods calls
	   //this will fail the test if the method wasn't called with these arguments
	   //(for example, if you set the timeout to a different value)	    
	    Mockito.verify(mockConnection).setConnectTimeout(HttpUtils.DEF_TIMEOUT);
	    Mockito.verify(mockConnection).setReadTimeout(HttpUtils.DEF_TIMEOUT);
	}
}
