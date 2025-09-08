package io.seleniumsky.testdata.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductType {
	private String type;
	private List<Product> products;

}
