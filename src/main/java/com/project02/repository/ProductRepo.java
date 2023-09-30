package com.project02.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project02.entity.Product;


public interface ProductRepo extends JpaRepository<Product, Integer>{

	

}
