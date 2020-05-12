package com.smoothstack.avalanche.lms.borrowermsvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.borrowermsvc.entity.Book;


@Repository
public interface BookDAO extends JpaRepository<Book ,Long>{

}
