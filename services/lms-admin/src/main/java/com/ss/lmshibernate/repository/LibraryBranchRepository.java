package com.ss.lmshibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ss.lmshibernate.entity.LibraryBranch;

@Repository
public interface LibraryBranchRepository extends JpaRepository<LibraryBranch, Long> {

}
