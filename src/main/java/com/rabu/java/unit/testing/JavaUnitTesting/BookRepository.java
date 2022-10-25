package com.rabu.java.unit.testing.JavaUnitTesting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
