package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import qbit.entier.product_service.entity.ProductImage;

import java.util.List;

public interface ProductVersionImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductVersionId(Long id);

}
