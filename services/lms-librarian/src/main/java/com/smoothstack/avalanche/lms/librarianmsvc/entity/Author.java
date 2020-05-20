package com.smoothstack.avalanche.lms.librarianmsvc.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tbl_author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorId;
	
	@Column(name = "authorName")
	private String authorName;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "tbl_book_authors",
			joinColumns = {@JoinColumn(name = "authorId")},
			inverseJoinColumns = { @JoinColumn(name = "bookId")}
			)
	private List<Book> books;
	/*
	 * TODO: implement mappings
	 */
	
	/*
	 * CONSTRUCTORS
	 */
	public Author() {}
	public Author(Long authorId, String name) {
		this.authorId = authorId;
		this.authorName = name;
	}
	/*
	 * GETTERS/SETTERS
	 */
	public Long getId() {
		return authorId;
	}

	public void setId(Long authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return authorName;
	}

	public void setName(String name) {
		this.authorName = name;
	}
	
	/*
	 * EQUALS / HASHCODE
	 */
	/*
	 * Equals/ HashCode
	 */
	@Override
	public boolean equals(Object o)
	{
		if( this == o) return true;
		if( o == null || getClass() != o.getClass())
			return false;
		Author other = (Author) o;
		return Objects.equals(getName(), other.getName());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(authorName);
	}
}
