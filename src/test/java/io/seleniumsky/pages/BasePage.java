package io.seleniumsky.pages;

import io.seleniumsky.factory.DriverFactory;
import io.seleniumsky.loggers.FrameworkLogger;
import io.seleniumsky.wrapper.Waits;
import io.seleniumsky.wrapper.WebAction;
import org.openqa.selenium.WebDriver;

public class BasePage {

	public void logData(String logData) {
		FrameworkLogger.info(logData);
	}

	public WebDriver driver() {
		return DriverFactory.getDriver();
	}

	public WebAction getAction() {
		return DriverFactory.getThread().getWebAction();
	}

	public Waits getWaits() {
		return DriverFactory.getThread().getWaits();
	}
}
