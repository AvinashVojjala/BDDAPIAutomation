package stepdef;
import org.json.simple.JSONObject;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import base.CrudOperation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.Utilities;

public class GoogleMap extends CrudOperation{
	public static Response response;
	JsonPath js;
	public static String placeID;

	@Given("User calls {string} with {string} http request")
	public void user_calls_request_with_http_request(String apiName, String requestType) throws Exception {
		System.out.println("the apiName is : " + apiName);
		System.out.println("the requestType is : " + requestType);
		JSONObject requestParams = new JSONObject();
		
		requestParams.put("userId", 11);
		requestParams.put("id", 12);
		requestParams.put("title", "Avinash");
		requestParams.put("completed", false);

		if(requestType.equalsIgnoreCase("POST")) {
			
			response = performPOSTcall(apiName, requestParams);
		} else if(requestType.equalsIgnoreCase("GET")) {
			System.out.println("entered get ");

 			response = performGETcall(apiName);
		}
	}
	

	@Then("Status code is {int}")
	public void status_code_is(Integer int1) {
		System.out.println("Status Code: "+response.statusCode());
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String value1, String value2) {
		js = Utilities.rawToJson(response);
		Assert.assertEquals(js.get(value1), value2);
	}
	
	@Then("I retrieve the Place ID")
	public void i_retrieve_the_place_id() {
		js = Utilities.rawToJson(response);
		placeID = js.get("place_id");
		System.out.println("Place ID: "+placeID);
	}

}