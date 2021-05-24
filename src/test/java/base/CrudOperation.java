package base;

import java.io.FileNotFoundException;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import utilities.PropertiesReader;
import org.junit.Assert;

public class CrudOperation  {
	static PropertiesReader pro = new PropertiesReader();

	private static final String BASE_URL = pro.getPropValue("baseurl");
	 
	private static Response response;
	RequestSpecification reqspec;

	public Response performPOSTcall(String apiName, JSONObject req) throws FileNotFoundException {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();		 
		APIResources resourceAPI= APIResources.valueOf(apiName);
		request.header("Content-Type", "application/json");
		response = request.body(req).post(resourceAPI.getResource());
		Assert.assertEquals(response.getStatusCode(), 200); 
		return response;
	}
	
	public Response performGETcall(String apiName) throws Exception {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();		 
		APIResources resourceAPI= APIResources.valueOf(apiName);
		response = request.get(resourceAPI.getResource());
		Assert.assertEquals(response.getStatusCode(), 200); 
		return response;
	}

}
