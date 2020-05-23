package com.smoothstack.avalanche.lms.librarianmsvc.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smoothstack.avalanche.lms.librarianmsvc.entity.Genre;


@Repository
public interface GenreDAO extends JpaRepository<Genre ,Long>{

}
