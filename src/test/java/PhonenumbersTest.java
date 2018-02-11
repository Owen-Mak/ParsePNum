package test.java;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;
import net.codejava.Phonenumbers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;


/*
 * Unit tests for Phonenumbers.java
 *
 * @author Owen Mak
 */
public class PhonenumbersTest extends TestCase{
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
    @Before
    protected void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void test1() throws Exception{
		//test case#1 for doGet() 
		//case is based on example from OSD600 page
		//url = http://localhost:8080/ParsePNum/phonenumbers/parse/text/Seneca%20Phone%20Number%3A%20416-491-5050
		System.out.println("Test #1");
		when(request.getScheme()).thenReturn("http"); 
		when(request.getServerName()).thenReturn("localhost"); 
		when(request.getServerPort()).thenReturn(8080);
		when(request.getContextPath()).thenReturn("/ParsePnum"); 
		when(request.getServletPath()).thenReturn("/phonenumbers");
		StringBuffer strBuff = new StringBuffer("/ParsePNum/phonenumbers/parse/text/Seneca%20Phone%20Number%3A%20416-491-5050");
		when(request.getRequestURL()).thenReturn(strBuff);
		when(request.getQueryString()).thenReturn(null);
		
		//redirects server responses to sw
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);				
		when(response.getWriter()).thenReturn(pw);		
		new Phonenumbers().doGet(request, response);
		//stores server responses to result
		String result = sw.getBuffer().toString().trim();	
		System.out.println("Expected Result: [\"(416) 491-5050\"]\tActual Result: " + result);
		assertEquals("[\"(416) 491-5050\"]", result);	
    }
    
    @Test
    public void test2() throws Exception{
    	//test case#2 for doGet()
    	//typical GET case using the submit button
		//url = http://localhost:8080/ParsePNum/phonenumbers/parse/text/?phoneNumber=416-297-4913
		System.out.println("Test #2");
		when(request.getScheme()).thenReturn("http"); 
		when(request.getServerName()).thenReturn("localhost"); 
		when(request.getServerPort()).thenReturn(8080);
		when(request.getContextPath()).thenReturn("/ParsePnum"); 
		when(request.getServletPath()).thenReturn("/phonenumbers");
		StringBuffer strBuff = new StringBuffer("/ParsePNum/phonenumbers/parse/text/?phoneNumber=416-297-4913");
		when(request.getRequestURL()).thenReturn(strBuff);
		when(request.getQueryString()).thenReturn(null);
		
		//redirects server responses to sw
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);				
		when(response.getWriter()).thenReturn(pw);		
		new Phonenumbers().doGet(request, response);
		//stores server responses to result
		String result = sw.getBuffer().toString().trim();	
		System.out.println("Expected Result: [\"(416) 297-4913\"]\tActual Result: " + result);
		assertEquals("[\"(416) 297-4913\"]", result);
    }
	
    @Test
    public void testdoPost() throws Exception{
    	//this is a WIP to test the doPost method
    	//have been trying to create a httpServletRequest from code to simulate a post request without success
    	//need to create request, add file upload payload onto the request, and call doPost() with request
    	//will try doing this using Spring Test Framework in the future    	    
		
    	//FileInputStream f = new FileInputStream(new File("F:/Users/Owen/Documents/workspace/ParsePNum/tempReadme.txt"));		
		File f = new File("tempReadme.txt");
		DiskFileItemFactory factory = new DiskFileItemFactory();				
	    File repository = new File("tempReadme.txt");
	    factory.setRepository(repository);	    
	    ServletFileUpload tempUpload = new ServletFileUpload(factory);
	    	    	   
		//InputStream fileIn = new FileInputStream(f);
	    //when(in).thenReturn(fileIn);
	    //when(contents).thenReturn("123456789");
		PhoneNumberUtil pNumUtil = PhoneNumberUtil.getInstance();
		Iterable<PhoneNumberMatch> match = pNumUtil.findNumbers("416-297-4913", "CA");
		Iterator<PhoneNumberMatch> it = match.iterator();
		when(request.getContentType()).thenReturn("multipart/form-data; boundary=someBoundary");		
	    //when(phoneUtil.findNumbers("123456789","CA")).thenReturn(match);
		//when(i).thenReturn(match.iterator());			
		//redirects server responses to sw		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		when(response.getWriter()).thenReturn(pw);		
		new Phonenumbers().doPost(request, response);
		//stores server responses to result			
		String result = sw.getBuffer().toString().trim();	
		System.out.println("Result: " + result);
		
    		    	
    }
    
	@Test
	public void test() throws Exception {
		//doGet() test
		test1();
		test2();
		
	    //capture duplicate prints after junit test is finished
	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	}
}
