package com.user.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.entities.Hotel;
import com.user.entities.Rating;
import com.user.entities.User;
import com.user.exceptions.ResourceNotFoundExc;
import com.user.external.services.HotelService;
import com.user.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		String randomUserId =  UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		List<User> user=  userRepository.findAll();
		List<String> userId = user.stream().map(userIdDet->userIdDet.getUserId()).collect(Collectors.toList());
		// Calling rating micro-service api below
		Rating[] callRatingService = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+userId, Rating[].class);
		List<Rating> ratingOfUser = Arrays.stream(callRatingService).toList();
		logger.info("rating service calling {}",ratingOfUser);
		
		/* Micriservices calling using RestTemplate */
		//Calling hotel micro-service api below
//		ratingOfUser.stream().map(rating->{
//			ResponseEntity<Hotel> hotelServiceCalling= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//			Hotel hotel = hotelServiceCalling.getBody();
//			rating.setHotel(hotel);
//			logger.info("Response status code {} ",hotelServiceCalling.getStatusCode());
//			return rating;
//		}).collect(Collectors.toList());	
//		for(User userData: user) {
//			userData.setRatings(ratingOfUser);
//		}
		
		/* Micriservices calling using FeignClient */
		ratingOfUser.stream().map(rating->{
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());	
		for(User userData: user) {
			userData.setRatings(ratingOfUser);
		}
		
		return user;
	}

	@Override
	public User getUserById(String userId) {
		User user =  userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundExc("User with the given is not available on server: "+userId));
		
		// Calling rating micro-service api below
		Rating[] callRatingService = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		List<Rating> ratingOfUser = Arrays.stream(callRatingService).toList();
		logger.info("rating service calling {}",ratingOfUser);
		
		//Calling hotel micro-service api below
		ratingOfUser.stream().map(rating->{
			ResponseEntity<Hotel> hotelServiceCalling= restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelServiceCalling.getBody();
			rating.setHotel(hotel);
			logger.info("Response status code {} ",hotelServiceCalling.getStatusCode());
			return rating;
		}).collect(Collectors.toList());	
		user.setRatings(ratingOfUser);
		
		return user;
	}

	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public User updateUser(User user) {
		User existUser =  userRepository.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundExc("User with the given is not available on server: "+user.getUserId()));
		existUser.setAbout(user.getAbout());
		existUser.setEmail(user.getEmail());
		existUser.setName(user.getName());
		existUser.setUserId(user.getUserId());
		return userRepository.save(existUser);
	}

}
