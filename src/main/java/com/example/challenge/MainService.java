package com.example.challenge;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class MainService {

	private final PersonRepo personRepo;
	private final MessageRepo messageRepo;
	private final FollowingRepo followingRepo;

	public MainService(PersonRepo personRepo, MessageRepo messageRepo, FollowingRepo followingRepo) {
		this.personRepo = personRepo;
		this.messageRepo = messageRepo;
		this.followingRepo = followingRepo;
	}

	long writeMessage(String text, String name) throws AppException {
		if (text.length() > 140) throw new AppException("Your message is too long");
		if (text.length() < 1) throw new AppException("No text found");
		Person person = personRepo.findByName(name);
		if (person != null) {
			throw new AppException("Person of this name already exists");
		} else {
			person = new Person();
		}
		person.setName(name);
		personRepo.save(person);
		Message message = new Message(text, person);
		message.setDate(new Timestamp(new Date().getTime()));
		return messageRepo.save(message).getId();
	}

	List<Message> myMessages(Long userId) throws AppException {
		Person person = personRepo.findById(userId).orElseThrow(() -> new AppException("No such a person"));
		return messageRepo.findAllByAuthorOrderByDateDesc(person);
	}


	List<Message> findMessagesOfWhoIFollow(Long userId) throws AppException {
		Person person = personRepo.findById(userId).orElseThrow(() -> new AppException("No such a person"));
		List<Follows> followsList = followingRepo.findFollowsByObserver(person);
		List<Message> messageList = new ArrayList<>();
		for (Follows follows: followsList) {
			List<Message> messages = messageRepo.findAllByAuthorOrderByDateDesc(follows.getAuthor());
			messageList.addAll(messages);
		}
		Collections.sort(messageList);
		return messageList;
	}

	void follow(Long whoToFollow, Long whoAmI) throws AppException {
		Person personToBeObserved = personRepo.findById(whoToFollow).orElseThrow(() -> new AppException("No such a person to be followed"));
		Person thatIsMe = personRepo.findById(whoAmI).orElseThrow(() -> new AppException("No such a person to follow"));
		Follows follows = new Follows();
		follows.setObserver(thatIsMe);
		follows.setAuthor(personToBeObserved);
		followingRepo.save(follows);
	}

}
