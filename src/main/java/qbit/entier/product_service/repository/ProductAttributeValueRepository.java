package qbit.entier.product_service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.ProductAttributeValue;

import java.util.List;

@Repository
public interface ProductAttributeValueRepository extends JpaRepository<ProductAttributeValue, Long> {
    Page<ProductAttributeValue> findByProductId(Long id, Pageable pageable);
    List<ProductAttributeValue> findByProductId(Long id);
    @Transactional
    @Modifying
    void deleteByProductId(Long id);
}
