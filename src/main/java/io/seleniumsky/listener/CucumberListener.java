package io.seleniumsky.listener;

import io.cucumber.java.*;
import io.seleniumsky.factory.DriverFactory;
import io.seleniumsky.loggers.FrameworkLogger;
import io.seleniumsky.loggers.LoggerInit;
import io.seleniumsky.wrapper.WebAction;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class CucumberListener {

	@Before
	public void beforeScenario(Scenario scenario) {
		LoggerInit.init();
		// Set scenario for current thread
		DriverFactory.getThread().setScenario(scenario);
		FrameworkLogger.info("=== Starting Scenario: " + scenario.getName() + " ===");
	}

	@After
	public void afterScenario(Scenario scenario) {
		if (scenario.isFailed()) {
			FrameworkLogger.error("Scenario failed: " + scenario.getName());
			FrameworkLogger.captureScreenshot(DriverFactory.getDriver(),
					"Failure_" + scenario.getName().replaceAll(" ", "_"));
		} else {
			FrameworkLogger.info("Scenario passed: " + scenario.getName());
		}
		FrameworkLogger.info("=== Finished Scenario: " + scenario.getName() + " ===");

		DriverFactory.getThread().quitDriver();
	}
}
