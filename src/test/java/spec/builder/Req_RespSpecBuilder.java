package spec.builder;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.add.place.AddPlace;
import pojo.add.place.Location;

public class Req_RespSpecBuilder {

	@Test
	public void reqRespSpecBuilder() {

		// Creating the body using the pojo class
		Location location = new Location(-38.383494, 33.427362);
		ArrayList<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");

		// Creating the object of AddPlace Pojo class
		AddPlace addPalce = new AddPlace(location, 50, "Frontline house", "(+91) 983 893 3937",
				"29, side layout, cohen 09", types, "http://google.com", "French-IN");

		// building Request SpecBuilder
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.log(LogDetail.BODY).addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		// Add place request
		RequestSpecification addPlaceReq = given().spec(req).body(addPalce);
		// building Response SpecBuilder
		ResponseSpecification resp = new ResponseSpecBuilder().expectStatusCode(200).log(LogDetail.ALL)
				.expectContentType(ContentType.JSON).build();
		// Adding the place using Pojo class
		String addPlaceResp = addPlaceReq.when().post("/maps/api/place/add/json").then().spec(resp).extract().response()
				.asString();

		System.out.println(addPlaceResp);
	}
}
