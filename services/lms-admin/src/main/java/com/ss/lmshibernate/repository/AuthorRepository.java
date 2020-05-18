package com.ss.lmshibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ss.lmshibernate.entity.Author;


@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
	
	  //public List<Author> findByName(String input);
	  //public List<Author> readByNameStartingWith(String input);
	  
	  @Query("SELECT a FROM Author a WHERE a.authorName LIKE %:title%")
	  List<Author> searchByNameike(@Param("title") String title);
}

