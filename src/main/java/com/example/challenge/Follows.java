package com.example.challenge;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
public class Follows implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Person observer;
	@ManyToOne
	private Person author;

}
