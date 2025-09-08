package io.seleniumsky.factory;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.seleniumsky.config.Config;
import io.seleniumsky.utils.PerfectoUtils;
import io.seleniumsky.wrapper.ServicesAction;
import io.seleniumsky.wrapper.Waits;
import io.seleniumsky.wrapper.WebAction;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class DriverFactory {

	// Thread-safe instance per parallel thread
	private static final ThreadLocal<DriverFactory> driverThread = ThreadLocal.withInitial(DriverFactory::new);

	private WebDriver driver;
	private Waits wait;
	private WebAction webAction;
	private ServicesAction servicesAction;
	private PerfectoUtils perfectoUtils;
	private Scenario scenario;

	private DriverFactory() {
		initializeDriver();
	}

	/** Get DriverFactory instance for current thread */
	public static DriverFactory getThread() {
		return driverThread.get();
	}

	/** Get WebDriver for current thread, initialize if null */
	public static WebDriver getDriver() {
		return driverThread.get().driver;
	}

	/** Lazy initialization of driver and wrappers */
	private void initializeDriver() {
		try {
			driver = Config.isCloud() ? createRemoteDriver() : createLocalDriver();
			wait = new Waits(driver);
			webAction = new WebAction(driver);
			servicesAction = new ServicesAction();
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize driver: " + e.getMessage(), e);
		}
	}

	/** Quit driver and remove thread-local */
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
		driverThread.remove();
	}

	/** Wrapper getters */
	public Waits getWaits() {
		return wait;
	}

	public WebAction getWebAction() {
		return webAction;
	}

	public ServicesAction getServicesAction() {
		return servicesAction;
	}

	public PerfectoUtils getPerfectoUtils() {
		return perfectoUtils;
	}

	/** Scenario getter/setter */
	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	/** Create local desktop driver using WebDriverManager */
	private WebDriver createLocalDriver() {
		String browser = Config.getBrowser().toLowerCase();
		WebDriver localDriver;

		switch (browser) {
			case "chrome" :
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				if (Config.isHeadless()) {
					chromeOptions.addArguments("--headless", "--disable-gpu");
				}
				localDriver = new ChromeDriver(chromeOptions);
				localDriver.manage().window().maximize();
				break;

			case "firefox" :
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				localDriver = new FirefoxDriver(firefoxOptions);
				localDriver.manage().window().maximize();
				break;

			case "edge" :
				WebDriverManager.edgedriver().setup();
				localDriver = new EdgeDriver();
				localDriver.manage().window().maximize();
				break;

			case "safari" :
				localDriver = new SafariDriver();
				localDriver.manage().window().maximize();
				break;

			default :
				throw new IllegalArgumentException("Unsupported local browser: " + browser);
		}

		return localDriver;
	}

	/** Create remote/cloud driver */
	private WebDriver createRemoteDriver() throws MalformedURLException {
		DesiredCapabilities caps = getDesiredCapabilities();
		String gridUrl = Config.getGridUrl();
		WebDriver remoteDriver = new RemoteWebDriver(new URL(gridUrl), caps);
		remoteDriver.manage().window().maximize();
		return remoteDriver;
	}

	/** Build DesiredCapabilities dynamically */
	private DesiredCapabilities getDesiredCapabilities() {
		DesiredCapabilities caps = new DesiredCapabilities();
		String browser = Config.getBrowser().toLowerCase();

		caps.setCapability("browserName", browser);
		caps.setCapability("javascriptEnabled", true);
		caps.setCapability("platformName", Config.getPlatform());
		caps.setCapability("browserVersion", Config.getBrowserVersion());

		if (Config.isPerfecto()) {
			caps.setCapability("securityToken", Config.getPerfectoToken());
			caps.setCapability("waitForAvailableLicense", true);
		}

		return caps;
	}
}
