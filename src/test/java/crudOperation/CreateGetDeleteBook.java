package crudOperation;

import org.testng.annotations.Test;

import files.Payload;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.Random;

public class CreateGetDeleteBook {

	@Test
	public void addBook() {
		// setting the base URI
		baseURI = "http://216.10.245.166";

		// creating ramdom number and converting to String
		Random random = new Random();
		int r = random.nextInt(999);
		String aisle = Integer.toString(r);

		String isbn = "cbz";

		// Creating book
		String resp = given().log().all().contentType(ContentType.JSON).body(new Payload().addBook(isbn, aisle)).when()
				.post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		// converting String response to json format and getting the BookID
		JsonPath js = new JsonPath(resp);
		String boookID = js.getString("ID");

		System.out.println("Id of the book = " + boookID);

		// Get the book
		given().queryParam("ID", boookID).when().get("Library/GetBook.php").then().log().all();

		// Delete the book
		given().log().all().contentType(ContentType.JSON)
				.body("{\r\n" + " \r\n" + "\"ID\" : \"" + boookID + "\"\r\n" + " \r\n" + "} \r\n" + "").when()
				.post("/Library/DeleteBook.php").then().log().all();

	}
}
