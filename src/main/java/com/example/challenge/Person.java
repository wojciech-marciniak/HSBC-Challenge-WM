package com.example.challenge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * System User
 */
@Data
@Entity
public class Person {

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "observer")
	private List<Follows> whoFollowsMe;

	@JsonIgnore
	@OneToMany(mappedBy = "author")
	private List<Follows> whomIdoFollow;





}
