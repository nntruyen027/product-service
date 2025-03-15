package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.AttributeValue;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    Page<AttributeValue> findByAttributeId(Long id, Pageable pageable);
}
