package com.smoothstack.avalanche.lms.borrowermsvc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.borrowermsvc.entity.Book;


@Repository
public interface BookDAO extends JpaRepository<Book ,Long>{

}
