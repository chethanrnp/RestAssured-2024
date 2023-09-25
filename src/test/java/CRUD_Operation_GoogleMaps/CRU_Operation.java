package CRUD_Operation_GoogleMaps;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CRU_Operation {

	@Test
	public void create() {
		baseURI = "https://rahulshettyacademy.com";
		// creating the place
		// passing body directy and verify status code and satus body and also server
		Response res = given().queryParam("key", "qaclick123").contentType(ContentType.JSON)
				.body(new files.Payload().addPlace()).log().all().when().post("/maps/api/place/add/json").then().assertThat()
				.statusCode(200).log().all().body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract().response();
		// getting the place id from the response
		String placeId = res.jsonPath().get("place_id");

		// update the place
		given().queryParam("key", "qaclick123").contentType(ContentType.JSON)
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"70 Summer walk, USA\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}\r\n" + "")
				.log().all().log().all().when().put("/maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated")).log().all();
		// validate the updated place
		String respo = given().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("/maps/api/place/get/json").then().assertThat().statusCode(200).log().all().extract().response()
				.asString();
		// creating jason object to convert string to JSON
		JsonPath js = new JsonPath(respo);
		// gettin addres from and validating
		Assert.assertEquals(js.getString("address"), "70 Summer walk, USA");
	}

}
