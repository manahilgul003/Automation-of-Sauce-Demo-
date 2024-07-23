package base;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.json.TypeToken;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import java.net.MalformedURLException;


public class BaseTests {
    private WebDriver myDriver;
    private ExtentReports extent;
    private ExtentTest test;
    static Scanner scanner = new Scanner(System.in);
    private static final String GRID_URL = "http://172.16.2.85:4444";



@BeforeSuite
    public void browserchoice() throws FileNotFoundException, MalformedURLException {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        Gson gson = new Gson();
        FileReader reader = new FileReader("C:\\Users\\Ayesha Noor\\IdeaProjects\\Assignment_Final\\src\\test\\java\\base\\config.json");
        Type credentialsType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> credentials = gson.fromJson(reader, credentialsType);
        String browser = credentials.get("browser");
        System.out.println("Browser: " + browser);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (browser.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        } else if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }


        myDriver = new RemoteWebDriver(new URL(GRID_URL), capabilities);
        if (myDriver != null) {
            myDriver.manage().window().maximize();
        }

    }

@BeforeMethod
    public void credential_read() throws IOException {
        test = extent.createTest("credential_read");

        // Read configuration from JSON file
        Gson gson = new Gson();


        // Read credentials from JSON file
        FileReader reader = new FileReader("C:\\Users\\Ayesha Noor\\IdeaProjects\\Assignment_Final\\src\\test\\java\\base\\credentials.json");
        Type credentialsType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> credentials = gson.fromJson(reader, credentialsType);
        String username = credentials.get("username");
        String password = credentials.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);


        // Perform login on the website
        myDriver.get("https://www.saucedemo.com/");
        WebElement usernameField = myDriver.findElement(By.id("user-name"));
        WebElement passwordField = myDriver.findElement(By.id("password"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        WebElement loginButton = myDriver.findElement(By.id("login-button"));
        loginButton.click();
    System.out.println("---------SuccessFully Logged in to Swag Website--------");

    }


    @Test
    @Tag("Cart")
    public void testAddAndRemoveItemsFromCart() throws IOException {

        test = extent.createTest("testAddAndRemoveItemsFromCart");


        String currentURL = myDriver.getCurrentUrl();
        test.info("Navigated to URL: " + currentURL);
//        myDriver.get("https://www.saucedemo.com/");
//        WebElement usernameField = myDriver.findElement(By.id("user-name"));
//        WebElement passwordField = myDriver.findElement(By.id("password"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");
//        WebElement loginButton = myDriver.findElement(By.id("login-button"));
//        loginButton.click();
        WebElement addtoCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addtoCartItem1.click();
        System.out.println("Cart Item 1 Added");
        WebElement addtoCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        addtoCartItem2.click();
        System.out.println("Cart Item 2 Added");
        WebElement cartBadge = myDriver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "2");
        System.out.println("Assertion passed that the cart will have 2 objects has been passed");
        WebElement removingfromCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]"));
        removingfromCartItem1.click();
        System.out.println("Cart Item 1 Removed");
        WebElement removingfromCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-bike-light\"]"));
        removingfromCartItem2.click();
        System.out.println("Cart Item 2 Removed");
        boolean cardBadge_displayed = myDriver.findElements(By.className("shopping_cart_badge")).size() > 0;
        Assert.assertFalse(cardBadge_displayed, "Cart badge is still displayed");
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).isDisplayed());
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).isDisplayed());
        System.out.println("Assertions passed that after coming to the main page the items are displayed");
    }

    @Test
    @Tag("Cart")
    public void testAddAndRemoveItemsFromProfilePage() throws IOException {
        test = extent.createTest("testAddAndRemoveItemsFromProfilePage");

        WebElement addtoCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addtoCartItem1.click();
        System.out.println("Cart Item 1 Added");
        WebElement addtpCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        addtpCartItem2.click();
        System.out.println("Cart Item 2 Added");
        WebElement cartItem1 = myDriver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        cartItem1.click();
        System.out.println("Cart Item 1 Clicked");
        WebElement removingItem1 = myDriver.findElement(By.xpath("//*[@id=\"remove\"]"));
        removingItem1.click();
        System.out.println("Cart Item 1 Removed");
        WebElement Back_button1 = myDriver.findElement(By.xpath("//*[@id=\"back-to-products\"]"));
        Back_button1.click();
        System.out.println("Back Button Clicked");
        WebElement cartItem2 = myDriver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div"));
        cartItem2.click();
        System.out.println("Cart Item 2 Clicked");
        WebElement removingItem2 = myDriver.findElement(By.xpath("//*[@id=\"remove\"]"));
        removingItem2.click();
        System.out.println("Cart Item 2 Removed");
        WebElement Back_button2 = myDriver.findElement(By.xpath("//*[@id=\"back-to-products\"]"));
        Back_button2.click();
        System.out.println("Back Button Clicked");
        boolean card_badge_displayed = myDriver.findElements(By.className("shopping_cart_badge")).size() > 0;
        Assert.assertFalse(card_badge_displayed, "Cart badge is still displayed");
        System.out.println("Assertions passed as cart badge is empty after removing from profile page");
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).isDisplayed());
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).isDisplayed());
        System.out.println("Assertions passed that after coming to the main page the items are displayed");
    }

    @Test
    @Tag("Checkout")
    public void testAddAndRemoveItemsFromCheckoutPage() {
        test = extent.createTest("testAddAndRemoveItemsFromCheckoutPage");

        WebElement addtoCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addtoCartItem1.click();
        System.out.println("Cart Item 1 Added");
        WebElement addtpCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        addtpCartItem2.click();
        System.out.println("Cart Item 2 Added");
        WebElement cartBadge = myDriver.findElement(By.className("shopping_cart_badge"));
        Assert.assertEquals(cartBadge.getText(), "2");
        System.out.println("Assertion passed as cart has two added items");
        WebElement cardIcon = myDriver.findElement(By.className("shopping_cart_link"));
        cardIcon.click();
        System.out.println("Cart Icon Clicked");
        WebElement cartItem1 = myDriver.findElement(By.xpath("//*[@id=\"item_4_title_link\"]/div"));
        WebElement cartItem2 = myDriver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div"));
        Assert.assertEquals(cartItem1.getText(), "Sauce Labs Backpack");
        Assert.assertEquals(cartItem2.getText(), "Sauce Labs Bike Light");
        System.out.println("Assertions passed as the cart has same items as added");
        WebElement removingItem1 = myDriver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]"));
        removingItem1.click();
        System.out.println("Cart Item 1 Removed");
        WebElement remobingItem2 = myDriver.findElement(By.xpath("//*[@id=\"remove-sauce-labs-bike-light\"]"));
        remobingItem2.click();
        System.out.println("Cart Item 2 Removed");
        boolean is_item1_removed = myDriver.findElements(By.xpath("//*[@id=\"remove-sauce-labs-backpack\"]")).size() == 0;
        boolean is_item2_removed = myDriver.findElements(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).size() == 0;
        Assert.assertTrue(is_item1_removed, "Backpack is still in the cart");
        Assert.assertTrue(is_item2_removed, "Bike Light is still in the cart");
        System.out.println("Assertions passed as no items in cart");
        boolean card_badge_displayed = myDriver.findElements(By.className("shopping_cart_badge")).size() > 0;
        Assert.assertFalse(card_badge_displayed, "Cart badge is still displayed");
        System.out.println("Assertion passed as no cart page as well ");
        myDriver.findElement(By.id("continue-shopping")).click();
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]")).isDisplayed());
        Assert.assertTrue(myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]")).isDisplayed());
        System.out.println("Assertions passed that after coming to the main page the items are displayed");
    }

    @Test
    @Tag("Checkout")
    void testBuying() throws IOException {
        test = extent.createTest("testBuying");

        WebElement addtoCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addtoCartItem1.click();
        System.out.println("Cart Item 1 Added");
        WebElement addtpCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        addtpCartItem2.click();
        System.out.println("Cart Item 2 Added");
        WebElement cardIcon = myDriver.findElement(By.className("shopping_cart_link"));
        cardIcon.click();
        System.out.println("Cart icon clicked");
        WebElement checkout_button = myDriver.findElement(By.xpath("//*[@id=\"checkout\"]"));
        checkout_button.click();
        System.out.println("Checkout button Clicked");
        WebElement first_Name = myDriver.findElement(By.id("first-name"));
        WebElement last_Name = myDriver.findElement(By.id("last-name"));
        WebElement postal_Code = myDriver.findElement(By.id("postal-code"));
        first_Name.sendKeys("Manahil");
        last_Name.sendKeys("Gul");
        postal_Code.sendKeys("12546");
        System.out.println("Credentials added");
        WebElement continue_Button = myDriver.findElement(By.xpath("//*[@id=\"continue\"]"));
        continue_Button.click();
        System.out.println("Continue Button Clicked");
        WebElement finish_Button = myDriver.findElement(By.xpath("//*[@id=\"finish\"]"));
        finish_Button.click();
        System.out.println("Finish Button Clicked");


    }

    @Test
    @Tag("Logout-Login")
    public void testLogoutlogin() throws IOException {
        test = extent.createTest("testLogoutlogin");

//        myDriver.get("https://www.saucedemo.com/");
//        WebElement usernameField = myDriver.findElement(By.id("user-name"));
//        WebElement passwordField = myDriver.findElement(By.id("password"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");

//        WebElement loginButton = myDriver.findElement(By.id("login-button"));
//        loginButton.click();
        WebElement addtoCartItem1 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-backpack\"]"));
        addtoCartItem1.click();
        WebElement addtpCartItem2 = myDriver.findElement(By.xpath("//*[@id=\"add-to-cart-sauce-labs-bike-light\"]"));
        addtpCartItem2.click();
        WebElement cartBadge = myDriver.findElement(By.className("shopping_cart_badge"));
        String myString = cartBadge.getText();
        WebElement threeBars = myDriver.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]"));
        threeBars.click();
        WebElement threeBars_Page = myDriver.findElement(By.cssSelector("#menu_button_container > div > div.bm-menu-wrap > div.bm-menu"));
        threeBars_Page.click();
        WebElement Logout = myDriver.findElement(By.cssSelector("#logout_sidebar_link"));
        Logout.click();
        WebElement usernameField1 = myDriver.findElement(By.id("user-name"));
        WebElement passwordField1 = myDriver.findElement(By.id("password"));
        usernameField1.sendKeys("standard_user");
        passwordField1.sendKeys("secret_sauce");
        WebElement loginButton1 = myDriver.findElement(By.id("login-button"));
        loginButton1.click();
        Assert.assertEquals(myString, "2");


    }

    @Test
    @Tag("Verify")
    public void testdropDownH_l() throws IOException {
        test = extent.createTest("testdropDownH_l");

//        myDriver.get("https://www.saucedemo.com/");
//        WebElement usernameField = myDriver.findElement(By.id("user-name"));
//        WebElement passwordField = myDriver.findElement(By.id("password"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");

//        WebElement loginButton = myDriver.findElement(By.id("login-button"));
//        loginButton.click();
        List<WebElement> my_List_BeforeSort = myDriver.findElements(By.className("inventory_item_price"));//selector for prices
        List<Double> my_priceList_BeforeSort = new ArrayList<>();
        for (WebElement a : my_List_BeforeSort) {
            my_priceList_BeforeSort.add(Double.valueOf(a.getText().replace("$", "")));
        }
        Select drop_Down = new Select(myDriver.findElement(By.className("product_sort_container")));//selector for dropdown
        drop_Down.selectByVisibleText("Price (high to low)");
        List<WebElement> My_List_AfterSort = myDriver.findElements(By.className("inventory_item_price"));
        List<Double> my_PriceList_AfterSort = new ArrayList<>();
        for (WebElement a : My_List_AfterSort) {
            my_PriceList_AfterSort.add(Double.valueOf(a.getText().replace("$", "")));
        }
        Collections.sort(my_priceList_BeforeSort);
        Collections.reverse(my_priceList_BeforeSort);
        Assert.assertEquals(my_priceList_BeforeSort, my_PriceList_AfterSort);
        System.out.println("Action completed");
    }

    @Test
    @Tag("Verify")
    public void testdropDownL_H() throws IOException {
        test = extent.createTest("testdropDownL_H");

//        myDriver.get("https://www.saucedemo.com/");
//        WebElement usernameField = myDriver.findElement(By.id("user-name"));
//        WebElement passwordField = myDriver.findElement(By.id("password"));
//        usernameField.sendKeys("standard_user");
//        passwordField.sendKeys("secret_sauce");

//        WebElement loginButton = myDriver.findElement(By.id("login-button"));
//        loginButton.click();
        List<WebElement> my_List_BeforeSort = myDriver.findElements(By.className("inventory_item_price"));//selector for prices
        List<Double> my_priceList_BeforeSort = new ArrayList<>();
        for (WebElement a : my_List_BeforeSort) {
            my_priceList_BeforeSort.add(Double.valueOf(a.getText().replace("$", "")));
        }
        Select drop_Down = new Select(myDriver.findElement(By.className("product_sort_container")));//selector for dropdown
        drop_Down.selectByVisibleText("Price (low to high)");
        List<WebElement> My_List_AfterSort = myDriver.findElements(By.className("inventory_item_price"));
        List<Double> my_PriceList_AfterSort = new ArrayList<>();
        for (WebElement a : My_List_AfterSort) {
            my_PriceList_AfterSort.add(Double.valueOf(a.getText().replace("$", "")));
        }
        Collections.sort(my_priceList_BeforeSort);

        Assert.assertEquals(my_priceList_BeforeSort, my_PriceList_AfterSort);
        System.out.println("Action completed");
    }

    @Test
    @Tag("Verify")
    public void testdropDownA_Z() throws IOException {
        test = extent.createTest("testdropDownA_Z");


        List<WebElement> my_List_BeforeSort = myDriver.findElements(By.className("inventory_item_name"));
        List<String> my_priceList_BeforeSort = new ArrayList<>();
        for (WebElement a : my_List_BeforeSort) {
            my_priceList_BeforeSort.add((a.getText()));
        }
        Select drop_Down = new Select(myDriver.findElement(By.className("product_sort_container")));
        drop_Down.selectByVisibleText("Name (A to Z)");
        List<WebElement> My_List_AfterSort = myDriver.findElements(By.className("inventory_item_name"));
        List<String> my_PriceList_AfterSort = new ArrayList<>();
        for (WebElement a : My_List_AfterSort) {
            my_PriceList_AfterSort.add((a.getText()));
        }
        Collections.sort(my_priceList_BeforeSort);

        Assert.assertEquals(my_priceList_BeforeSort, my_PriceList_AfterSort);
        System.out.println("Action completed");

    }

    @Test
    @Tag("Verify")
    public void testdropDownZ_A() throws IOException {
        test = extent.createTest("testdropDownZ_A");
        List<WebElement> my_List_BeforeSort = myDriver.findElements(By.className("inventory_item_name"));
        List<String> my_nameList_BeforeSort = new ArrayList<>();
        for (WebElement a : my_List_BeforeSort) {
            my_nameList_BeforeSort.add((a.getText()));
        }
        Select drop_Down = new Select(myDriver.findElement(By.className("product_sort_container")));
        drop_Down.selectByVisibleText("Name (Z to A)");
        List<WebElement> My_List_AfterSort = myDriver.findElements(By.className("inventory_item_name"));
        List<String> my_nameList_AfterSort = new ArrayList<>();
        for (WebElement a : My_List_AfterSort) {
            my_nameList_AfterSort.add((a.getText()));
        }
        Collections.sort(my_nameList_BeforeSort);
        Collections.reverse(my_nameList_BeforeSort);
        Assert.assertEquals(my_nameList_BeforeSort, my_nameList_AfterSort);
        System.out.println("Action completed");

    }
    @Order(1)
    @AfterEach
    void Wait() throws InterruptedException {
    Thread.sleep(2000);
    }
    @Order(2)
