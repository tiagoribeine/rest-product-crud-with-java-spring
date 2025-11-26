package tiagoribeine.com.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tiagoribeine.com.github.model.Product;
import tiagoribeine.com.github.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService; // Autowired creates an instance of productSerivce;

    // Create
    @PostMapping
    public Product create(@RequestBody Product product){
        return productService.saveProduct(product);
    }

    @PostMapping("/batch")
    public List<Product> createAll(@RequestBody List<Product> products){
        return productService.saveAllProducts(products);
    }

//    @PostMapping(
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public Product create(@RequestBody Product product){
//        return productService.saveProduct(product);
//    }

    // Read
    @GetMapping("/{id}")
    public Product read(
            @PathVariable("id") Long id
    ){
        return productService.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @GetMapping
    public List<Product> readAll(){
        return productService.listAll();
    }

    // Update
    @PutMapping("/{id}")
    public Product update(
            @PathVariable("id") Long id,
            @RequestBody Product product
    ){
        return productService.updateProduct(id, product);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ){
       productService.deleteProduct(id);
    }


}
