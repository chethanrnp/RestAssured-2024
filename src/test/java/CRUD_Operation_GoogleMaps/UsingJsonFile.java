package CRUD_Operation_GoogleMaps;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;

public class UsingJsonFile {

	@Test
	public void usingJsonFile() {
		baseURI = "https://rahulshettyacademy.com";
		// adding the place using json file
		Response resp = given().queryParam("key", "qaclick123")
				.body(new File(System.getProperty("user.dir") + "\\src\\test\\resources\\GoogleMaps.json"))
				.contentType(ContentType.JSON).log().all().when().post("/maps/api/place/add/json").then().log().all()
				.extract().response();
		// getting the place id
		String placeId = resp.jsonPath().get("place_id");
		// geeting the added place
		given().queryParam("key", "qaclick123").queryParam("place_id", placeId).when().get("/maps/api/place/get/json")
				.then().log().all();
	}
}
