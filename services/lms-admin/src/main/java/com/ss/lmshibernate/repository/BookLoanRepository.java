package com.ss.lmshibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ss.lmshibernate.entity.BookLoan;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {

}
