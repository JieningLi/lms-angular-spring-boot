package com.smoothstack.avalanche.lms.borrowermsvc.entity;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_borrower")
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardNo;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;

	@Column(name = "phone")
	private String phone;
	
	@OneToMany(mappedBy = "borrower",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<BookLoans> loans;
	/*
	 * CONSTRUCTORS
	 */
	public Borrower() {}
	public Borrower(Long id, String name, String address, String phone) {
		this.cardNo = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	/*
	 * GETTERS/SETTERS
	 */
	public Long getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(Long id) {
		this.cardNo = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
		Borrower other = (Borrower) o;
		return Objects.equals(getName(), other.getName()) 
				&& Objects.equals(getAddress(), other.getAddress())
				&& Objects.equals(getPhone(), other.getPhone());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(name, address, phone);
	}
}
