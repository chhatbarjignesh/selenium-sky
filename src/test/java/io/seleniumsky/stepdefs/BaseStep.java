package io.seleniumsky.stepdefs;

import io.seleniumsky.actions.BaseAction;

public class BaseStep {

	public BaseAction getBaseAction() {
		return new BaseAction();
	}
}
