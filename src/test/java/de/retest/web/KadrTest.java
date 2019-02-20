package de.retest.web;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckImpl;

public class KadrTest {
	WebDriver driver;
	Recheck re;

	@Test
	public void kard_page() throws Exception {
		re = new RecheckImpl();

		System.setProperty( "webdriver.chrome.driver", "src/test/resources/chromedriver" );
		driver = new ChromeDriver();

		driver.get( "http://www.kadr-llc.com" );
		Thread.sleep( 5000 );

		driver.findElement( By.xpath( "/html/body/header/nav/div/div[2]/ul/li[5]/a" ) ).click();
		driver.findElement( By.xpath( "/html/body/div[2]/div/div/div/div[1]/div[2]/div/div[2]/a[2]" ) ).click();
		re.check( driver, "open" );
		re.capTest();
	}

	@After
	public void tearDown() {
		driver.quit();
		// Produce the result file.
		re.cap();
	}
}
