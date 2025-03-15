package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByTypeIdAndIsOpenedTrue(Long id, Pageable pageable);
    List<Product> findByTypeIdAndIsOpenedTrue(Long id);
    Page<Product> findByBrandIdAndIsOpenedTrue(Long id, Pageable pageable);
    List<Product> findByBrandIdAndIsOpenedTrue(Long id);
    Page<Product> findByIsOpenedTrue(Pageable pageable);
}
