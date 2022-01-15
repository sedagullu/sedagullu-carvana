package scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Waiter;

import java.util.List;

public class CarvanaTest extends Base {
    /*\
    Test Case 1: Test name = Validate Carvana home page title and url
    Test priority = 1
    Given user is on "https://www.carvana.com/"
    Then validate title equals to "Carvana | Buy & Finance Used Cars Online | At Home Delivery"
    And validate url equals to "https://www.carvana.com/"

     */
    @Test(testName = "Validate Carvana home page title and url", priority = 1)
    public void ValidateTitle() {
        driver.get("https://www.carvana.com/");
        Assert.assertEquals(driver.getTitle(), "Carvana | Buy & Finance Used Cars Online | At Home Delivery", "Title validation failed");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/", "Url validation failed");
    }

    /*
    Test Case 2: Test name = Validate the Carvana logo
    Test priority = 2
    Given user is on "https://www.carvana.com/"
    Then validate the logo of the “Carvana” is displayed
     */

    @Test(testName = "Validate the Carvana logo", priority = 2)
    public void ValidateLogo() {
        driver.get("https://www.carvana.com/");
        WebElement carvanaLogo = driver.findElement(By.cssSelector("div[data-qa='logo-wrapper']"));
        Assert.assertTrue(carvanaLogo.isDisplayed(), "Carvana");
    }

    /*
    Test Case 3: Test name = Validate the main navigation section items
    Test priority = 3
    Given user is on "https://www.carvana.com/"
    Then validate the navigation section items below are displayed
    |HOW IT WORKS      |
    |ABOUT CARVANA     |
    |SUPPORT & CONTACT |
     */

    @Test(testName = "Validate the main navigation section items", priority = 3)
    public void ValidateNavigationSection() {
        driver.get("https://www.carvana.com/");

        List<WebElement> navigationSection = driver.findElements(By.xpath("//a/*[contains(data-qa,'menu-title')]"));
        String[] expectedNavigationSection = {"HOW IT WORKS", "ABOUT CARVANA", "SUPPORT & CONTACT"};
        for (int i = 0; i < navigationSection.size(); i++) {
            Assert.assertEquals(navigationSection.get(i).getText(), expectedNavigationSection[i]);
        }
    }

    /*
    Test Case 4: Test name = Validate the main navigation section items
    Test priority = 4
    Given user is on "https://www.carvana.com/"
    When user clicks on “SIGN IN” button
    Then user should be navigated to “Sign in” modal
    When user enters email as “johndoe@gmail.com”
    And user enters password as "abcd1234"
    And user clicks on "SIGN IN" button
    Then user should see error message as "Email address and/or password combination is incorrect
    Please try again or reset your password."
     */
    @Test(testName = "Validate the sign in error message", priority = 4)
    public void ValidateSignInButton() {
        driver.get("https://www.carvana.com/");

        driver.findElement(By.xpath("//a[@role='button']")).click();
        WebElement signInModal = driver.findElement(By.xpath("//div[@class='modalScroll-qk9d85-4 klkGgp']"));
        Assert.assertTrue(signInModal.isDisplayed());

        List<WebElement> signInBoxes = signInModal.findElements(By.xpath("//div[@data-qa='account']/div/div"));
        String[] modalBoxsKeys = {"johndoe@gmail.com", "abcd1234"};
        for (int i = 0; i < signInBoxes.size(); i++) {
            signInBoxes.get(i).sendKeys(modalBoxsKeys[i]);
        }
        driver.findElement(By.xpath("//button[@data-cv='sign-in-submit']")).click();
        WebElement errorMessage = driver.findElement(By.xpath("//*[@data-qa='error-message-container']"));
        Assert.assertEquals(errorMessage.getText(), "Email address and/or password combination is incorrect\n" +
                "        Please try again or reset your password.");

    }

