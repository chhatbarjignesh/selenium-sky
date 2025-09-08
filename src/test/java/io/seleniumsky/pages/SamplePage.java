package io.seleniumsky.pages;

import io.seleniumsky.locators.SamplePageLocators;
import io.seleniumsky.testdata.TestDataUtils;
import io.seleniumsky.testdata.entity.User;

public class SamplePage extends BasePage {

	public SamplePage openUrl(String url) {
		getAction().goToUrl(url);
		return this;
	}

	public SamplePage searchKeyword(String keyword) {
		getAction().type(SamplePageLocators.SEARCH_BOX, keyword);
		return this;
	}

	public SamplePage clickSearch() {
		getAction().click(SamplePageLocators.SEARCH_BUTTON);
		return this;
	}

	public void printOneUserFromUserType(String userType) {
		User user = TestDataUtils.getOneUser(userType);
		logData("UserType: " + userType);
		logData("User's name: " + user.getUsername());
		logData("User's email: " + user.getPassword());
	}
}
