package de.retest.web;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.retest.recheck.GoldenMaster;
import de.retest.recheck.RecheckAdapter;
import de.retest.recheck.ui.DefaultValueFinder;
import de.retest.recheck.ui.descriptors.RetestIdProviderUtil;
import de.retest.recheck.ui.descriptors.RootElement;
import de.retest.recheck.ui.descriptors.idproviders.RetestIdProvider;

public class RecheckSeleniumAdapter implements RecheckAdapter {

	public static final RetestIdProvider idProvider = RetestIdProviderUtil.getConfiguredRetestIdProvider();

	private static final String GET_ALL_ELEMENTS_BY_PATH_JS_PATH = "/javascript/getAllElementsByPath.js";

	private static final Logger logger = LoggerFactory.getLogger( RecheckSeleniumAdapter.class );

	private final DefaultValueFinder defaultWebValueFinder = new DefaultWebValueFinder();

	public RecheckSeleniumAdapter() {
		logger.debug( "New RecheckSeleniumAdapter created: {}.", System.identityHashCode( this ) );
	}

	@Override
	public boolean canCheck( final Object toVerify ) {
		return toVerify instanceof WebDriver;
	}

	@Override
	public Set<RootElement> convert( final Object toVerify ) {
		final WebDriver driver = (WebDriver) toVerify;

		logger.info( "Retrieving attributes for each element." );
		final List<String> cssAttributes = AttributesProvider.getInstance().getCssAttributes();
		final JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		final String result = jsExecutor.executeScript( getQueryJS(), cssAttributes ).toString();

		final ObjectMapper mapper = new ObjectMapper();

		try {
			final GoldenMaster goldenMaster = mapper.readValue( result, GoldenMaster.class );
			logger.info( "Golden master: {}", goldenMaster );
		} catch ( final IOException e ) {
			logger.error( "Error reading json.", e );
		}

		logger.info( "Checking website {}.", driver.getCurrentUrl() );

		//		final RootElement lastChecked = convertToPeers( result, driver.getTitle(), shootFullPage( driver ) );
		//		if ( driver instanceof RecheckDriver ) {
		//			((RecheckDriver) driver).setLastActualState( lastChecked );
		//		}

		return null;

	}

	public String getQueryJS() {
		try ( final InputStream url = getClass().getResourceAsStream( GET_ALL_ELEMENTS_BY_PATH_JS_PATH ) ) {
			return String.join( "\n", IOUtils.readLines( url, StandardCharsets.UTF_8 ) );
		} catch ( final IOException e ) {
			throw new UncheckedIOException( "Exception reading '" + GET_ALL_ELEMENTS_BY_PATH_JS_PATH + "'.", e );
		}
	}

	public RootElement convertToPeers( final Map<String, Map<String, Object>> data, final String title,
			final BufferedImage screenshot ) {
		return new PeerConverter( idProvider, data, title, screenshot ).convertToPeers();
	}

	@Override
	public DefaultValueFinder getDefaultValueFinder() {
		return defaultWebValueFinder;
	}

}
