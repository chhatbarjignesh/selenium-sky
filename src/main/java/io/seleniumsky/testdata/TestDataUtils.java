package io.seleniumsky.testdata;

import io.seleniumsky.config.Config;
import io.seleniumsky.testdata.entity.Product;
import io.seleniumsky.testdata.entity.ProductType;
import io.seleniumsky.testdata.entity.User;
import io.seleniumsky.testdata.entity.UserType;
import io.seleniumsky.testdata.wrappers.ProductWrapper;
import io.seleniumsky.testdata.wrappers.UserWrapper;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.LoaderOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TestDataUtils {

	private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

	public static final List<UserType> USERS;
	public static final List<ProductType> PRODUCTS;
	private static final Random RANDOM = new Random();

	static {
		try {
			USERS = loadYamlData(Config.getEnvironment(), "users.yaml", UserWrapper.class).getUserTypes();
			PRODUCTS = loadYamlData(Config.getEnvironment(), "products.yaml", ProductWrapper.class).getProductTypes();
		} catch (IOException e) {
			throw new RuntimeException("Failed to load test data", e);
		}
	}

	// ---------------- Lookup Methods ----------------

	public static UserType getUserType(String typeName) {
		Optional<UserType> match = USERS.stream().filter(ut -> ut.getType().equalsIgnoreCase(typeName)).findFirst();
		return match.orElse(null);
	}

	public static ProductType getProductType(String typeName) {
		Optional<ProductType> match = PRODUCTS.stream().filter(pt -> pt.getType().equalsIgnoreCase(typeName))
				.findFirst();
		return match.orElse(null);
	}

	// ---------------- Random Selection ----------------

	public static User getOneUser(String userTypeName) {
		UserType userType = getUserType(userTypeName);
		if (userType == null || userType.getUsers().isEmpty())
			return null;

		int index = RANDOM.nextInt(userType.getUsers().size());
		return userType.getUsers().get(index);
	}

	public static Product getOneProduct(String productTypeName) {
		ProductType productType = getProductType(productTypeName);
		if (productType == null || productType.getProducts().isEmpty())
			return null;

		int index = RANDOM.nextInt(productType.getProducts().size());
		return productType.getProducts().get(index);
	}

	// ---------------- YAML Loader ----------------

	private static <T> T loadYamlData(String environment, String fileName, Class<T> type) throws IOException {
		File file = new File(TEST_DATA_PATH + environment, fileName);
		if (!file.exists())
			throw new IOException("File not found: " + file.getAbsolutePath());

		LoaderOptions options = new LoaderOptions();
		Constructor constructor = new Constructor(type, options);
		Yaml yaml = new Yaml(constructor);

		try (FileInputStream fis = new FileInputStream(file)) {
			return yaml.load(fis);
		}
	}
}
