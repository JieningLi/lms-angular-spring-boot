package com.ss.lmshibernate.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ss.lmshibernate.entity.Author;
import com.ss.lmshibernate.entity.Book;
import com.ss.lmshibernate.entity.Publisher;
import com.ss.lmshibernate.exception.ResourceNotFoundException;
import com.ss.lmshibernate.repository.AuthorRepository;
import com.ss.lmshibernate.repository.PublisherRepository;

@RestController
@RequestMapping(value = "/lms/admin*")
public class PublisherController {

	@Autowired
	PublisherRepository publisherRepository;

	@GetMapping("/publishers")
	public Page<Publisher> getAllPublishers(Pageable pageable) {
		return publisherRepository.findAll(pageable);
	}

	@PostMapping("/publishers")
	public Publisher createPublisher(@Valid @RequestBody Publisher publisher) {
		return publisherRepository.save(publisher);
	}

	@PutMapping("/publishers/{publisherId}")
	public Publisher updatePublisher(@PathVariable Long publisherId, @Valid @RequestBody Publisher publisherRequest) {
		return publisherRepository.findById(publisherId).map(publisher -> {
			publisher.setPublisherName(publisherRequest.getPublisherName());
			publisher.setPublisherAddress(publisherRequest.getPublisherAddress());
			publisher.setPublisherPhone(publisherRequest.getPublisherPhone());
			return publisherRepository.save(publisher);
		}).orElseThrow(() -> new ResourceNotFoundException("PublisherId " + publisherId + " not found"));
	}

	@DeleteMapping("/publishers/{publisherId}")
	public ResponseEntity<?> deletePost(@PathVariable Long publisherId) {
		return publisherRepository.findById(publisherId).map(publisher -> {
			publisherRepository.delete(publisher);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("PublisherId " + publisherId + " not found"));
	}

}
