package com.ss.lmshibernate.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
@Entity
@Table(name = "tbl_book_loans")
public class BookLoan implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "dateOut", columnDefinition = "DATE")
	private Date dateOut;

	@Column(name = "dueDate", columnDefinition = "DATE")
	private Date dueDate;
	
	@EmbeddedId
	private BookLoanId bookLoanId;
	
	public BookLoan() {
		
	}
	

	/**
	 * @param dateOut
	 * @param dueDate
	 * @param bookLoanId
	 */
	public BookLoan(Date dueDate, BookLoanId bookLoanId) {
		super();
		this.dueDate = dueDate;
		this.bookLoanId = bookLoanId;
	}


	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public BookLoanId getBookLoanId() {
		return bookLoanId;
	}

	public void setBookLoanId(BookLoanId bookLoanId) {
		this.bookLoanId = bookLoanId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookLoanId == null) ? 0 : bookLoanId.hashCode());
		result = prime * result + ((dateOut == null) ? 0 : dateOut.hashCode());
		result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
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
		BookLoan other = (BookLoan) obj;
		if (bookLoanId == null) {
			if (other.bookLoanId != null)
				return false;
		} else if (!bookLoanId.equals(other.bookLoanId))
			return false;
		if (dateOut == null) {
			if (other.dateOut != null)
				return false;
		} else if (!dateOut.equals(other.dateOut))
			return false;
		if (dueDate == null) {
			if (other.dueDate != null)
				return false;
		} else if (!dueDate.equals(other.dueDate))
			return false;
		return true;
	}
}
