package com.Selenium.APITest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.Selenium.JsonRequest.Address;
import com.Selenium.JsonRequest.CreatePerson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Service {
	List<JSONObject> list;
	public Response personcreation(String name,String surname,String city,String landmark,String state,String zipcode) {
		CreatePerson pogo1=new CreatePerson();
		pogo1.setName(name);
		pogo1.setSurname(surname);
		Address address=new Address();
		pogo1.setAddress(address);
		address.setCity(city);
		address.setLandmark(landmark);
		address.setState(state);
		address.setZipcode(zipcode);
		
		JSONObject json=new JSONObject(pogo1);
		list=new ArrayList<JSONObject>();
		list.add(json);
		System.out.println("json array is " +list);
		
		RequestSpecification requestspec=RestAssured.given();
		requestspec.contentType("application/json");
		requestspec.accept("application/json");
		requestspec.body(list.toString());
		System.out.println("URL is "+ServiceURL.URL);
		Response response=requestspec.post(ServiceURL.URL);
		return response;
		
	}
	
	public static void main(String[] args) {
		Service servicerequest=new Service();
		Response data=servicerequest.personcreation("name", "surname", "city", "landmark", "state", "560072");
		System.out.println("Response is  "+data.asString());
	}

}
