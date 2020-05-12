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
import com.ss.lmshibernate.entity.LibraryBranch;
import com.ss.lmshibernate.exception.ResourceNotFoundException;

import com.ss.lmshibernate.repository.LibraryBranchRepository;

@RestController
@RequestMapping(value = "/lms/admin*")
public class LibraryBranchController {
	@Autowired
	LibraryBranchRepository libraryBranchRepository;

	@GetMapping("/libraryBranches")
	public Page<LibraryBranch> getAllAuthors(Pageable pageable) {
		return libraryBranchRepository.findAll(pageable);
	}

	@PostMapping("/libraryBranches")
	public LibraryBranch createBranch(@Valid @RequestBody LibraryBranch libraryBranch) {
		return libraryBranchRepository.save(libraryBranch);
	}

	@PutMapping("/libraryBranches/{branchId}")
	public LibraryBranch updateBranch(@PathVariable Long branchId, @Valid @RequestBody LibraryBranch branchRequest) {
		return libraryBranchRepository.findById(branchId).map(libraryBranch -> {
			libraryBranch.setBranchName(branchRequest.getBranchName());
			libraryBranch.setBranchAddress(branchRequest.getBranchAddress());
			return libraryBranchRepository.save(libraryBranch);
		}).orElseThrow(() -> new ResourceNotFoundException("BranchId " + branchId + " not found"));
	}

	@DeleteMapping("/libraryBranches/{branchId}")
	public ResponseEntity<?> deleteBranch(@PathVariable Long branchId) {
		return libraryBranchRepository.findById(branchId).map(libraryBranch -> {
			libraryBranchRepository.delete(libraryBranch);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("BranchId " + branchId + " not found"));
	}
}