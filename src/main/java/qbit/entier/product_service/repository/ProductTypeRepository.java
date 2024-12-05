package qbit.entier.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
}
