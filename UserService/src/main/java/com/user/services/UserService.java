package com.user.services;

import java.util.List;

import com.user.entities.User;

public interface UserService {

	User saveUser(User user);
	List<User> getAllUser();
	User getUserById(String userId);
	void deleteUser(String userId);
	User updateUser(User user);
}
