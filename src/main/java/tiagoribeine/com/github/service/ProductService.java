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

    @Autowired //Ganhamos os metodos: findAll, findById, save, saveAll, deleteById etc.;;
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
        return productRepository.save(checkStockStatus(product));
    }

    //Delete a product
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    //Update a product
    public Product updateProduct(Long id, Product product){

        if(productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(checkStockStatus(product));
        } else {
            throw new RuntimeException("Product with the ID" + id + " not found");
        }
    }

    //Save a list of products - Batch
    public List<Product> saveAllProducts(List<Product> products){
        for(Product product: products){
            checkStockStatus(product);
        }
        return productRepository.saveAll(products);
    }

    private Product checkStockStatus(Product product){
        if (product.getQuantity() <= 0 ){
            product.setStatus("No Stock");
        }
        else if (product.getQuantity() <= 5){
            product.setStatus("Low Stock");
        }
        else if (product.getQuantity() > 5) {
            product.setStatus("In Stock");
        }
        return product;
    }
}
