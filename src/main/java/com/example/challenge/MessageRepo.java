package com.example.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

	List<Message> findAllByAuthorOrderByDateDesc (Person person);

}
