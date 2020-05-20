package com.smoothstack.avalanche.lms.librarianmsvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.librarianmsvc.entity.BookLoans;
import com.smoothstack.avalanche.lms.librarianmsvc.entity.BookLoansId;


@Repository
public interface BookLoansDAO extends JpaRepository<BookLoans, BookLoansId>{

	
	@Query("SELECT bookloans FROM BookLoans bookloans WHERE bookloans.borrower.cardNo = :cardNum")
	List<BookLoans> findByCardNo(@Param("cardNum") Long cardNo);
	/*
	@Query(value = "SELECT * FROM tbl_book_loans "
			+ "WHERE card_no = :cardNum AND "
			+ "book_id = :bookId AND "
			+ "branch_id= :branchId", nativeQuery = true)
	Optional<BookLoans> findByBookLoanId(@Param("cardNum") Long cardNo, @Param("bookId") Long bookId, @Param("branchId") Long branchId);
	*/
}
