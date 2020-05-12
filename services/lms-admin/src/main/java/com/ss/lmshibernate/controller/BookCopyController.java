//package com.ss.lmshibernate.controller;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ss.lmshibernate.entity.Author;
//import com.ss.lmshibernate.entity.Book;
//import com.ss.lmshibernate.entity.BookCopy;
//import com.ss.lmshibernate.entity.Publisher;
//import com.ss.lmshibernate.exception.ResourceNotFoundException;
//import com.ss.lmshibernate.repository.BookCopyRepository;
//import com.ss.lmshibernate.repository.BookRepository;
//import com.ss.lmshibernate.repository.LibraryBranchRepository;
//
//
//
//@RestController
//@RequestMapping("/lms")
//public class BookCopyController {
//	
//	@Autowired
//	  BookCopyRepository bookCopyRepository;
//	
//	  @Autowired
//	  BookRepository bookRepository;
//
//	  @Autowired
//	  LibraryBranchRepository libraryBranchRepository;
//	  
//	    @GetMapping("/bookCopies")
//	    public Page<BookCopy> getAllBooks(Pageable pageable) {
//	        return bookCopyRepository.findAll(pageable);
//	    }
//
//	 
//	  
//
//}
