package io.seleniumsky.testdata.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserType {
	private String type;
	private List<User> users;

}
