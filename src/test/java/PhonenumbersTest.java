package test.java;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
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
	
	private ServletFileUpload fileUpload;
    private Phonenumbers pNumbers;
    private FileItemStream uploadStream;
    private String uid;
	
    @Before
    protected void setUp() throws Exception {
    	
    	ServletContext servletContext = mock(ServletContext.class);

        when(servletContext.getRealPath(anyString())).thenReturn("/tmp");

        ServletConfig config = mock(ServletConfig.class);
        when(config.getServletContext()).thenReturn(servletContext);

        fileUpload = mock(ServletFileUpload.class);
        
        pNumbers = new Phonenumbers(fileUpload);
        pNumbers.init(config);

        uid = newUid();
    	
    	MockitoAnnotations.initMocks(this);
    }
    
    @Before
    public void createRequestAndResponse() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
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
	
    @SuppressWarnings("resource")
	@Test
    public void testdoPost() throws Exception
    {	
    	System.out.println("Test #3");
    	String uploadContents = "[\"(908) 908-9008\",\"(907) 907-9007\",\"(906) 906-9006\"]";
        setupMultiPartRequest("randomfile.txt", uid, uploadContents, uploadContents.length());

        File f = new File("src/test/java/randomfile.txt");
        if(f.exists() && !f.isDirectory()) 
        { 
        	PrintWriter writer = new PrintWriter(f.getName(), "UTF-8");
        	writer.println("9089089008 9079079007 9069069006 Extra Text! ");
        	writer.close();
        }
        
        f.createNewFile();
        
        StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);
        pNumbers.doPost(request, response);
        
        //String result = new BufferedReader(new FileReader(f.getName())).readLine();
        String result = sw.getBuffer().toString().trim();
        
        System.out.println("Expected Result: [\"(908) 908-9008\",\"(907) 907-9007\",\"(906) 906-9006\"]\tActual Result: " + result);
        assertEquals(uploadContents, result);
    	
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
	
	private void setupMultiPartRequest(String originalFileName, String uid, String uploadContents, int contentLength) throws Exception 
	{
        FileItemStream uidStream = mock(FileItemStream.class);
        when(uidStream.isFormField()).thenReturn(true);
        when(uidStream.getFieldName()).thenReturn("uid");
        when(uidStream.openStream()).thenReturn(stringToStream(uid));

        uploadStream = mock(FileItemStream.class);
        when(uploadStream.isFormField()).thenReturn(false);
        when(uploadStream.getName()).thenReturn(originalFileName);
        when(uploadStream.openStream()).thenReturn(stringToStream(uploadContents));

        FileItemIterator itemIterator = mock(FileItemIterator.class);
        when(itemIterator.hasNext()).thenReturn(true, true, false);
        when(itemIterator.next()).thenReturn(uidStream, uploadStream);
        when(fileUpload.getItemIterator(request)).thenReturn(itemIterator);

        when(request.getContextPath()).thenReturn("/");
        when(request.getContentLength()).thenReturn(contentLength);
    }
	
	private ByteArrayInputStream stringToStream(String string) 
	{
        return new ByteArrayInputStream(string.getBytes());
    }
	
	private String newUid() 
	{
        return String.valueOf(System.currentTimeMillis());
    }
}
