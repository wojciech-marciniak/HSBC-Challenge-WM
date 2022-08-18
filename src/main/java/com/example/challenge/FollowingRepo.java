package com.example.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepo extends JpaRepository<Follows, Long> {

	List<Follows> findFollowsByObserver(Person person);

}
