package net.javaguides.maven_web_project.newresttest;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import au.com.bytecode.opencsv.CSVReader;



public class test1 implements ITestListener {
	

final static String ROOT_URI = "https://automationintesting.online/booking/";

//	@Test(priority=3)
	public void api_getBookings() {
		Response responseGBs = get(ROOT_URI);
		Reporter.log("Checking if 2 or more booking ids are present or note");
		String str = responseGBs.asString();
		int c = str.split("bookingid").length - 1;
		if(c>= 2){
			System.out.println("2 or more existing bookings are returned in the response");	
		}
		else {
			System.out.println("bookings returned in the response are less than 2");	
			
		}
		System.out.println("getBooking Response Code is: " + responseGBs.getStatusCode());
	}
	

	@Test(priority=1)
	public void api_createBooking() throws ParseException, NumberFormatException, IOException {
		Reporter.log("Posting the data"); 
		String CSV_PATH = "./data/restapidata.csv";
	    CSVReader reader = new CSVReader(new java.io.FileReader(CSV_PATH));	    
	    String[] data;
	    while ((data = reader.readNext()) != null) { 
		String z = "{ \"bookingdates\": { \"checkin\": "+ data[0]+", \"checkout\":"+ data[1]+"}, \"bookingid\":\"" + data[2]+"\", \"depositpaid\": "+ data[3]+", \"email\": \""+ data[4]+"\", \"firstname\":\""+ data[5]+"\", \"lastname\": \""+ data[6]+"\", \"phone\": \""+ data[7]+"\", \"roomid\": "+ data[8]+"}";
		String[] c = z.split("roomid\":");
		Pattern p = Pattern.compile("\\d+");
		Matcher room = p.matcher(c[1]);
		while (room.find()) {
		int roomid = Integer.parseInt(room.group());
		Response responseGB = get(ROOT_URI + "?roomid=" + roomid);
		System.out.println(responseGB.asString());
		boolean textcheck = responseGB.asString().contains(":"+roomid+",");
 		int statusCode = responseGB.getStatusCode();
 		if(textcheck == true) {
	 			String k[] = z.split("\":");
	 			String checkin_date = k[2].substring(2, 12);
	 			String checkout_date = k[3].substring(2, 12);
	 		    String sDate1= checkin_date;  
	 		    Date date1=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(sDate1); 
	 		    String sDate2= checkout_date; 
	 		    Date date2=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(sDate2);
	 			String resk[] = responseGB.asString().split("\":");
	 			String rescheckin_date = resk[8].substring(1, 11);
	 			String rescheckout_date = resk[9].substring(1, 11);
	 		    String resDate1= rescheckin_date; 
	 		    Date resdate1=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(resDate1); 
	 		    String resDate2= rescheckout_date;  
	 		    Date resdate2=(Date) new SimpleDateFormat("yyyy-MM-dd").parse(resDate2);
	 		    if( date1.compareTo(date2) > 0 || (resdate1.compareTo(date2) < 0 && date2.compareTo(resdate2) <0)) {
	 		        Response responsenew = given().
	 						contentType(ContentType.JSON)
	 						.accept(ContentType.JSON)
	 						.body(z)
	 						.when()
	 						.post(ROOT_URI);
		 		 	int statusCoden = responsenew.getStatusCode();
		 		 	try {
		 		 		System.out.println(statusCoden);
		 		 		Assert.assertEquals(statusCoden, 409);
		 		     } catch (AssertionError e) {
		 		    	Reporter.log("Duplicated entry added"); }
		 		 	}
	 		    else {
	 		        Response responsenew = given().
	 						contentType(ContentType.JSON)
	 						.accept(ContentType.JSON)
	 						.body(z)
	 						.when()
	 						.post(ROOT_URI);
	 				System.out.println("createBooking Response : \n" + responsenew.asString());
	 		 		int statusCode2 = responsenew.getStatusCode();
	 		 		System.out.println(statusCode2);}
	 	}
			else{
 		        Response responsenew = given().
 						contentType(ContentType.JSON)
 						.accept(ContentType.JSON)
 						.body(z)
 						.when()
 						.post(ROOT_URI);	
 		 		int statusCode2 = responsenew.getStatusCode();
 		 		System.out.println(statusCode2);
			}}}
	    }


		@Test(priority=2)
		public void api_getBooking() {
			Reporter.log("Validating the response with roomid = 1");
			Response responseGB = get(ROOT_URI + "1");
			//System.out.println(responseGB.asString());
			System.out.println("getBooking Response Code is: " + responseGB.getStatusCode());}
	
}




		
