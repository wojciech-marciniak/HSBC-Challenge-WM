package com.example.challenge;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Message written or read
 */
@Entity
@Data
@NoArgsConstructor
public class Message implements Comparable<Message> {

	@Id
	@GeneratedValue
	private Long id;

	String text;

	Message(String text, Person author) {
		this.text = text;
		this.author = author;
	}

	@ManyToOne
	@JoinColumn(name = "author")
	private Person author;

	private Timestamp date;

	@Override
	public int compareTo(Message o) {
		if (this.date.before(o.date)) return -1;
		if (this.date.after(o.date)) return 1;
		return 0;
	}
}
