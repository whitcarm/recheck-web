package de.retest.web.it;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckImpl;
import de.retest.web.testutils.WebDriverFactory;
import de.retest.web.testutils.WebDriverFactory.Driver;

public class WikipediaJavaIT {

	private WebDriver driver;
	private Recheck re;

	@Before
	public void setup() {
		driver = WebDriverFactory.driver( Driver.CHROME );
		re = new RecheckImpl();
	}

	@Test
	public void index() throws Exception {
		re.startTest( "wikipedia" );

		driver.get( "https://en.wikipedia.org/wiki/Java" );

		re.check( driver, "index" );

		re.capTest();
	}

	@After
	public void tearDown() {
		driver.quit();
		re.cap();
	}

}
