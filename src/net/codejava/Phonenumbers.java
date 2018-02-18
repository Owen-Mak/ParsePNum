package net.codejava;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import java.net.URLDecoder;


/**
 * Servlet implementation class HelloServlet
 */

@WebServlet(urlPatterns = {"/phonenumbers/parse/text/*", "/phonenumbers/parse/file"})
public class Phonenumbers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
       
	private static final String FILES_PATH = "/files";
	private ServletFileUpload fileUpload;
	private ServletContext servletContext;
	private boolean doPostTestFlag = false;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Phonenumbers() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Phonenumbers(ServletFileUpload fileUpload) {
        this.fileUpload = fileUpload;
        this.doPostTestFlag = true;
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        this.servletContext = config.getServletContext();
        configureFilesPath();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = null;
		StringBuffer fullUrl = request.getRequestURL();
		String baseUrl = request.getScheme() + "://" + 
				request.getServerName() + ":" + 
				request.getServerPort() + 
				request.getContextPath() + 
				request.getServletPath();
		if (baseUrl.equals(fullUrl.toString())) {
			name = "";
		}else {
			String relUrl =  fullUrl.substring(baseUrl.length() + 1);
			if (request.getQueryString() != null) {
				relUrl = relUrl + "?" + request.getQueryString();
			}
			name = URLDecoder.decode(relUrl, "UTF-8");			
		}				
		Iterable<PhoneNumberMatch> matches = phoneUtil.findNumbers(name, "CA");
		printNonDuplicateNumbers(matches, response);			
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ServletFileUpload upload = new ServletFileUpload();
		ServletFileUpload fileUpload = new ServletFileUpload();
		fileUpload.setSizeMax(50000);
		String contents = null;
		try {
		      FileItemIterator iterator = fileUpload.getItemIterator(request);
		      while (iterator.hasNext()) {
		    	  FileItemStream item = iterator.next();		    	 
		          InputStream in = item.openStream();
		          contents = Streams.asString(in, UTF_8.name());
		      }
		      
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html");
	    response.setCharacterEncoding(UTF_8.name());
	    Iterable<PhoneNumberMatch> matches = phoneUtil.findNumbers(contents, "CA");
	    printNonDuplicateNumbers(matches, response);
	     
	}
	
	private void configureFilesPath() 
	{
        String path = this.servletContext.getRealPath(".") + FILES_PATH;
        File filesPath = new File(path);

        if (!filesPath.exists()) {
            //log.info("Creating directory {}", path);

            filesPath.mkdir();

            //log.info("Directory {} successfully created.", path);
        }
    }
	
	protected void printNonDuplicateNumbers(Iterable<PhoneNumberMatch> matches, HttpServletResponse response) {
		Iterator<PhoneNumberMatch> i = matches.iterator();
		ArrayList<PhoneNumber> fullNumberList = new ArrayList<>();
		ArrayList<PhoneNumber> noDuplicateList = new ArrayList<>();
		while (i.hasNext()) {
			PhoneNumberMatch m = i.next();
			fullNumberList.add(m.number());						
		}
		for (PhoneNumber m : fullNumberList) {
			if (!noDuplicateList.contains(m)) {
				noDuplicateList.add(m);
			}
		}
		try {
			if(!doPostTestFlag)
			{
				response.getWriter().print("[");
				Iterator<PhoneNumber> j = noDuplicateList.iterator();
				while (j.hasNext()) {
					PhoneNumber m = j.next();
					String out = "\"" + phoneUtil.format(m, PhoneNumberFormat.NATIONAL) + "\"";				
					response.getWriter().print(j.hasNext() ? out + "," : out);	
				}
				response.getWriter().print("]");
			}
			else
			{
				response.getWriter().print("[");
				Iterator<PhoneNumber> j = noDuplicateList.iterator();
				while (j.hasNext()) {
					PhoneNumber m = j.next();
					String out = "\"" + phoneUtil.format(m, PhoneNumberFormat.NATIONAL) + "\"";		
					response.getWriter().print(j.hasNext() ? out + "," : out);	
				}
				response.getWriter().print("]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

