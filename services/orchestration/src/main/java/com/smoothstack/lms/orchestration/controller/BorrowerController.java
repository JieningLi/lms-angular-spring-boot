package com.smoothstack.lms.orchestration.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smoothstack.lms.orchestration.entity.Author;
import com.smoothstack.lms.orchestration.entity.Book;
import com.smoothstack.lms.orchestration.entity.BookCopies;
import com.smoothstack.lms.orchestration.entity.BookLoans;
import com.smoothstack.lms.orchestration.entity.Branch;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("http://localhost:8083/lms/borrower")
public class BorrowerController {

	@Autowired
	RestTemplate restTemplate;
	
	private static String baseUrl = "http://localhost:8083/lms/borrower";
	
	private static final Logger logger = LogManager.getLogger(BorrowerController.class);
	
	@GetMapping("/books")
	public Book[] readBooks() {
		ResponseEntity<Book[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/books", Book[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@GetMapping("/authors")
	public Author[] readAuthors() {
		ResponseEntity<Author[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/authors", Author[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@GetMapping("/books/{input}")
	public Book[] readBooksByTitleAndAuthor(@Valid @PathVariable("input") String input) {
		ResponseEntity<Book[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/books/" + input, Book[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@GetMapping("/bookloans/{cardNo}")
	public BookLoans[] readLoansByCardNo(@Valid @PathVariable("cardNo") Long cardNo) {
		ResponseEntity<BookLoans[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/bookloans/"+ cardNo, BookLoans[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@PostMapping("/bookloans")
	public void createLoan(@Valid @RequestBody BookLoans loan) {
		logger.debug("request: bookId={}, cardNo={}, branchId={}", 
				loan.getBookLoansId().getBookId(), loan.getBookLoansId().getCardNo(), loan.getBookLoansId().getBranchId());
		this.restTemplate.postForObject(baseUrl + "bookloans", loan, BookLoans.class);
	}
	
	@PutMapping("/bookloans:bookloans")
	public void updateLoan(@Valid @RequestBody BookLoans loan) {
		logger.debug("request: bookId={}, cardNo={}, branchId={}", 
				loan.getBookLoansId().getBookId(), loan.getBookLoansId().getCardNo(), loan.getBookLoansId().getBranchId());
		this.restTemplate.put(baseUrl + "bookloans", loan, BookLoans.class);
	}
	
	@GetMapping(path ="/branches")
	public Branch[] readBranches() {
		ResponseEntity<Branch[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/branches/", Branch[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@GetMapping(path = "/bookcopies/{branchId}")
	public BookCopies[] readBookCopiesByBranch(@Valid @PathVariable("branchId") Long branchId) {
		ResponseEntity<BookCopies[]> responseEntity = this.restTemplate.getForEntity(baseUrl + "/branches/"+ branchId, BookCopies[].class);
		logger.debug("response: {}", responseEntity);
		return responseEntity.getBody();
	}
	
	@PutMapping(path = "/lms/borrower/bookcopies:bookcopies")
	public void updateBookCopy(@Valid @RequestBody BookCopies bc){
		this.restTemplate.put(baseUrl + "/bookcopies:bookcopies", bc, BookCopies[].class);
		logger.debug("response: {}", bc.toString());
		
	}
	
}
