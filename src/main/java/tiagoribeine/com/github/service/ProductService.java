package tiagoribeine.com.github.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiagoribeine.com.github.data.dto.ProductRequestDTO;
import tiagoribeine.com.github.data.dto.ProductResponseDTO;
import tiagoribeine.com.github.model.Product;
import tiagoribeine.com.github.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tiagoribeine.com.github.mapper.ObjectMapper.parseListObjects;
import static tiagoribeine.com.github.mapper.ObjectMapper.parseObject;

@Service
public class ProductService {

    @Autowired //Ganhamos os metodos: findAll, findById, save, saveAll, deleteById etc.;;
    private ProductRepository productRepository;

    //find all
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    //find by Id
    public Optional<Product> findById(Long id){
        return productRepository.findById(id);
    }

    //Create new product
    public Product saveProduct(Product product){
        return productRepository.save(checkStockStatus(product));
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

    //Delete a product
    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    //Save a list of products - Batch
    public List<Product> saveAllProducts(List<Product> products){
        return productRepository.saveAll(products);
    }

    //============================ Regras de negócio ===============================//

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

    //============================ Metodos DTOs ===============================//

    //find all - DTO
    public List<ProductResponseDTO> findAllDTO(){

        //Busca todos os produtos do banco e os armazena em uma lista de Produtos
        List<Product> products = productRepository.findAll();

        //Convertendo List<Product> para List<ProductResponseDTO> utilizando o ObjectMapper criado
        List<ProductResponseDTO> responseDTOS = parseListObjects(products, ProductResponseDTO.class);

        // 3. Aplicando Regras de negócio: Para CADA produto, aplica a regra de negócio
        List<Product> productsWithStatus = new ArrayList<>();
        for (Product product : products) {
        Product productWithStatus = checkStockStatus(product);
        productsWithStatus.add(productWithStatus);
        }

        //Retornando o DTO para o Client
        return parseListObjects(productsWithStatus, ProductResponseDTO.class);


    };

    //find by ID - DTO
    public ProductResponseDTO findByIdDTO(Long id){

        //Armazenando Produto em uma variável
        Product product = productRepository.findById(id)
                //Tratamento de exceções para caso o produto não seja encontrado
                .orElseThrow(() -> new RuntimeException("Product notFound"));

        // 2. Aplicando a regra de negócio
        // product = checkStockStatus(product);

        //Convertendo product para um productDTO
        ProductResponseDTO productDTO = parseObject(product, ProductResponseDTO.class);

        //Retornando o productDTO para o client
        return productDTO;
    }

    // Create
    public ProductResponseDTO saveProductDTO(ProductRequestDTO requestDTO){

        //Convertendo o RequestDTO em Entity para salvar no banco de dados - Não se salva DTO diretamenteno banco de dados pois não é uma Entity JPA
        Product productEntity = parseObject(requestDTO, Product.class);

        //Aplicando a regra de negócio(Nesse caso é obrigatório - senão os produtos ficam sem status no banco de dados
        productEntity = checkStockStatus(productEntity); //Preencherá o status de acordo com a regra de negócio

        //Salvando a Entity no banco de dados
        Product savedEntity = productRepository.save(productEntity);

        //Convertendo a Entity salva para um ResponseDTO - PARA SER EXIBIDO AO CLIENT
        ProductResponseDTO responseDTO = parseObject(savedEntity, ProductResponseDTO.class);

        //Retornando o ResponseDTO
        return responseDTO;
    }

    //Save a list of products - Batch - CREATE ALL
    public List<ProductResponseDTO> saveAllProductsDTO(List<ProductRequestDTO> requestsDTOs){

        List<Product> products = new ArrayList<>();

        //Convertendo cada requestDTO em product(entidade) para armazenar em uma lista e conseguirmos salvar em um banco de dados.
        for(ProductRequestDTO requestDTO: requestsDTOs){
            Product product = parseObject(requestDTO, Product.class);
            product = checkStockStatus(product); //Aplica regra de negócio
            products.add(product);
        }

        //Salvando os produtos no banco de dados e armazenando-os em uma lista para converte-los em DTO antes de retorna-los
        List<Product> savedProducts = productRepository.saveAll(products);

        //Convertendo os Products salvos em DTO para retorna-los ao client
        List<ProductResponseDTO> responseDTOs = new ArrayList<>();
        for(Product savedProduct: savedProducts){
            ProductResponseDTO responseDTO = parseObject(savedProduct, ProductResponseDTO.class);
            responseDTOs.add(responseDTO);
        }

        // Retornando os responseDTOs
        return responseDTOs;

    }

    //Update
    public ProductResponseDTO updateProductDTO(Long id, ProductRequestDTO requestDTO){

        //Resgatando o produto pelo ID passado como parâmetro e salvando na variável product + tratamento de exceções
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        //Checando se os valores informados pelo client são nulos, e caso não sejam, atualizaremos o produto de acordo
        if(requestDTO.getName() != null){
            product.setName(requestDTO.getName());
        }

        if(requestDTO.getPrice() != null){
            product.setPrice(requestDTO.getPrice());
        }

        if(requestDTO.getQuantity() != null){
            product.setQuantity(requestDTO.getQuantity());
        }

        //Aplicando a regra de negócio(Nesse caso é obrigatório - senão os produtos ficam sem status no banco de dados
        product = checkStockStatus(product);

        //Salvando o Produto atualizado no banco de dados
        Product updatedProduct = productRepository.save(product);

        //Convertendo o produto atualizado para ResponseDTO
        ProductResponseDTO responseDTO = parseObject(updatedProduct, ProductResponseDTO.class);

        //Retornando ao Client o DTO do produto atualizado
        return responseDTO;
    }

    //Delete não altera com os metodos DTO
}

