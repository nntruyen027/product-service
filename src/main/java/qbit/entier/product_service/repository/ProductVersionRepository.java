package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.ProductVersion;

import java.util.List;

@Repository
public interface ProductVersionRepository extends JpaRepository<ProductVersion, Long> {
    Page<ProductVersion> findByProductId(Long id, Pageable pageable);
    List<ProductVersion> findByProductId(Long id);
}
