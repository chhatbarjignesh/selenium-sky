package io.seleniumsky.wrapper;

import org.openqa.selenium.By;

public class Element {

	private String elementName; // friendly elementName for logging
	private String elementValue; // locator elementValue
	private Type elementType; // locator elementType

	/** Locator types we support */
	public enum Type {
		ID, NAME, XPATH, CSS, CLASS, LINK_TEXT, PARTIAL_LINK_TEXT, TAG_NAME
	}

	public Element(String elementName, String elementValue, Type elementType) {
		this.elementName = elementName;
		this.elementValue = elementValue;
		this.elementType = elementType;
	}

	/** Convert Element to Selenium By */
	public By getByObject() {
		switch (elementType) {
			case ID :
				return By.id(elementValue);
			case NAME :
				return By.name(elementValue);
			case XPATH :
				return By.xpath(elementValue);
			case CSS :
				return By.cssSelector(elementValue);
			case CLASS :
				return By.className(elementValue);
			case LINK_TEXT :
				return By.linkText(elementValue);
			case PARTIAL_LINK_TEXT :
				return By.partialLinkText(elementValue);
			case TAG_NAME :
				return By.tagName(elementValue);
			default :
				throw new IllegalArgumentException("Unsupported locator elementType: " + elementType);
		}
	}

	public Element format(Object... args) {
		String formattedValue = elementValue;
		for (int i = 0; i < args.length; i++) {
			formattedValue = formattedValue.replace("{" + i + "}", args[i].toString());
		}
		return new Element(this.elementName, formattedValue, this.elementType);
	}

	/** Getters for logging or reporting */
	public String getElementName() {
		return elementName;
	}

	public String getElementValue() {
		return elementValue;
	}

	public Type getElementType() {
		return elementType;
	}

	@Override
	public String toString() {
		return "Element{elementName='" + elementName + "', elementType=" + elementType + ", elementValue='"
				+ elementValue + "'}";
	}
}
