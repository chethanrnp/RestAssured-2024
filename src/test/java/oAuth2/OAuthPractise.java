package oAuth2;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.get.cources.Api;
import pojo.get.cources.WebAutomation;
import pojo.get.cources.pojoGetCourses;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OAuthPractise {

	@Test
	public void oAuth() {

		String[] expectedCourseTitel = { "Selenium Webdriver Java", "Cypress", "Protractor" };
		// paste url and login and the paste in getcode
		String browserUrl = "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=chethanbc";
		// seperating code from the string
		String getCode = "https://rahulshettyacademy.com/getCourse.php?state=chethanbc&code=4%2F0Adeu5BWTp5BEKaost1Lf_yTvQPCe__HGYhNMlD5wR5pO-dUYJ5VgJC6MHWa9Gd2twjOujg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

		String code = getCode.split("&")[1].split("=")[1];

		System.out.println(code);

		// generate the access token with the help of the following
		String acessTokenResp = given().urlEncodingEnabled(false).log().all().queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		System.out.println("===" + acessTokenResp);

		// creating jsonpath object to extract acessToken from the string
		JsonPath js = new JsonPath(acessTokenResp);
		String acessToken = js.getString("access_token");

		// getting the crouses with the help of access token and Deserilization using
		// GetCource Pojo Class
		pojoGetCourses getCourse = given().queryParam("access_token", acessToken).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(pojo.get.cources.pojoGetCourses.class);

		// printing the output
		System.out.println("Course Name = " + getCourse.getCourses().getWebAutomation().get(0).getCourseTitle());

		System.out.println("LinkedIn = " + getCourse.getLinkedIn());

		System.out.println("Instructor Name = " + getCourse.getInstructor());

		// getting the price of the soap Testing
		List<Api> course = getCourse.getCourses().getApi();
		for (Api lv : course) {
			if (lv.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println("Price = " + lv.getPrice());
			}
		}

		// getting courses title of webAutomation and Storing to verify
		ArrayList<String> actualCoursesTitel = new ArrayList<String>();
		List<WebAutomation> web = getCourse.getCourses().getWebAutomation();

		for (WebAutomation lv : web) {
			actualCoursesTitel.add(lv.getCourseTitle());
		}
		// converting array to arrayList
		List<String> expectedCourTitle = Arrays.asList(expectedCourseTitel);

		// verify using Assert Class
		Assert.assertEquals(actualCoursesTitel, expectedCourTitle);
	}

}
