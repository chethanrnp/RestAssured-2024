package Seril_DeserilizationCourses;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.add.place.AddPlace;
import pojo.add.place.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;

public class serilizationAddPlace {

	@Test
	public void serilizationAddPlace() {
		// Setting the base URI
		baseURI = "https://rahulshettyacademy.com";
		// Creating the body using the pojo class
		Location location = new Location(-38.383494, 33.427362);
		ArrayList<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");

		// Creating the object of AddPlace Pojo class
		AddPlace addPalce = new AddPlace(location, 50, "Frontline house", "(+91) 983 893 3937",
				"29, side layout, cohen 09", types, "http://google.com", "French-IN");

		// Adding the place using Pojo class
		String resp = given().log().all().queryParam("key", "qaclick123").contentType(ContentType.JSON).body(addPalce)
				.when().post("/maps/api/place/add/json").then().extract().response().asString();

		System.out.println(resp);

		// Creating object of the JSON path and getting place ID
		JsonPath js = new JsonPath(resp);
		String placeID = js.getString("place_id");
		System.out.println("Place ID = " + placeID);

		// DeSeriliazation using AddPlace Pojo Class
		AddPlace addPlaceResp = given().log().all().queryParams("key", "qaclick123").queryParams("place_id", placeID)
				.expect().defaultParser(Parser.JSON).when().get("/maps/api/place/get/json").then().log().all().extract()
				.response().as(AddPlace.class);

		// Getting the Lattitude
		System.out.println("lat = " + addPlaceResp.getLocation().getLat());

		// Getting the Types
		System.out.println("Types = " + addPlaceResp.getTypes().get(1));

		// Getting the Language
		System.out.println("Language = " + addPlaceResp.getLanguage());
	}
}
