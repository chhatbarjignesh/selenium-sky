package io.seleniumsky.actions;

import io.seleniumsky.factory.PageFactory;

public class SampleAction {
	PageFactory factory = new PageFactory();
	public void goToUrl(String arg0) {
		factory.samplePage().openUrl(arg0);
	}

	public void searchKeyword(String keyword) {
		factory.samplePage().searchKeyword(keyword).clickSearch();
	}

	public void printOneUserFromUserType(String userType) {
		factory.samplePage().printOneUserFromUserType(userType);
	}
}
