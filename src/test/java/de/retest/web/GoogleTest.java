package de.retest.web;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckImpl;

public class GoogleTest {
	WebDriver driver;
	Recheck re;

	@Test
	public void google() throws Exception {
		re = new RecheckImpl();

		System.setProperty( "de.retest.ignoredAttributes", "absolute-outline" );
		System.setProperty( "webdriver.chrome.driver", "src/test/resources/chromedriver" );
		driver = new ChromeDriver();

		driver.get( "http://www.google.com" );
		Thread.sleep( 5000 );

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
