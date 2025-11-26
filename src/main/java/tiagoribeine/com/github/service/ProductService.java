package tiagoribeine.com.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiagoribeine.com.github.model.Product;
import tiagoribeine.com.github.repository.ProductRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //List all
    public List<Product> listAll(){
        return productRepository.findAll();
    }

    //find by Id
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    //Save new product
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    //Delete a product
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    //Update a product
    public Product updateProduct(Long id, Product product){

        if(productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product with the ID" + id + " not found");
        }
    }
}
