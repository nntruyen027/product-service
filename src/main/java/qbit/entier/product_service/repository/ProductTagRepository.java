package qbit.entier.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.ProductTag;

import java.util.List;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    List<ProductTag> findByTagId(Long id);
    List<ProductTag> findByProductId(Long id);
    void deleteByProductId(Long id);
}
