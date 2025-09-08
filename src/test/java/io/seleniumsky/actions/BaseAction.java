package io.seleniumsky.actions;

import io.seleniumsky.factory.PageFactory;

public class BaseAction {

	PageFactory pageFactory = new PageFactory();

	public SampleAction sampleAction() {
		return new SampleAction();
	}
}
