import io.seleniumsky.loggers.LoggerInit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {
	private static final Logger logger = LogManager.getLogger("Framework");

	public static void main(String[] args) {
		LoggerInit.init();
		logger.info("Hello Log4j2");
	}
}