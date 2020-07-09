package com.smoothstack.lms.orchestration.entity;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "tbl_book_copies")
public class BookCopies {

	@EmbeddedId
	private BookCopiesId bookCopiesId;
	
	@Column(name = "noOfCopies")
	private Long noOfCopies;

	@MapsId("bookId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonBackReference
	@JoinColumn(name = "bookId", referencedColumnName = "bookId",insertable = false, updatable = false, nullable = false)
	private Book book;
	
	@MapsId("branchId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branchId", referencedColumnName = "branchId",insertable = false, updatable = false, nullable = false)
	private Branch branch;
	
	/*
	 * CONSTRUCTOR
	 */

	public BookCopies() {
		
	}

	public BookCopies(BookCopiesId bookCopiesId, Long noOfCopies) {
		this.bookCopiesId = bookCopiesId;
		this.noOfCopies = noOfCopies;
	}
	/*
	 * GETTERS / SETTERS
	 */
	
	public BookCopiesId getBookCopiesId() {
		return bookCopiesId;
	}

	public void setBookCopiesId(BookCopiesId bookCopiesId) {
		this.bookCopiesId = bookCopiesId;
	}
	
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @return the branch
	 */
	public Branch getBranch() {
		return branch;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}
	/*
	 * EQUALS/HASHCODE
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((bookCopiesId == null) ? 0 : bookCopiesId.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookCopies other = (BookCopies) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (bookCopiesId == null) {
			if (other.bookCopiesId != null)
				return false;
		} else if (!bookCopiesId.equals(other.bookCopiesId))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		return true;
	}
	
}
