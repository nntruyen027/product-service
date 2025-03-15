package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.Attribute;
import qbit.entier.product_service.entity.ProductImage;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Page<Attribute> findByProductVersionId(Long id, Pageable pageable);
}
