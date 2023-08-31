package jsonPath;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJson {

	public static void main(String[] args) {
		// creatin object of JSON Path and passing String
		JsonPath js = new JsonPath(new Payload().coursePrice());

		// Print the number of cources returned by APE
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Get purchase AMount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);

		// Print tile of First Cource
		String titleFirstCource = js.getString("courses[0].title");
		System.out.println(titleFirstCource);

		// Print all the Cources title and Respective title
		for (int i = 0; i < count; i++) {
			String courcesTitele = js.get("courses[" + i + "].title");
			int courcesPrice = js.getInt("courses[" + i + "].price");

			System.out.println(courcesTitele + " = " + courcesPrice);
		}

		// Number of copies Sold By RPA
		for (int i = 0; i < count; i++) {
			if (js.get("courses[" + i + "].title").equals("RPA")) {
				int price = js.getInt("courses[" + i + "].price");
				int copies = js.getInt("courses[" + i + "].copies");

				System.out.println("Number of Copies Sold by RPA = " + copies);
				break;
			}
		}

		// Verify if sum of all cources matches with purchase amount

		int actualAmount = 0;
		for (int i = 0; i < count; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			actualAmount = actualAmount + (price * copies);
		}

		System.out.println(actualAmount);
		// verify using Assert Class
		Assert.assertEquals(actualAmount, purchaseAmount);
	}

}
