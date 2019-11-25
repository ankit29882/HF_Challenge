This solution is built using Maven(v4.0.0)- TestNG(v7.0.0) Framework plugin for eclipse(version 4.11.0).
-In order to run this project:You need to have Eclipse and TestNG installed, with Maven dependencies -org.testng(v7.0.0) for TestNG, com.googlecode.json-simple(v1.1.1) for JSON , io.rest-assured(v4.1.2) for RestAssured API and au.com.bytecode(v2.4) for CSV included in the ./src/target/pom.xml file of your project.
Features of this solution:
 >Uses Maven pom for dependant libraries
 >Uses default generated report
 >test1.java file contains 3 tests[ api_createBooking, api_getBooking and api_getBookings ]
 >Data is posted at the end point (URI)- "https://automationintesting.online/booking/" 
 >Reads the data from csv file [./dataset/restapidata.csv]
 >Verifying if 2 or more entries are present.
 >Compare if the data returned for an existing booking matches.
 > Tests are designed to run in a sequenced manner by setting up the Priority.
 > A testing.xml file is used to run the entire Test Suite and create an test-output folder containing the reports.
 > The reports generated are stored in [{projectname}/test-output/.].
 >Exception handling for duplicate values that get posted sometimes.


