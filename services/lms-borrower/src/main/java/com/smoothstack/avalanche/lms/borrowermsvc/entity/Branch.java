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
@Table(name = "tbl_library_branch")
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	
	@Column(name = "branchName")
	private String branchName;
	
	@Column(name = "branchAddress")
	private String branchAddress;

	@OneToMany(mappedBy = "branch", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<BookLoans> bookLoans;
	
	@OneToMany(mappedBy = "branch", 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<BookCopies> bookCopies;
	
	/*
	 * CONSTRUCTORS
	 */
	public Branch() {}
	public Branch(Long branchId, String branchName, String branchAddress) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
	}
	/*
	 * GETTERS/SETTERS
	 */
	public Long getId() {
		return branchId;
	}

	public void setId(Long id) {
		this.branchId = id;
	}

	public String getName() {
		return branchName;
	}

	public void setName(String name) {
		this.branchName = name;
	}

	public String getAddress() {
		return branchAddress;
	}

	public void setAddress(String address) {
		this.branchAddress = address;
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
		Branch other = (Branch) o;
		return Objects.equals(getName(), other.getName()) && Objects.equals(getAddress(), other.getAddress());
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(branchName, branchAddress);
	}
}
