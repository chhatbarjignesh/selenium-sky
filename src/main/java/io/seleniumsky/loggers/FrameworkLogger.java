package io.seleniumsky.loggers;

import com.epam.reportportal.service.ReportPortal;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FrameworkLogger {

	private static final Logger logger = LogManager.getLogger("Framework");

	public static void info(String message) {
		logger.info(message);
		Allure.step(message);
		ReportPortal.emitLog(message, "INFO", new Date()); // ReportPortal
	}

	public static void debug(String message) {
		logger.debug(message);
		// Debug is usually too noisy for Allure/Extent, so keep it only in log file
	}

	public static void error(String message) {
		logger.error(message);
		Allure.step("ERROR: " + message);
		ReportPortal.emitLog("ERROR: " + message, "ERROR", new Date());

	}

	// Attach screenshot to Allure & ReportPortal
	public static void attachScreenshot(String name, byte[] screenshotBytes) {
		// Allure attachment
		Allure.addAttachment(name, new ByteArrayInputStream(screenshotBytes));

		// ReportPortal attachment
		try {
			File file = saveScreenshotToFile(screenshotBytes, name);
			ReportPortal.emitLog("Screenshot: " + name, "INFO", new Date(), file);
			file.deleteOnExit();
		} catch (IOException e) {
			logger.error("Failed to save screenshot for ReportPortal", e);
		}
	}

	// Save screenshot bytes to temporary file
	private static File saveScreenshotToFile(byte[] screenshotBytes, String name) throws IOException {
		File tempFile = File.createTempFile(name, ".png");
		try (FileOutputStream fos = new FileOutputStream(tempFile)) {
			fos.write(screenshotBytes);
		}
		return tempFile;
	}

	// Convenience method: capture screenshot from WebDriver
	public static void captureScreenshot(WebDriver driver, String name) {
		byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		attachScreenshot(name, screenshot);
	}
}
