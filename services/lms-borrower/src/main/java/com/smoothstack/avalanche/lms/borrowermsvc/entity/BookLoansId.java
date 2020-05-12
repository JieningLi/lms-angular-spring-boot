package com.smoothstack.avalanche.lms.borrowermsvc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class BookLoansId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "bookId")
	private Long bookId;
	
	@Column(name= "branchId")
	private Long branchId;
	
	@Column(name = "cardNo")
	private Long cardNo;

	
	
	/**
	 * 
	 */
	public BookLoansId() {
		super();
	}



	/**
	 * @param bookId
	 * @param branchId
	 * @param cardNo
	 */
	public BookLoansId(Long bookId, Long branchId, Long cardNo) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
	}



	/**
	 * @return the bookId
	 */
	public Long getBookId() {
		return bookId;
	}



	/**
	 * @return the branchId
	 */
	public Long getBranchId() {
		return branchId;
	}



	/**
	 * @return the cardNo
	 */
	public Long getCardNo() {
		return cardNo;
	}



	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}



	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}



	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((cardNo == null) ? 0 : cardNo.hashCode());
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
		BookLoansId other = (BookLoansId) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (cardNo == null) {
			if (other.cardNo != null)
				return false;
		} else if (!cardNo.equals(other.cardNo))
			return false;
		return true;
	}
	


	
	
}
