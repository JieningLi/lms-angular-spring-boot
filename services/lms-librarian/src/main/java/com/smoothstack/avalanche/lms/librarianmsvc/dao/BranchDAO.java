package com.smoothstack.avalanche.lms.librarianmsvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.librarianmsvc.entity.Branch;


@Repository
public interface BranchDAO extends JpaRepository<Branch ,Long>{

}
