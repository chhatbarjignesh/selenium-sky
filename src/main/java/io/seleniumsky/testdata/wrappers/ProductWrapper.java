package io.seleniumsky.testdata.wrappers;

import io.seleniumsky.testdata.entity.ProductType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductWrapper {
	private List<ProductType> productTypes;

}
