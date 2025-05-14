package day8;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class CreateTestDataUsingFakeLibrary {
	
	@Test
	void createFakeTestData() {
		
		Faker faker = new Faker();
		String firstName = faker.name().firstName();
		String lastName = faker.name().lastName();
		String email = faker.internet().safeEmailAddress();
		String password = faker.internet().password(5,8);
		String phoneNum = faker.phoneNumber().cellPhone();
		
		System.out.println("FirstName : " + firstName);
		System.out.println("LastName : " + lastName);
		System.out.println("Email : " + email);
		System.out.println("Password : " + password);
		System.out.println("PhoneNumber : " + phoneNum);
				
		
		
	}

}
