This solution is built using TestNG Framework plugin for eclipse(version 6.14 [version 7.x is not compatible with Extent Report]).
-In order to run this project:You need to have Eclipse with TestNG(v6.14) installed on your machine.
Features of this solution:
 > Enabled logging
 > Takes screenshot for failed tests automatically[Under ./Screenshots/]
 > human readable report is generated [./test-output/HF_WebAutiomation_Report.html]
 > Reads values for new user registeration from a customizable csv file[Under ./DataFiles/SignUpTestData.csv]
 > SetUp, Actions on page and Test Data are in separate blocks
 > An xml file is used configurator to easily customize test execution configuration
 > Test is designed to run in paralell mode by using Xml Configurator file
 > Different browser(Firefox and Chrome implemented for PoC) can be passed from XML configurator file
 > Different environment url can be passed from XML configurator file
 > Solution reads test data from CSV file for passing cusomized dress name,size and color for CheckOut test
 > All test cases can be executed from command line by giving the location of testNG jar file,Compiled classes path and the Xml configurator file[as per your system]

