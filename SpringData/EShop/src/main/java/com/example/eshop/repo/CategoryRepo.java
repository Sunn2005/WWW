package com.example.eshop.repo;

import com.example.eshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByNameAsc();
}
