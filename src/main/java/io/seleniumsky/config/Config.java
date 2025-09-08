package io.seleniumsky.config;

import io.seleniumsky.loggers.FrameworkLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	// Default values
	private static final Properties DEFAULTS = new Properties();
	private static final Properties PROPS = new Properties();

	static {
		// Define defaults
		DEFAULTS.setProperty("browser", "chrome");
		DEFAULTS.setProperty("browserVersion", "latest");
		DEFAULTS.setProperty("platform", "windows");
		DEFAULTS.setProperty("headless", "false");
		DEFAULTS.setProperty("cloud", "false");
		DEFAULTS.setProperty("gridUrl", "http://localhost:4444/wd/hub");
		DEFAULTS.setProperty("perfecto", "false");
		DEFAULTS.setProperty("perfectoToken", "");
		DEFAULTS.setProperty("environment", "dev");

		// Load from config.properties
		try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input != null) {
				PROPS.load(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Override with system properties if provided
		DEFAULTS.forEach((key, value) -> {
			String sysValue = System.getProperty(key.toString());
			if (sysValue != null) {
				PROPS.setProperty(key.toString(), sysValue);
			}
		});

		// Fill in missing properties with defaults
		DEFAULTS.forEach((key, value) -> PROPS.putIfAbsent(key, value));

		// Log all properties dynamically
		StringBuilder sb = new StringBuilder("===== Runtime Configuration =====\n");
		PROPS.forEach((key, value) -> sb.append(key).append(" = ").append(value).append("\n"));
		sb.append("=================================");
		FrameworkLogger.info(sb.toString());
	}

	// ===== Separate getters =====
	public static String getBrowser() {
		return PROPS.getProperty("browser");
	}

	public static String getBrowserVersion() {
		return PROPS.getProperty("browserVersion");
	}

	public static String getPlatform() {
		return PROPS.getProperty("platform");
	}

	public static boolean isHeadless() {
		return Boolean.parseBoolean(PROPS.getProperty("headless"));
	}

	public static boolean isCloud() {
		return Boolean.parseBoolean(PROPS.getProperty("cloud"));
	}

	public static String getGridUrl() {
		return PROPS.getProperty("gridUrl");
	}

	public static boolean isPerfecto() {
		return Boolean.parseBoolean(PROPS.getProperty("perfecto"));
	}

	public static String getPerfectoToken() {
		return PROPS.getProperty("perfectoToken");
	}
	public static String getEnvironment() {
		return PROPS.getProperty("environment");
	}
}
