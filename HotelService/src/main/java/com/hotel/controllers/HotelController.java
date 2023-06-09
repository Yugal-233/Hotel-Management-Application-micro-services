package com.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.entities.Hotel;
import com.hotel.services.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	//@PreAuthorize("hasAuthority('Admin')")		///Not able to implement it properly
	@PostMapping
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
		Hotel hotelData = hotelService.createHotel(hotel);
		return ResponseEntity.status(HttpStatus.CREATED).body(hotelData);
	}
	//@PreAuthorize("hasAuthority('SCOPE_internal')")		///Not able to implement it properly
	@GetMapping("/{hotelId}")
	public ResponseEntity<Hotel> getHotelById(@PathVariable("hotelId") String hotelId){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getHotelById(hotelId));
	}
	//@PreAuthorize("hasAuthority('SCOPE_internal') || hasAuthority('Admin')")		///Not able to implement it properly
	@GetMapping
	public ResponseEntity<List<Hotel>> getAllHotels(){
		return ResponseEntity.status(HttpStatus.OK).body(hotelService.getAllHotels());
	}
}
