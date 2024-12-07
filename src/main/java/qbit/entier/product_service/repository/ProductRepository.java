package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByTypeId(Long id, Pageable pageable);
    List<Product> findByTypeId(Long id);
    Page<Product> findByBrandId(Long id, Pageable pageable);
    List<Product> findByBrandId(Long id);
}
