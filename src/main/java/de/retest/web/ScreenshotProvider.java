package de.retest.web;

import static de.retest.recheck.ui.image.ImageUtils.extractScale;
import static de.retest.recheck.ui.image.ImageUtils.resizeImage;

import java.awt.image.BufferedImage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

public class ScreenshotProvider {

	private static final int SCROLL_TIMEOUT = 100;
	private static final boolean USE_DEVICE_PIXEL_RATIO = true;

	public static final int SCALE = extractScale();

	private ScreenshotProvider() {
		// private constructor for util class
	}

	public static BufferedImage shootFullPage( final WebDriver driver ) {
		final BufferedImage image;
		if ( driver instanceof ChromeDriver ) {
			image = Shutterbug
					.shootPage( driver, ScrollStrategy.WHOLE_PAGE_CHROME, SCROLL_TIMEOUT, USE_DEVICE_PIXEL_RATIO )
					.getImage();
			return resizeImage( image, image.getWidth() / SCALE, image.getHeight() / SCALE );
		} else {
			image = Shutterbug
					.shootPage( driver, ScrollStrategy.BOTH_DIRECTIONS, SCROLL_TIMEOUT, USE_DEVICE_PIXEL_RATIO )
					.getImage();
		}
		return image;
	}
}
