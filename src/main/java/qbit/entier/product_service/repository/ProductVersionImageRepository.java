package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.ProductImage;

import java.util.List;

@Repository
public interface ProductVersionImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductVersionId(Long id);

}
