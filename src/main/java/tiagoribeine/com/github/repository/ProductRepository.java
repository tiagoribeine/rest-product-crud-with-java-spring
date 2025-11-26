package tiagoribeine.com.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tiagoribeine.com.github.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
