package io.seleniumsky.stepdefs;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class Sample extends BaseStep {
	@Given("I am on {string} page")
	public void iAmOnPage(String arg0) {
		getBaseAction().sampleAction().goToUrl(arg0);
	}

	@Then("I search for {string} keyword")
	public void iSearchForKeyword(String keyword) {
		getBaseAction().sampleAction().searchKeyword(keyword);
	}

	@And("I print one user from userType {string}")
	public void iPrintOneUserFromUserType(String userType) {
		getBaseAction().sampleAction().printOneUserFromUserType(userType);
	}
}
