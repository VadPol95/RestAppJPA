package com.vadpol.RestAppHibernate.repository;

import com.vadpol.RestAppHibernate.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Integer> {
    List<Student> findByName(String name);
}
