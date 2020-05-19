package com.smoothstack.avalanche.lms.librarianmsvc.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smoothstack.avalanche.lms.librarianmsvc.entity.BookCopies;
import com.smoothstack.avalanche.lms.librarianmsvc.entity.Branch;
import com.smoothstack.avalanche.lms.librarianmsvc.service.LibrarianService;

import javassist.NotFoundException;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
public class LibrarianController {
	
	@Autowired
	LibrarianService librarianService;
	
	private static final Logger logger = LogManager.getLogger(LibrarianController.class);
	
	@GetMapping(path = "/lms/librarian/branch")
	public ResponseEntity<List<Branch>> readBranches(){
		logger.info("Librarian: Read Branches");
		try {
			List<Branch> searchBranch = librarianService.readBranches();
			return new ResponseEntity<List<Branch>>(searchBranch, new HttpHeaders(), HttpStatus.OK);
		}
		catch(NotFoundException e) {
			logger.error("Branches not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Branches not found");
		}
	}
	
	@GetMapping(path = "/lms/librarian/branch/{branchId}")
	public Branch readBranchByBranchId(@Valid @PathVariable("branchId") Long branchId) throws ClassNotFoundException, IllegalArgumentException, SQLException, NotFoundException{
		logger.info("Check Book Copies from: " + branchId);
		try{
			return librarianService.readBranchById(branchId);
		} catch(NotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	@PutMapping(path = "/lms/librarian/branch")
	public ResponseEntity<Branch> updateBranch(@Valid @RequestBody Branch branch){
		logger.info("Branch is being updated");
		try {
			librarianService.updateBranch(branch);
			ResponseEntity<Branch> response= new ResponseEntity<Branch>(branch, HttpStatus.ACCEPTED);
			return response;
		}
		catch(IllegalArgumentException e) {
			logger.error("Book loan update failed: " + e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e);
		}
	}
	
	
	@GetMapping(path = "/lms/librarian/bookcopies/{branchId}")
	public List<BookCopies> readBookCopiesByBranch(@Valid @PathVariable("branchId") Long branchId) throws ClassNotFoundException, IllegalArgumentException, SQLException, NotFoundException{
		logger.info("Check Book Copies from: " + branchId);
		try{
			List<BookCopies> searchBookCopies = librarianService.readBookCopiesByBranch(branchId);
			return searchBookCopies;
		} catch(NotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PostMapping(path = "/lms/librarian/bookcopies")
	public ResponseEntity<BookCopies> createBookCopy(@Valid @RequestBody BookCopies bc) throws ClassNotFoundException, SQLException, NotFoundException{
		logger.info("Checking out book: " + bc.getBookCopiesId().getBookId() + "from :" + bc.getBookCopiesId().getBranchId());
		try{
			librarianService.createBookCopy(bc);
			ResponseEntity<BookCopies> response = new ResponseEntity<BookCopies>(bc, HttpStatus.CREATED);
			return response;
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@PutMapping(path = "/lms/librarian/bookcopies")
	public ResponseEntity<BookCopies> updateBookCopy(@Valid @RequestBody BookCopies bc) throws ClassNotFoundException, SQLException, NotFoundException{
		logger.info("Checking out book: " + bc.getBookCopiesId().getBookId() + "from :" + bc.getBookCopiesId().getBranchId());
		try{
			librarianService.updateBookCopies(bc);
			ResponseEntity<BookCopies> response = new ResponseEntity<BookCopies>(bc, HttpStatus.NO_CONTENT);
			return response;
		} catch(NotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@DeleteMapping(path ="/lms/librarian/bookcopies:bookcopies")
	public ResponseEntity<BookCopies> deleteBookCopy(@Valid @RequestBody BookCopies bc){
		logger.info("Deleting copies of " + bc.getBook().getTitle() + " from: " + bc.getBranch().getId());
		try {
			librarianService.removeBookCopies(bc);
			ResponseEntity<BookCopies> responseEntity = new ResponseEntity<BookCopies>(bc, HttpStatus.NO_CONTENT);
			return responseEntity;
		} catch(IllegalArgumentException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		} catch(NotFoundException e) {
			logger.error(e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	
}
