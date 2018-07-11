package com.Selenium.APITest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.Selenium.JsonRequest.Address;
import com.Selenium.JsonRequest.CreatePerson;
import com.Selenium.JsonResponse.CreateResponse;
import com.google.gson.Gson;

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
		
		RequestSpecification requestspecification=RestAssured.given();
		requestspecification.contentType("application/json");
		requestspecification.accept("application/json");
		requestspecification.body(list.toString());
		System.out.println("URL is "+ServiceURL.URL);
		Response response=requestspecification.post(ServiceURL.URL);
		return response;
		
	}
	
	public static void main(String[] args) {
		Service servicerequest=new Service();
		Response data=servicerequest.personcreation("name", "surname", "city", "landmark", "state", "560074");
		System.out.println("Response is  "+data.asString());
		Gson gson=new Gson();
		CreateResponse createresponse=gson.fromJson(data.asString(), CreateResponse.class);
		System.out.println(createresponse.getResponse().get(0).getName());
		System.out.println(createresponse.getResponse().get(0).getSurname());
		System.out.println(createresponse.getResponse().get(0).getAddress().getCity());
		System.out.println(data.getStatusCode());
		
	}

}
