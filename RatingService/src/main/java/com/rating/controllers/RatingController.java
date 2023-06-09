package com.rating.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rating.entities.Rating;
import com.rating.services.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {
	
	@Autowired
	private RatingService ratingService;
	
	//@PreAuthorize("hasAuthority('admin')")        ///Not able to implement it properly
	@PostMapping
	public ResponseEntity<Rating> createUser(@RequestBody Rating rating){
		Rating ratingData =ratingService.createRating(rating); 
		return ResponseEntity.status(HttpStatus.CREATED).body(ratingData);
	}
	@GetMapping
	public ResponseEntity<List<Rating>> getAllRatings(){
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllRating());
	}
    //@PreAuthorize("hasAuthority('internal') || hasAuthority('admin')")		///Not able to implement it properly
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<Rating>> getRatingByUser(@PathVariable("userId") String userId){
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByUserId(userId));
	}
	@GetMapping("/hotels/{hotelId}")
	public ResponseEntity<List<Rating>> getRatingByHotel(@PathVariable("hotelId") String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(ratingService.getRatingByHotelId(hotelId));
	}
}
