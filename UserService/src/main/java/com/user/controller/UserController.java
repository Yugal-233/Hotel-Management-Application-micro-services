package com.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entities.User;
import com.user.services.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(User.class);

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User userData = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userData);
	}

	int retryCount = 1;

	@GetMapping("/{userId}")
	// @CircuitBreaker(name = "ratingHotelBreaker",fallbackMethod =
	// "ratingHotelFallBack")
	// @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallBack")
	@RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallBack")
	public ResponseEntity<User> getSingleUser(@PathVariable("userId") String userId) {
		logger.info("get single user handler : UserController");
		logger.info("retry count {}", retryCount);
		retryCount++;
		User userData = userService.getUserById(userId);
		return ResponseEntity.ok(userData);
	}
	/// method for circuit-breaker
	/*
	 * public ResponseEntity<User> ratingHotelFallBack(String userId,Exception ex){
	 * logger.info("FallBack is executed becuase the service is down",
	 * ex.getMessage()); User user = new User();
	 * user.setEmail("yug233@gmail.com -dummmy"); user.setName("yug -dummmy");
	 * user.setUserId("123456"); user.
	 * setAbout("This implementation is created bcoz the services are down  -dummmy"
	 * ); return new ResponseEntity<>(user, HttpStatus.OK); }
	 */

	// Method for the retry
	/*
	 * public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex)
	 * { User user = new User(); user.setEmail("yug233@gmail.com -dummmy");
	 * user.setName("yug -dummmy"); user.setUserId("123456"); user.
	 * setAbout("This implementation is created bcoz the services are down  -dummmy"
	 * ); return new ResponseEntity<>(user, HttpStatus.OK); }
	 */
	
	// Method for the Rate Limiter
	public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex) {
		User user = new User();
		user.setEmail("yug233@gmail.com -dummmy");
		user.setName("yug -dummmy");
		user.setUserId("123456");
		user.setAbout("This implementation is created bcoz the services are down  -dummmy");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> userData = userService.getAllUser();
		return ResponseEntity.ok(userData);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
		User userData = userService.updateUser(user);
		return new ResponseEntity<String>("The given user is updated of id: " + userData.getUserId(), HttpStatus.OK);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<String>("user delete with id: " + userId, HttpStatus.OK);
	}

}
