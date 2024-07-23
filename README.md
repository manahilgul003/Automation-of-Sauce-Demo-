# Automation-of-Sauce-Demo-
# Setting-up-BaseTest
# Things-needed-for-setting-up
1. Java Developement Kit (8 or higher)-in my case the version is 21.
2. All the setting up is done on a Maven Project
3. WebDriver binaries: Ensure you have the WebDriver binaries (e.g., chromedriver for Chrome(given above), geckodriver for Firefox(given above) downloaded and available in your system's PATH.
4. Selenium Server 4.22.0 should be in the project directory.
5. Selenium Grid: Ensure you have a Selenium Grid running at the specified URL (http://172.16.2.85:4444 in this case).
6. You should have the dependencies given in pom.xml file given above to have certain libraries.
7. Make sure you clean and install these dependencies and click the refresh button.
8. Make a test package in src/test/java named base.
9. Create a BaseTests Class in the base package.
10. Make sure that your config.json and credentials.json file is in the package dir:src/test/java/base
# Running Tests
1. We have a BaseTests Class.
2. We run the BaseTests Class in which we have 9 further tests to be tested.
3. 9 Tests will run and we are using the Selenium Grid,so make sure you are running it the specified url by running the command java -jar selenium-version-4.22.0.jar
4. When Tests will run and an extent-html report will be generated in the project directory.
5. Open the file in file explorer which will open the file in browser.
6. There you will see the reporting on test being passed or failed.
7. In this test class the browserchoice() method is annotated with @BeforeSuite() annotation rather because it will be run only one time but credential_read() method is annotated with @BeforeMethod() because credentials are needed in every Test.
   
----------------------------------------------------------------------------------------The End-------------------------------------------------------------------------------------











































