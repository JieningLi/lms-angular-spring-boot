package com.smoothstack.avalanche.lms.borrowermsvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.borrowermsvc.entity.BookCopies;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.BookCopiesId;


@Repository
public interface BookCopiesDAO extends JpaRepository<BookCopies , BookCopiesId>{

	List<BookCopies> findBookCopiesByBookCopiesId_branchId(Long branchId);
	
//	@Query("SELECT bc FROM BookCopies bc WHERE bc.book.id = :bid AND bc.branch.id = :brid")
//	Optional<BookCopies> findBookCopiesById(@Param("bid") Long bookId, @Param("brid") Long branchId);
}
