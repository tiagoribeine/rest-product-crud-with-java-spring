package tiagoribeine.com.github.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tiagoribeine.com.github.data.dto.ProductRequestDTO;
import tiagoribeine.com.github.data.dto.ProductResponseDTO;
import tiagoribeine.com.github.model.Product;
import tiagoribeine.com.github.service.ProductService;
import java.util.List;

//@CrossOrigin(origins = "*")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService; // Autowired creates an instance of productSerivce;

    // Create
    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO requestDTO){
        return productService.saveProductDTO(requestDTO);
    }

    @PostMapping("/batch")
    public List<ProductResponseDTO> createAll(@RequestBody List<ProductRequestDTO> requestsdto){
        return productService.saveAllProductsDTO(requestsdto);
    }

    // Read
    @GetMapping("/{id}")
    public ProductResponseDTO findById(
            @PathVariable("id") Long id
    ){
        return productService.findByIdDTO(id); //findByIdDTO ja lança a exceção se não encontrar
    }

    @GetMapping
    public List<ProductResponseDTO> findAll(){

        return productService.findAllDTO();
    }

    // Update
    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable("id") Long id,
            @RequestBody ProductRequestDTO requestDTO
    ){
        return productService.updateProductDTO(id, requestDTO);
    }

    // Delete
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id
    ){
       productService.deleteProduct(id);
    }


}
