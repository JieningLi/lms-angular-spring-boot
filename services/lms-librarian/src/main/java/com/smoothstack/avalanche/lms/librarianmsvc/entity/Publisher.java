package com.smoothstack.avalanche.lms.librarianmsvc.entity;

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
@Table(name = "tbl_publisher")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long publisherId;
	
	@Column(name = "publisherName")
	private String publisherName;
	
	@Column(name = "publisherAddress")
	private String publisherAddress;

	@Column(name = "publisherPhone")
	private String publisherPhone;
	
	@OneToMany(mappedBy = "publisher",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Book> books;
	/*
	 * CONSTRUCTORS
	 */
	public Publisher() {}
	public Publisher(Long id, String name, String address, String phone) {
		this.publisherId = id;
		this.publisherName = name;
		this.publisherAddress = address;
		this.publisherPhone = phone;
	}
	/*
	 * GETTERS/SETTERS
	 */
	public Long getId() {
		return publisherId;
	}

	public void setId(Long id) {
		this.publisherId = id;
	}

	public String getName() {
		return publisherName;
	}

	public void setName(String name) {
		this.publisherName = name;
	}

	public String getAddress() {
		return publisherAddress;
	}

	public void setAddress(String address) {
		this.publisherAddress = address;
	}
	public String getPhone() {
		return publisherPhone;
	}
	public void setPhone(String phone) {
		this.publisherPhone = phone;
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
		Publisher other = (Publisher) o;
		return Objects.equals(getName(), other.getName())
				&& Objects.equals(getAddress(), other.getAddress())
				&& Objects.equals(getPhone(), other.getPhone());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(publisherName, publisherAddress, publisherPhone);
	}
}
