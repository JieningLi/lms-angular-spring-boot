package com.ss.lmshibernate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lmshibernate.entity.Author;
import com.ss.lmshibernate.entity.Borrower;
import com.ss.lmshibernate.exception.ResourceNotFoundException;
import com.ss.lmshibernate.repository.AuthorRepository;
import com.ss.lmshibernate.repository.BorrowerRepository;

@RestController
@RequestMapping(value = "/lms/admin*")
public class BorrowerController {

	@Autowired
	BorrowerRepository borrowerRepository;

	@GetMapping("/borrowers")
	public Page<Borrower> getAllBorrrowers(Pageable pageable) {
		return borrowerRepository.findAll(pageable);
	}

	@PostMapping("/borrowers")
	public Borrower createBorrower(@Valid @RequestBody Borrower borrower) {
		return borrowerRepository.save(borrower);
	}

	@PutMapping("/borrowers/{cardNo}")
	public Borrower updateBorrower(@PathVariable Long cardNo, @Valid @RequestBody Borrower borrowerRequest) {
		return borrowerRepository.findById(cardNo).map(borrower -> {
			borrower.setName(borrowerRequest.getName());
			borrower.setAddress(borrowerRequest.getAddress());
			borrower.setPhone(borrowerRequest.getPhone());
			return borrowerRepository.save(borrower);
		}).orElseThrow(() -> new ResourceNotFoundException("AuthorId " + cardNo + " not found"));
	}

	@DeleteMapping("/borrowers/{cardNo}")
	public ResponseEntity<?> deleteBorrower(@PathVariable Long cardNo) {
		return borrowerRepository.findById(cardNo).map(borrower -> {
			borrowerRepository.delete(borrower);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("CardNo " + cardNo + " not found"));
	}
}
