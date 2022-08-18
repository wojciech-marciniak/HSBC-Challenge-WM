package com.example.challenge;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MainController {

	private final MainService mainService;

	public MainController(MainService mainService) {
		this.mainService = mainService;
	}

	/**
	 *
	 * @param personDTO, JSON
	 * @return ID of tne newly registered person
	 */
	@PostMapping("/register")
	public ResponseEntity<Long> writeMessage(@RequestBody PersonDTO personDTO) {
		try{
			return ResponseEntity.ok(mainService.writeMessage(personDTO.getMessage(), personDTO.getName()));
		} catch (AppException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 *
	 * @param id, long
	 * @return List of messages authored by person of ID provided
	 */
	@GetMapping("/messages/{id}")
	public ResponseEntity<List<Message>> myMessages(@PathVariable("id") long id) {
		try{
			return ResponseEntity.ok(mainService.myMessages(id));
		} catch (AppException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 *
	 * @param id ID of person who wants to see messages authored by persons they follow.
	 * @return List of messages.
	 */
	@GetMapping("/messages/other/{id}")
	public ResponseEntity<List<Message>> findMessagesOfOthers(@PathVariable("id") long id) {
		try{
			return ResponseEntity.ok(mainService.findMessagesOfWhoIFollow(id));
		} catch (AppException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 *
	 * @param id id of the user using app
	 * @param other id of the another user who the user using app wants to follow
	 * @return nothing
	 */

	@GetMapping("/follow/{me}/{other}")
	public ResponseEntity<Void> follow (@PathVariable("me") long id, @PathVariable("other") long other) {
		try{
			mainService.follow(other, id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (AppException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}



}
