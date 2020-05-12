package com.ss.lmshibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.lmshibernate.entity.Borrower;

@Repository
public interface BorrowerRepository extends JpaRepository<Borrower, Long> {

}
