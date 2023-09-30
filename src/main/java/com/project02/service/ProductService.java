package com.project02.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project02.entity.Product;
import com.project02.repository.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;
	
	public Product saveProduct(Product product) {
		return productRepo.save(product);		
	}
	
	public List<Product> saveProducts(List<Product> product){
		return productRepo.saveAll(product);
	}
	
	public List<Product> getProducts(){
		return productRepo.findAll();
	}
	
	public Product getProductById(int id) {
		return productRepo.findById(id).orElse(null);
	}
	
	
	public String deleteProduct(int id) {
		productRepo.deleteById(id);
		return "Product removed || " +id;
	}
	
	public Product updateProduct(Product product) {
		Product existingProduct = productRepo.findById(product.getId()).orElse(null);
		existingProduct.setName(product.getName());
		existingProduct.setQuantity(product.getQuantity());
		return productRepo.save(existingProduct);
	}
}
