package io.seleniumsky.wrapper;

import java.util.Set;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class WebAction {

	private WebDriver driver;
	private Waits waits;
	private Actions actions;

	public WebAction(WebDriver driver) {
		this.driver = driver;
		this.waits = new Waits(driver);
		this.actions = new Actions(driver);
	}

	// ---------------- Basic Actions ----------------

	public WebAction click(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.CLICKABLE);
		e.click();
		return this;
	}

	public WebAction type(Element element, String text) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		e.clear();
		e.sendKeys(text);
		return this;
	}

	public WebAction findElement(Element element) {
		waits.waitForElement(element, Waits.Condition.PRESENT);
		return this;
	}

	public boolean isElementPresent(Element element) {
		try {
			waits.waitForElement(element, Waits.Condition.PRESENT);
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getText(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		return e.getText();
	}

	public WebAction scrollToElement(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", e);
		return this;
	}

	public WebAction doubleClick(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.CLICKABLE);
		actions.doubleClick(e).perform();
		return this;
	}

	public WebAction javascriptClick(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", e);
		return this;
	}

	public WebAction goToUrl(String url) {
		driver.get(url);
		return this;
	}

	public WebAction navigateToUrl(String url) {
		driver.navigate().to(url);
		return this;
	}

	public WebAction acceptAlert() {
		driver.switchTo().alert().accept();
		return this;
	}

	public WebAction refreshCurrentPage() {
		driver.navigate().refresh();
		return this;
	}

	public WebAction navigateBack() {
		driver.navigate().back();
		return this;
	}

	// ---------------- Window & Frame Actions ----------------

	public WebAction switchToWindow(String windowHandle) {
		driver.switchTo().window(windowHandle);
		return this;
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public WebAction switchToFrame(String frameNameOrId) {
		driver.switchTo().frame(frameNameOrId);
		return this;
	}

	public WebAction switchToFrame(int index) {
		driver.switchTo().frame(index);
		return this;
	}

	public WebAction switchToFrame(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.PRESENT);
		driver.switchTo().frame(e);
		return this;
	}

	public WebAction switchToDefaultContent() {
		driver.switchTo().defaultContent();
		return this;
	}

	// ---------------- Mouse & Actions ----------------

	public WebAction moveToElement(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		actions.moveToElement(e).perform();
		return this;
	}

	public WebAction actionClick(Element element) {
		WebElement e = waits.waitForElement(element, Waits.Condition.CLICKABLE);
		actions.click(e).perform();
		return this;
	}

	// ---------------- JavaScript ----------------

	public WebAction executeJavaScriptOnElement(Element element, String script) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		((JavascriptExecutor) driver).executeScript(script, e);
		return this;
	}

	// ---------------- Dropdown Actions ----------------

	public WebAction selectByIndexFromDropdown(Element element, int index) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		Select select = new Select(e);
		select.selectByIndex(index);
		return this;
	}

	public WebAction selectByValueFromDropdown(Element element, String value) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		Select select = new Select(e);
		select.selectByValue(value);
		return this;
	}

	public WebAction selectByVisibleTextFromDropdown(Element element, String text) {
		WebElement e = waits.waitForElement(element, Waits.Condition.VISIBLE);
		Select select = new Select(e);
		select.selectByVisibleText(text);
		return this;
	}

	// ---------------- Multiple Windows ----------------

	public boolean checkIfMultipleWindowsPresent() {
		return driver.getWindowHandles().size() > 1;
	}
}