@AfterSuite
    void Quit(){
    myDriver.quit();
        extent.flush();
}
//public static void main(String[]args) throws IOException, InterruptedException {
//    BaseTests mytest = new BaseTests();
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testAddAndRemoveItemsFromCart();
//    //mytest.Quit();
//    mytest.Wait();
//    System.out.println("1-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testAddAndRemoveItemsFromCheckoutPage();
//   // mytest.Quit();
//    mytest.Wait();
//    System.out.println("2-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testAddAndRemoveItemsFromProfilePage();
//    //mytest.Quit();
//    mytest.Wait();
//    System.out.println("3-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testBuying();
//   // mytest.Quit();
//    mytest.Wait();
//    System.out.println("4-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testLogoutlogin();
//    //mytest.Quit();
//    mytest.Wait();
//    System.out.println("5-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testdropDownH_l();
//    //mytest.Quit();
//    mytest.Wait();
//    System.out.println("6-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testdropDownL_H();
//   // mytest.Quit();
//    mytest.Wait();
//    System.out.println("7-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testdropDownA_Z();
//   // mytest.Quit();
//    mytest.Wait();
//    System.out.println("8-DONE");
//    mytest.browserchoice();
//    mytest.credential_read();
//    mytest.testdropDownZ_A();
//
//    mytest.Wait();
//    System.out.println("9-DONE");
//    mytest.Quit();
//
//}

}



