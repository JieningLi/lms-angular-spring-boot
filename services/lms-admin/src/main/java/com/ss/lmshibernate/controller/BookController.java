package com.ss.lmshibernate.controller;

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
import com.ss.lmshibernate.entity.Book;
import com.ss.lmshibernate.entity.Publisher;
import com.ss.lmshibernate.repository.AuthorRepository;
import com.ss.lmshibernate.repository.BookRepository;
import com.ss.lmshibernate.repository.PublisherRepository;
import com.ss.lmshibernate.exception.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/lms/admin*")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PublisherRepository publisherRepository;

	@GetMapping("/books")
	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@GetMapping("/books/{bookId}")
	public Page<Book> getBookById(@PathVariable(value = "bookId") Long bookId, Pageable pageable) {
		return bookRepository.findByBookId(bookId, pageable);
	}

	@PostMapping("/books/{authorId}/{publisherId}")
	public Book createBook(@PathVariable(value = "authorId") Integer authorId,
			@PathVariable(value = "publisherId") Long publisherId, @Valid @RequestBody Book book) {

		if (!authorRepository.existsById(authorId)) {
			throw new ResourceNotFoundException("Author with id " + authorId + " does not exist");
		}
		Author author = new Author();
		author.setAuthorId(authorId);
		if (!publisherRepository.existsById(publisherId)) {
			throw new ResourceNotFoundException("Publisher with id " + publisherId + " does not exist");
		}
		Publisher publisher = new Publisher();
		publisher.setPublisherId(publisherId);
		book.setAuthor(author);
		book.setPublisher(publisher);
		return bookRepository.save(book);

	}

	@PutMapping("/books/{bookId}/{authorId}/{publisherId}")
	public Book updateBook(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "authorId") Integer authorId,
			@PathVariable(value = "publisherId") Long publisherId, @Valid @RequestBody Book bookRequest) {

		if (!authorRepository.existsById(authorId) && !publisherRepository.existsById(publisherId)) {
			throw new ResourceNotFoundException("AuthorId  or PublisherId not found");
		}

		return bookRepository.findById(bookId).map(book -> {
			book.setTitle(bookRequest.getTitle());
			Author author = new Author();
			author.setAuthorId(authorId);
			Publisher publisher = new Publisher();
			publisher.setPublisherId(publisherId);
			book.setAuthor(author);
			book.setPublisher(publisher);
			return bookRepository.save(book);
		}).orElseThrow(() -> new ResourceNotFoundException("BookId " + bookId + "not found"));
	}

	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable(value = "bookId") Long bookId) {
		return bookRepository.findById(bookId).map(book -> {
			bookRepository.delete(book);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
	}

}
