package com.ss.lmshibernate.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ss.lmshibernate.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	Page<Book> findByBookId(Long bookId, Pageable pageable);
}
