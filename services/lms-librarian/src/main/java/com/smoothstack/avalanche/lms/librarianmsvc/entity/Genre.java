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
@Table(name = "tbl_genre")
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long genreId;
	
	@Column(name = "genreName")
	private String genreName;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "tbl_book_genres",
			joinColumns = {@JoinColumn(name = "genreId")},
			inverseJoinColumns = { @JoinColumn(name = "bookId")}
			)
	private List<Book> books;
	
	/*
	 * CONSTRUCTORS
	 */
	public Genre() {}
	public Genre(Long id, String name) {
		this.genreId = id;
		this.genreName = name;
	}
	/*
	 * GETTERS/SETTERS
	 */
	public Long getId() {
		return genreId;
	}

	public void setId(Long id) {
		this.genreId = id;
	}

	public String getName() {
		return genreName;
	}

	public void setName(String name) {
		this.genreName = name;
	}
	
	/*
	 * Equals/ HashCode
	 */
	@Override
	public boolean equals(Object o)
	{
		if( this == o) return true;
		if( o == null || getClass() != o.getClass())
			return false;
		Genre other = (Genre) o;
		return Objects.equals(getName(), other.getName());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(genreName);
	}
}
