package com.smoothstack.avalanche.lms.librarianmsvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.avalanche.lms.librarianmsvc.dao.AuthorDAO;
import com.smoothstack.avalanche.lms.librarianmsvc.dao.BookCopiesDAO;
import com.smoothstack.avalanche.lms.librarianmsvc.dao.BookDAO;
import com.smoothstack.avalanche.lms.librarianmsvc.dao.BookLoansDAO;
import com.smoothstack.avalanche.lms.librarianmsvc.dao.BranchDAO;
import com.smoothstack.avalanche.lms.librarianmsvc.entity.Book;
import com.smoothstack.avalanche.lms.librarianmsvc.entity.BookCopies;
import com.smoothstack.avalanche.lms.librarianmsvc.entity.Branch;

import javassist.NotFoundException;

@Service
@Transactional
public class LibrarianService {
	@Autowired
	private BookLoansDAO loansDAO;
	
	@Autowired
	private BranchDAO branchDAO;
	
	@Autowired
	private BookCopiesDAO copiesDAO;
	
	@Autowired
	private AuthorDAO authorDAO;
	
	@Autowired
	private BookDAO bookDAO;
	
	//Branch Functions
	public List<Branch> readBranches() throws NotFoundException {
		List<Branch> searchBranches = branchDAO.findAll();
		if(searchBranches.size() <= 0) {
			throw new NotFoundException("Branches not found");
		}
		return searchBranches;
	}
	
	public Branch readBranchById(Long branchId) throws NotFoundException{
		Optional<Branch> searchBranch = branchDAO.findById(branchId);
		if(searchBranch.isPresent()) {
			return searchBranch.get();
		}
		else {
			throw new NotFoundException("Branch does not exist: Id:" + branchId);
		}
	}
	public void updateBranch(Branch branch) {
		Optional<Branch> searchBranch = branchDAO.findById(branch.getId());
		searchBranch.orElseThrow(() -> new IllegalArgumentException("Branch not found: Id:" + branch.getId() + ", name: " + branch.getName()));
		branchDAO.save(branch);
	}
	
	//books
	public List<Book> readBooks() throws NotFoundException {
		List<Book> searchBook = bookDAO.findAll();
		if(searchBook.size() <= 0)
			throw new NotFoundException("Books not found");
		return searchBook;
	}
	
	//books not in library
	public List<Book> readBooksNotInLibrary(Long id) throws NotFoundException{
		List<Book> searchBook = bookDAO.findAll();
		List<BookCopies> searchBookCopies = copiesDAO.findBookCopiesByBookCopiesId_branchId(id);
		List<Book> unlistedBooks = new ArrayList<Book>();
		List<Book> listedBooks = new ArrayList<Book>();
		searchBookCopies.forEach(bc -> {
			listedBooks.add(bc.getBook());
		});
		searchBook.forEach(book ->{
			if(!listedBooks.contains(book)) {
				unlistedBooks.add(book);
			}
		});
		return unlistedBooks;
	}
	
	//Book Copies Function
	public List<BookCopies> readBookCopiesByBranch(Long branchId) throws NotFoundException {
		System.out.println("branchId is" + branchId);
		List<BookCopies> searchCopies = copiesDAO.findBookCopiesByBookCopiesId_branchId(branchId);
		if(searchCopies.size() <= 0) {
			throw new NotFoundException("Book Copies not found with Branch Id: " + branchId);
		}
		return searchCopies;
	}
	
	public void createBookCopy(BookCopies bc) throws IllegalArgumentException{
		if(bc == null || bc.getBook()== null || bc.getBranch()== null) {
			throw new IllegalArgumentException("Inproper Input");
		}
		Optional<BookCopies> searchCopies = copiesDAO.findById(bc.getBookCopiesId());
		if(searchCopies.isPresent()) {
			throw new IllegalArgumentException("Book: " + bc.getBook().getTitle() + " already exists in " + bc.getBranch().getName());
		}
		copiesDAO.save(bc);
	}
	
    public void updateBookCopies(BookCopies copies) throws NotFoundException {
    	Optional<BookCopies> searchBookCopies = copiesDAO.findById(copies.getBookCopiesId());
    	searchBookCopies.orElseThrow(() -> new NotFoundException("Book Copies Not Found"));
    	copiesDAO.save(copies);
    }

    public void removeBookCopies(BookCopies bc) throws IllegalArgumentException, NotFoundException{
    	if(bc == null || bc.getBook()== null || bc.getBranch()== null) {
			throw new IllegalArgumentException("Inproper Input");
		}
    	List<BookCopies> searchCopies = copiesDAO.findBookCopiesByBookCopiesId(bc.getBookCopiesId());
    	if(searchCopies.size() <= 0) {
			throw new NotFoundException("Book Copies not found");
		}
    	copiesDAO.delete(bc);
    }
    
}