    /*
    Test Case 5: Test name = Validate the search filter options and search button
    Test priority = 5
    Given user is on "https://www.carvana.com/"
    When user clicks on "SEARCH ALL VEHICLES" link
    Then user should be routed to "https://www.carvana.com/cars"
    And user should see a search input box
    And user should see filter options
    |PAYMENT & PRICE      |
    |MAKE & MODEL      |
    |BODY TYPE |
    |YEAR & MILEAGE      |
    |FEATURES      |
    |MORE FILTERS |
    When user enters "Tesla" to the search input box
    Then validate "GO" button in the search input box is displayed as expected
     */
    @Test(testName = "Validate the search filter options and search button", priority = 5)
    public void ValidateSearchFilterAndButton() {
        driver.get("https://www.carvana.com/");
        driver.findElement(By.linkText("/cars")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars");

        WebElement searchInputBox = driver.findElement(By.xpath("//input[@type='text']"));
        Assert.assertTrue(searchInputBox.isDisplayed());

        List<WebElement> filterOptions = driver.findElements(By.xpath("//span[@data-qa='label-text']"));
        String[] expectedFilters = {"PAYMENT & PRICE", "MAKE & MODEL", "BODY TYPE", "YEAR & MILEAGE", "FEATURES", "MORE FILTERS"};

        for (int i = 0; i < filterOptions.size(); i++) {
            Assert.assertEquals(filterOptions.get(i).getText(), expectedFilters[i]);
        }
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("Tesla");
        WebElement goButton = driver.findElement(By.xpath("//button[@data-qa='go-button']"));
        Assert.assertTrue(goButton.isDisplayed());
        Assert.assertTrue(goButton.isSelected());
    }

    /*
    Test Case 6: Test name = Validate the search result tiles
    Test priority = 6
    Given user is on "https://www.carvana.com/"
    When user clicks on "SEARCH ALL VEHICLES" link
    Then user should be routed to "https://www.carvana.com/cars"
    When user enters "mercedes-benz" to the search input box
    And user clicks on "GO" button in the search input box
    Then user should see "mercedes-benz" in the url
    And validate each result tile
    VALIDATION OF EACH TILE INCLUDES BELOW
    Make sure each result tile is displayed with below information
    1. an image
    2. add to favorite button
    3. tile body
    ALSO VALIDATE EACH TILE BODY:
    Make sure each tile body has below information
    1. Inventory type - text should be displayed and should not be null
    2. Year-Make-Model information - text should be displayed and should not be null
    3. Trim-Mileage information - text should be displayed and should not be null
    4. Price - Make sure that each price is more than zero
    5. Trim-Mileage information - text should be displayed and should not be null
    6. Monthly Payment information - text should be displayed and should not be null
    7. Down Payment information - text should be displayed and should not be null
    8. Delivery chip must be displayed as “Free Shipping”
     */
    @Test(testName = "Validate the search result tiles", priority = 6)
    public void ValidateSearchResultTiles() {
        driver.get("https://www.carvana.com/");
        driver.findElement(By.linkText("/cars")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.carvana.com/cars");
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("mercedes-benz");
        driver.findElement(By.xpath("//button[@data-qa='go-button']")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("mercedes-benz"));

        List<WebElement> images = driver.findElements(By.xpath("//picture[@data-qa='base-vehicle-image']"));
        List<WebElement> favButton = driver.findElements(By.xpath("//div[@data-qa='base-favorite-vehicle']"));
        List<WebElement> tileBody = driver.findElements(By.xpath("//div[@class='result-tile']"));
        List<WebElement> inventoryType = driver.findElements(By.xpath("//div[@data-test='BaseInventoryType']"));
        List<WebElement> yearMakeModel = driver.findElements(By.xpath("//div[@class='year-make-experiment']"));
        List<WebElement> trimMileage = driver.findElements(By.xpath("//div[@data-test='TrimMileage']"));
        List<WebElement> price = driver.findElements(By.xpath("//div[@class='price-variant']"));
        List<WebElement> monthlyPayment = driver.findElements(By.xpath("//div[@data-test='MonthlyPayment']"));
        List<WebElement> downPayment = driver.findElements(By.xpath("//div[@class='down-payment']"));
        List<WebElement> deliveryChip = driver.findElements(By.xpath("//div[@data-test='PlainDeliveryChip']"));


        for (int i = 0; i < images.size(); i++) {
            Assert.assertTrue(images.get(i).isDisplayed(), "Validation of  image \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(favButton.get(i).isDisplayed(), "Validation of  favorite button \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(tileBody.get(i).isDisplayed(), "Validation of  tile body \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(inventoryType.get(i).isDisplayed() && !inventoryType.isEmpty(), "Validation of  inventory type \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(yearMakeModel.get(i).isDisplayed() && !yearMakeModel.isEmpty(), "Validation of  inventory type \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(trimMileage.get(i).isDisplayed() && !trimMileage.isEmpty(), "Validation of  inventory type \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(Integer.parseInt(price.get(i).getText().replaceAll("[^0-9]", "")) > 0, "Validation of price \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(monthlyPayment.get(i).isDisplayed() && !monthlyPayment.isEmpty(), "Validation of monthly payment \"" + (i + 1) + "\" FAILED");
            Assert.assertTrue(downPayment.get(i).isDisplayed() && !downPayment.isEmpty(), "Validation of down payment \"" + (i + 1) + "\" FAILED");
            Assert.assertEquals(deliveryChip.get(i).getText(), "Free Shipping");
        }

    }
}

