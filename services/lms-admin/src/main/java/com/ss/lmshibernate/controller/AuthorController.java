package com.ss.lmshibernate.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.ss.lmshibernate.exception.ResourceNotFoundException;

import com.ss.lmshibernate.entity.Author;
import com.ss.lmshibernate.entity.Book;
import com.ss.lmshibernate.repository.AuthorRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/lms/admin*")
public class AuthorController{
	@Autowired
	AuthorRepository authorRepository;

	
	@GetMapping("/health")
	public HttpStatus isHealthy() {
		return HttpStatus.OK;
	}
	

	@GetMapping("/authors")
	public List<Author> getAllAuthors(){
		 List<Author> authors = authorRepository.findAll();
		 return authors;
	}
	

	@GetMapping("/authors/{authorId}")
	public ResponseEntity<Author> getAuthorById(@PathVariable(value = "authorId") Integer authorId)
			throws ResourceNotFoundException {
		Author author = authorRepository.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("Author not found : " + authorId));
		return ResponseEntity.ok().body(author);
	}

	// Get Authors by name
	@GetMapping(path = "/authors/search/{input}")
	public ResponseEntity<List<Author>> getAuthorsByName(@Valid @PathVariable("input") String input)
			throws ResourceNotFoundException {
		try {
			List<Author> authors = authorRepository.searchByNameike(input);
			return ResponseEntity.ok().body(authors);
		} catch (ResourceNotFoundException e) {

			throw new ResourceNotFoundException("Authors not found");
		}
	}

	@PostMapping("/authors")
	public Author createAuthor(@Valid @RequestBody Author author) {
		return authorRepository.save(author);
	}

	@PutMapping("/authors/{authorId}")
	public Author updateAuthor(@PathVariable Integer authorId, @Valid @RequestBody Author authorRequest) {
		return authorRepository.findById(authorId).map(author -> {
			author.setAuthorName(authorRequest.getAuthorName());
			return authorRepository.save(author);
		}).orElseThrow(() -> new ResourceNotFoundException("AuthorId " + authorId + " not found"));
	}

	@DeleteMapping("/authors/{authorId}")
	public ResponseEntity<?> deleteAuthor(@PathVariable Integer authorId) {
		return authorRepository.findById(authorId).map(author -> {
			authorRepository.delete(author);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("AuthorId " + authorId + " not found"));
	}

}
