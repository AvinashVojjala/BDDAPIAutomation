package base;

import java.io.FileNotFoundException;
import org.json.simple.JSONObject;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import utilities.PropertiesReader;

public class CrudOperation  {
	static PropertiesReader pro = new PropertiesReader();

	private static final String BASE_URL = pro.getPropValue("baseurl");
	 
	private static Response response;
	RequestSpecification reqspec;
	
	public String performLogin(String username,String password) {
		RestAssured.baseURI = BASE_URL;
		
		Response response = RestAssured.given()
				.config(RestAssured.config()
				.encoderConfig(EncoderConfig.encoderConfig()
				.encodeContentTypeAs("x-www-form-urlencoded",
				ContentType.URLENC)))
				.contentType("application/x-www-form-urlencoded; charset=UTF-8")
				.formParam("grant_type", "authorization_code")
				.formParam("username", username)
				.formParam("password", password)
				.post("/getToken");
				
        String token = response.jsonPath().getString("token");
		return token;
	}	

	public Response performPOSTcall(String username, String password,String apiName, JSONObject req) throws FileNotFoundException {
		RestAssured.baseURI = BASE_URL;
		String token = performLogin(username, password);
		RequestSpecification request = RestAssured.given().headers("Authorization","Bearer " + token);		
		APIResources resourceAPI= APIResources.valueOf(apiName);
		response = request.body(req).post(resourceAPI.getResource());
		return response;
	}
	
	public Response performGETcall(String username, String password,String apiName) throws Exception {
		RestAssured.baseURI = BASE_URL;
		String token = performLogin(username, password);
		RequestSpecification request = RestAssured.given().headers("Authorization","Bearer " + token);		
		request.header("Content-Type", "application/json");
		APIResources resourceAPI= APIResources.valueOf(apiName);
		response = request.get(resourceAPI.getResource());
		//Assert.assertEquals(response.getStatusCode(), 200); 
		return response;
	}

}
