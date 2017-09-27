package com.atos;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Servlet implementation class DirectorySample
 */
@WebServlet("/DirectorySample")
public class DirectorySample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DirectorySample() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        PrintWriter printWriter  = response.getWriter();
        printWriter.println("<h1>LDAP Example</h1>");
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		Properties properties = new Properties();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		//properties.put(Context.PROVIDER_URL, "ldap://129.185.160.190:389");
		
		String ldapurl = "";
		
		//accessing from VCAP ENV
		String vcap = System.getenv("VCAP_SERVICES");
		JSONObject jsonObject = new JSONObject(vcap);	
		//jsonObject =  jsonObject.getJSONObject("credentials");
		
		JSONArray array = jsonObject.getJSONArray("user-provided");
		jsonObject = array.getJSONObject(0);
		jsonObject =  jsonObject.getJSONObject("credentials");
		String host = jsonObject.getString("host");
    	String port = jsonObject.getString("port");    	
    	
    	//creating URL
    	ldapurl="ldap://"+host+":"+port;
		
    	//printWriter.println(" URL : "+ldapurl);
    	properties.put(Context.PROVIDER_URL, ""+ldapurl);
    	//properties.put(Context.PROVIDER_URL, "ldap://34.213.134.229:389");
    	
		try {
		DirContext context = new InitialDirContext(properties);
		Attributes attrs = context
		.getAttributes("cn=Action Maginley,ou=Accounting,o=Conf");
		//System.out.println("Surname: " + attrs.get("sn").get());
		System.out.println("Common name : " + attrs.get("cn").get());
		System.out.println("telephone number : " + attrs.get("homePhone").get());
		printWriter.println("<br>");
		
		printWriter.println("Common name : " +"<b>"+ attrs.get("cn").get()+"</b>");
		printWriter.println("<br><br>");		
		printWriter.println("telephone number : "+"<b>" + attrs.get("homePhone").get()+"</b>");
		printWriter.println("<br>");		
		printWriter.println("Mail id : "+"<b>" + attrs.get("mail").get()+"</b>");
		printWriter.println("<br>");		
		printWriter.println("UID : "+"<b>" + attrs.get("uid").get()+"</b>");
		printWriter.println("<br>");		
		printWriter.println("Employee Type : "+"<b>" + attrs.get("employeeType").get()+"</b>");
		
		
		} catch (NamingException e) {
		e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
