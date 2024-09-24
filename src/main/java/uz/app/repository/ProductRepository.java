package uz.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
