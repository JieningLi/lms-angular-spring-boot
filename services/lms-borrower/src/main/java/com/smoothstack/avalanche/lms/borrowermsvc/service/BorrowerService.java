package com.smoothstack.avalanche.lms.borrowermsvc.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smoothstack.avalanche.lms.borrowermsvc.dao.AuthorDAO;
import com.smoothstack.avalanche.lms.borrowermsvc.dao.BookCopiesDAO;
import com.smoothstack.avalanche.lms.borrowermsvc.dao.BookDAO;
import com.smoothstack.avalanche.lms.borrowermsvc.dao.BookLoansDAO;
import com.smoothstack.avalanche.lms.borrowermsvc.dao.BranchDAO;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.Author;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.Book;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.BookCopies;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.BookLoans;
import com.smoothstack.avalanche.lms.borrowermsvc.entity.Branch;

import javassist.NotFoundException;

@Service
@Transactional
public class BorrowerService{
	
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

	/*
	 * Functions for returning a book
	 */
	public List<BookLoans> readLoansByCardNo(Long cardNo) throws NotFoundException {
		List<BookLoans> searchLoans = loansDAO.findByCardNo(cardNo);
		if(searchLoans.size() <= 0) {
			throw new NotFoundException("Loans not found from card_no: " + cardNo);
		}
		return searchLoans;
	}
	
	public void updateBookLoans(BookLoans loan) throws IllegalArgumentException {
		Optional<BookLoans> searchLoan = loansDAO.findById(loan.getBookLoansId());
		searchLoan.orElseThrow(() -> new IllegalArgumentException("Loan not found:" + loan.toString()));
		loansDAO.save(loan);
	}


	public List<Book> readBooks() throws NotFoundException {
		List<Book> searchBook = bookDAO.findAll();
		if(searchBook.size() <= 0)
			throw new NotFoundException("Books not found");
		return searchBook;
	}
	
	public List<Author> readAuthors() throws NotFoundException {
		List<Author> searchAuthor = authorDAO.findAll();
		if(searchAuthor.size() <= 0)
			throw new NotFoundException("Authors not found");
		return searchAuthor;
	}
	
	/*
	 * Functions for checking out a book
	 */
	public List<Branch> readBranches() throws NotFoundException {
		List<Branch> searchBranches = branchDAO.findAll();
		if(searchBranches.size() <= 0) {
			throw new NotFoundException("Branches not found");
		}
		return searchBranches;
	}

	public List<BookCopies> readBookCopiesByBranch(Long branchId) throws NotFoundException {
		System.out.println("branchId is" + branchId);
		List<BookCopies> searchCopies = copiesDAO.findBookCopiesByBookCopiesId_branchId(branchId);
		if(searchCopies.size() <= 0) {
			throw new NotFoundException("Book Copies not found with Branch Id: " + branchId);
		}
		return searchCopies;
	}
	
    public void updateBookCopies(BookCopies copies) throws NotFoundException {
    	Optional<BookCopies> searchBookCopies = copiesDAO.findById(copies.getBookCopiesId());
    	searchBookCopies.orElseThrow(() -> new NotFoundException("Book Copies Not Found"));
    	copiesDAO.save(copies);
    }

	public void createLoan(BookLoans loan) throws IllegalArgumentException {
		if(loan == null || loan.getBook() == null || loan.getBranch() == null || loan.getBorrower() == null) {
			throw new IllegalArgumentException("Inproper Input");
		}
		Optional<BookLoans> searchLoan = loansDAO.findById(loan.getBookLoansId());
		if(searchLoan.isPresent()) {
			throw new IllegalArgumentException("Loans already exist with Id: BookId:" + loan.getBookLoansId().getBookId() +" BranchId:" + loan.getBookLoansId().getBranchId()
					+ " CardNo:"+ loan.getBookLoansId().getCardNo());
		}
		loansDAO.save(loan);
	}
}