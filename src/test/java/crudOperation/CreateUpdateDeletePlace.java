package crudOperation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.Payload;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class CreateUpdateDeletePlace {

	public static void main(String[] args) {

		// setting the base URI
		baseURI = "https://rahulshettyacademy.com";

		// Creating the Address of the Location
		String resp = given().log().all().queryParam("key", "qaclick123").contentType(ContentType.JSON)
				.body(new Payload().addPlace()).when().post("/maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract()
				.response().asString();

		// converting String response to json format and getting the placeID
		JsonPath js = new JsonPath(resp);
		String placeId = js.getString("place_id");

		System.out.println("Place ID = " + placeId);

		// update the place
		String newAddres = "77 Summer walk,Hassan";
		given().log().all().queryParam("key", "qaclick123").contentType(ContentType.JSON)
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddres + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat()
				.body("msg", equalTo("Address successfully updated")).statusCode(200);

		// get the added place
		String responseAddres = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		// converting String response to json format and getting the address
		JsonPath jspath = new JsonPath(responseAddres);
		String actualAddress = jspath.getString("address");

		// validating address with Assert
		Assert.assertEquals(actualAddress, newAddres);

		System.out.println("Address = " + actualAddress);
	}

}
