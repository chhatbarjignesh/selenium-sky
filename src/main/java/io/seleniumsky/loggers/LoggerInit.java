package io.seleniumsky.loggers;

import org.apache.logging.log4j.core.config.Configurator;

public class LoggerInit {
	public static void init() {
		Configurator.initialize(null, "src/test/resources/log4j2.xml");
	}
}
