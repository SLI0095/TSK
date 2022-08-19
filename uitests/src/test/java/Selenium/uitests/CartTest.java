package Selenium.uitests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  
  @BeforeEach
  public void setUp() {
	System.setProperty("webdriver.chrome.driver","E:\\chromedriver.exe");
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  
  //@ParameterizedTest(name = "Ammount - {0}, Price - {1}")
  @MethodSource("AmountSource")
  public void EditingCart(String quantity, String expectedValue) {
    driver.get("https://436498.myshoptet.com/");
    driver.findElement(By.linkText("Oblečení")).click();
    driver.findElement(By.linkText("Dětské")).click();
    driver.findElement(By.cssSelector("a[href='/columbia-squish-n-stuff/']")).click();
    driver.findElement(By.cssSelector(".btn-lg")).click();
    WebDriverWait driverWait = new WebDriverWait(driver, 10);
    driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".msg")));
    driverWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".msg"))));
    driver.findElement(By.cssSelector(".btn-icon")).click();
    driver.findElement(By.name("amount")).clear();
    driver.findElement(By.name("amount")).click();
    driver.findElement(By.name("amount")).sendKeys(quantity);
    driver.findElement(By.name("amount")).sendKeys(Keys.RETURN);
    driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".msg")));
    driverWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".msg"))));
    assertEquals(expectedValue, driver.findElement(By.cssSelector("strong.price.price-primary")).getText());
  }
  
  public static Stream<Arguments> AmountSource() {
		return Stream.of(
				//valid
				Arguments.of("1","400 Kč"),
				Arguments.of("5","2 000 Kč"),
				Arguments.of("10","4 000 Kč"),
				Arguments.of("1000","400 000 Kč"),
				Arguments.of("9999999","3 999 999 600 Kč"),
				//out of range
				Arguments.of("0","0 Kč"),
				Arguments.of("10000000","4 000 000 000 Kč"),
				//added characters
				Arguments.of("05","2 000 Kč"),
				Arguments.of("+5","2 000 Kč"),
				Arguments.of("5.0","2 000 Kč"),
				Arguments.of("5,0","2 000 Kč"),
				Arguments.of(" 5","2 000 Kč"),
				Arguments.of("5 ","2 000 Kč"),
				Arguments.of("1 000","400 000 Kč"),
				Arguments.of("1,000","400 000 Kč")
				);
  }
  
  //@Test
  public void CreatingOrder() {
    driver.get("https://436498.myshoptet.com/");
    driver.findElement(By.linkText("Do domácnosti")).click();
    //Do domacnosti
    assertEquals("Do domácnosti", driver.findElement(By.className("category-title")).getText());
    driver.findElement(By.cssSelector("a[href='/linteo-box-kapesniku/']")).click();
    //Linteo box kapesniku
    assertEquals("Linteo box kapesníků",driver.findElement(By.tagName("h1")).getText());
    driver.findElement(By.id("simple-variants-select")).click();
    {
      WebElement dropdown = driver.findElement(By.id("simple-variants-select"));
      dropdown.findElement(By.cssSelector("option[data-index='2']")).click();
    }
    driver.findElement(By.cssSelector(".add-to-cart-button")).click();
    WebDriverWait driverWait = new WebDriverWait(driver, 10);
    driverWait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".msg-success")));
    driverWait.until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".msg-success"))));
    driver.findElement(By.cssSelector(".btn-icon")).click();
    //Kosik
    assertTrue(driver.findElement(By.cssSelector("li.step.step-1.active")).isDisplayed());
    driver.findElement(By.id("continue-order-button")).click();
    //Doprava a platba
    assertTrue(driver.findElement(By.cssSelector("li.step.step-1.completed")).isDisplayed());
    assertTrue(driver.findElement(By.cssSelector("li.step.step-2.active")).isDisplayed());
    driver.findElement(By.id("shipping-6")).click();
    driver.findElement(By.cssSelector("div[data-id='billing-3']")).click();
    driver.findElement(By.id("orderFormButton")).click();
    //Informace
    assertTrue(driver.findElement(By.cssSelector("li.step.step-1.completed")).isDisplayed());
    assertTrue(driver.findElement(By.cssSelector("li.step.step-2.completed")).isDisplayed());
    assertTrue(driver.findElement(By.cssSelector("li.step.step-3.active")).isDisplayed());
    driver.findElement(By.id("billFullName")).clear();
    driver.findElement(By.id("billFullName")).sendKeys("Marek Vyskočil");
    driver.findElement(By.id("email")).clear();
    driver.findElement(By.id("email")).sendKeys("marek@vyskocil.cz");
    driver.findElement(By.id("phone")).clear();
    driver.findElement(By.id("phone")).sendKeys("605872352");
    driver.findElement(By.id("billStreet")).clear();
    driver.findElement(By.id("billStreet")).sendKeys("Na Vavřínech 25");
    driver.findElement(By.id("billCity")).clear();
    driver.findElement(By.id("billCity")).sendKeys("Kolín");
    driver.findElement(By.id("billZip")).clear();
    driver.findElement(By.id("billZip")).sendKeys("70010");
    driver.findElement(By.cssSelector("label[for='add-note']")).click();
    driver.findElement(By.id("remark")).sendKeys("Můžete doručit i o víkendu?");
    driver.findElement(By.id("submit-order")).click();
    //Odeslani objednavky
    assertEquals("OBJEDNÁVKA ODESLÁNA", driver.findElement(By.className("order-summary-heading")).getText());
    driver.findElement(By.cssSelector(".btn-primary")).click();
    //Navrat na uvodni stranku
    assertEquals("Akční zboží", driver.findElement(By.className("homepage-products-heading-1")).getText());
  }
  
  @ParameterizedTest(name = "email - {0}, password - {1}, expectingCompletion - {2}")
  @MethodSource("RegistrationAndLoginSource")
  public void RegistrationAndLogin(String email, String password, boolean expectedCompletion){
    driver.get("https://436498.myshoptet.com/");
    driver.findElement(By.linkText("Přihlášení")).click();
    driver.findElement(By.linkText("Nová registrace")).click();
    //Registrace
    assertEquals("Registrace",driver.findElement(By.tagName("h1")).getText());
    driver.findElement(By.id("email")).click();
    driver.findElement(By.id("email")).sendKeys(email);
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys(password);
    driver.findElement(By.id("passwordAgain")).click();
    driver.findElement(By.id("passwordAgain")).sendKeys(password);
    driver.findElement(By.cssSelector("input[value='Registrovat'")).click();
    if(driver.findElement(By.tagName("h1")).getText().equals("Nastavení mého účtu"))
    {
    	driver.findElement(By.className("btn-primary")).click();
    	//Navrat na uvodni stranku
    	WebDriverWait driverWait = new WebDriverWait(driver, 10);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(By.className("homepage-products-heading-1")));
        assertEquals("Akční zboží", driver.findElement(By.className("homepage-products-heading-1")).getText());    	
    	driver.findElement(By.linkText("Přihlášení")).click();
    	driver.findElement(By.name("email")).click();
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).click();
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.className("btn-login")).click();
        //Registrace se zdarila i s opetovnym prihlasenim
        assertEquals(expectedCompletion, driver.findElement(By.tagName("h1")).getText().equals("Klientské centrum"));
    }
    else
    {

    	//Registrace se nezdarila
    	 assertEquals(expectedCompletion, !(driver.findElement(By.tagName("h1")).getText().equals("Registrace")));
    }
  }
  
  public static Stream<Arguments> RegistrationAndLoginSource() {
		return Stream.of(
				//invalid
				Arguments.of("test","55533", false),
				Arguments.of("test@","55533", false),
				Arguments.of("@example.com","55533", false),
				Arguments.of("test test@example.com","55533", false),
				//invalid - already registered
				Arguments.of("testa@example.com","55533", false),
				//valid - only characters
				Arguments.of("testc@example.com","55533", true),
				//valid - characters and numbers
				Arguments.of("test3@example.com","55533", true),
				//valid - long
				Arguments.of("testtesttesttesttesttesttesttest1111111111111113@example.com","55533", true),
				//invalid - long
				Arguments.of("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest1111111111111111@example.com","55533", false),
				//valid - dots
				Arguments.of("test.test.C@example.com","55533", true),
				//invalid - dots
				Arguments.of(".test@example.com","55533", false),
				Arguments.of("test.@example.com","55533", false),
				//valid - only special characters
				Arguments.of("#%&!!!@example.com","55533", true),
				//invalid - bad domain
				Arguments.of("testa@example.grz","55533", false)
				);
}
}
