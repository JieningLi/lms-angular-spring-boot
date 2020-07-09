package com.smoothstack.lms.orchestration.entity;


import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_book_loans")
public class BookLoans {

	@EmbeddedId
	private BookLoansId bookLoansId;
	
	@MapsId("bookId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false)
	private Book book;
	
	@MapsId("branchId")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branchId", referencedColumnName = "branchId", nullable = false)
	private Branch branch;
	
	@MapsId("cardNo")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cardNo", referencedColumnName = "cardNo", nullable = false)
	private Borrower borrower;
	
	@Column(name = "dateOut")
	private Date dateOut;
	
	@Column(name = "dueDate")
	private Date dueDate;
	
	@Column(name = "dateIn")
	private Date dateIn;

	/*
	 * CONSTRUCTOR
	 */

	public BookLoans() {
		
	}

	public BookLoans(BookLoansId bookLoansId, Date dateOut, Date dueDate, Date dateIn) {

		this.bookLoansId = bookLoansId;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
		this.dateIn = dateIn;
	}

	/*
	 * GETTERS/SETTERS
	 */
	public BookLoansId getBookLoansId() {
		return bookLoansId;
	}

	public void setBookLoansId(BookLoansId bookLoansId) {
		this.bookLoansId = bookLoansId;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDateDue() {
		return dueDate;
	}

	public void setDateDue(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((bookLoansId == null) ? 0 : bookLoansId.hashCode());
		result = prime * result + ((borrower == null) ? 0 : borrower.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
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
		BookLoans other = (BookLoans) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (bookLoansId == null) {
			if (other.bookLoansId != null)
				return false;
		} else if (!bookLoansId.equals(other.bookLoansId))
			return false;
		if (borrower == null) {
			if (other.borrower != null)
				return false;
		} else if (!borrower.equals(other.borrower))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		return true;
	}
	
}
