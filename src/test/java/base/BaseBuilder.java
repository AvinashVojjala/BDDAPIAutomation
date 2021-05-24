package base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.PropertiesReader;

public class BaseBuilder {
	PropertiesReader pro = new PropertiesReader();
	RequestSpecBuilder builder;
	RequestSpecification reqspec;
	Response response;
	PrintStream log;
	
	public RequestSpecification placeSpecBuilder() throws FileNotFoundException {
		builder = new RequestSpecBuilder();
		
		String env = System.getProperty("WSNSHELL_HOME");
		
		if(env == null) {
			env = pro.getPropValue("baseurl");
		}
		else { 
			builder.setBaseUri(pro.getPropValue("baseurl")); 
		}
		System.out.println("Building Headers....");
		//builder.addQueryParam("key", "qaclick123");
		builder.setContentType("application/json");
		log = new PrintStream(new FileOutputStream("log.txt"));
		builder.addFilter(RequestLoggingFilter.logRequestTo(log));
		builder.addFilter(ResponseLoggingFilter.logResponseTo(log));
		reqspec =  builder.build();	
		return reqspec;
	}
}
