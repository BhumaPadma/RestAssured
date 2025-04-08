package day4;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

public class FileUploads {
	
	@Test
	void singleFileUpload(){
		
		File myfile = new File("C:\\Users\\BPADMA\\Downloads\\postman\\test1.txt");
		
		given()
			.multiPart("file" , myfile)
			.contentType("multipart/form-data")
		
		.when()
			.post("http://localhost:8080/uploadFile")
		
		.then()
			.statusCode(200)
			.body("fileName", equalTo("test1.txt"))
			.log().body();
		
		
	}
	
	@Test
	void MultipleFileUploads(){
		
		File myfile1 = new File("C:\\Users\\BPADMA\\Downloads\\postman\\test1.txt");
		File myfile2 = new File("C:\\Users\\BPADMA\\Downloads\\postman\\test2.txt");
		
		given()
			.multiPart("files",myfile1)
			.multiPart("files",myfile2)
			.contentType("multipart/form-data")
		
		.when()
			.post("http://localhost:8080/uploadMultipleFiles")
		
		.then()
			.statusCode(200)
			.body("[0].fileName", equalTo("test1.txt"))
			.body("[1].fileName", equalTo("test2.txt"))
			.log().body();
		
		
	}

}
