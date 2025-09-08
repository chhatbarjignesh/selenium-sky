package io.seleniumsky.testdata.wrappers;

import io.seleniumsky.testdata.entity.UserType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWrapper {

	private List<UserType> userTypes;
}
