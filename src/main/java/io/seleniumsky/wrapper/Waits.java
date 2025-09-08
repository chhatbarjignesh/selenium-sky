package io.seleniumsky.wrapper;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {

	private WebDriver driver;
	private final int DEFAULT_TIMEOUT = 20; // default seconds

	/** Supported wait conditions */
	public enum Condition {
		VISIBLE, CLICKABLE, PRESENT, INVISIBLE
	}

	public Waits(WebDriver driver) {
		this.driver = driver;
	}

	/** Wait for element with default timeout */
	public WebElement waitForElement(Element element, Condition condition) {
		return waitForElement(element, condition, DEFAULT_TIMEOUT);
	}

	/** Wait for element with custom timeout in seconds */
	public WebElement waitForElement(Element element, Condition condition, int timeoutSec) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
		By locator = element.getByObject();

		switch (condition) {
			case VISIBLE :
				return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			case CLICKABLE :
				return wait.until(ExpectedConditions.elementToBeClickable(locator));
			case PRESENT :
				return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			case INVISIBLE :
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
				return null; // invisible waits do not return element
			default :
				throw new IllegalArgumentException("Unsupported wait condition: " + condition);
		}
	}

	/** Convenience overload for Element + INVISIBLE without timeout */
	public void waitForInvisible(Element element) {
		waitForElement(element, Condition.INVISIBLE, DEFAULT_TIMEOUT);
	}

	/** Convenience overload for Element + INVISIBLE with custom timeout */
	public void waitForInvisible(Element element, int timeoutSec) {
		waitForElement(element, Condition.INVISIBLE, timeoutSec);
	}

	/** Sleep helper (optional) */
	public void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
