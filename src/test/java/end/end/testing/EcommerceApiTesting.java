package end.end.testing;

import org.testng.Assert;
import org.testng.annotations.Test;

import groovyjarjarantlr4.v4.runtime.atn.OrderedATNConfigSet;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;

public class EcommerceApiTesting {

	@Test
	public void ecommerceApiTesting() {
		String expectedDeleteMess = "Product Deleted Successfully";

		// login to the appliction
		RequestSpecification req = new RequestSpecBuilder().log(LogDetail.ALL)
				.setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();

		RequestSpecification reqLogin = given().spec(req)
				.body(new LoginRequestPojo("chethan.supernova@gmail.com", "Chethan@17"));

		LoginResponePayload loginResp = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponePayload.class);
		// extracting token and userID from response
		String token = loginResp.getToken();
		String userID = loginResp.getUserId();

		// Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().log(LogDetail.ALL)
				.setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();

		RequestSpecification reqAddProduct = given().spec(addProductBaseReq).param("productName", "Lenavo")
				.param("productAddedBy", userID).param("productCategory", "Study").param("productSubCategory", "Shirts")
				.param("productPrice", "11500").param("productDescription", "HP").param("productFor", "ALL")
				.multiPart("productImage", new File(System.getProperty("user.dir") + "\\image\\Pan_card.jpg"));

		String respAddProduct = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all().extract()
				.response().asString();

		// Creating JSON path object to Extract ProductID
		JsonPath js = new JsonPath(respAddProduct);
		String productID = js.getString("productId");

		// Create order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.log(LogDetail.ALL).setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.build();
		// Creating body using Order Payload
		ArrayList<OrderDetails> orderBody = new ArrayList<OrderDetails>();
		OrderDetails orders = new OrderDetails("India", productID);
		orderBody.add(orders);
		Orders order = new Orders(orderBody);

		RequestSpecification reqCreateOrder = given().spec(createOrderBaseReq).body(order);

		String respCreateOrder = reqCreateOrder.when().post("/api/ecom/order/create-order").then().log().all().extract()
				.response().asString();

		System.out.println("Create Order = " + respCreateOrder);

		// Delete Order
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.log(LogDetail.ALL).setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.build();

		RequestSpecification reqDeleteProduct = given().spec(deleteProductBaseReq).pathParam("productID", productID);

		String respDeleteProduct = reqDeleteProduct.when().delete("/api/ecom/product/delete-product/{productID}").then()
				.log().all().extract().response().asString();

		JsonPath jPath = new JsonPath(respDeleteProduct);
		String actualDeleteMess = jPath.getString("message");

		// Verify delete product message using Assert Class
		Assert.assertEquals(expectedDeleteMess, actualDeleteMess);
	}
}
