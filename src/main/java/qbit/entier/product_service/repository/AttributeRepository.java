package qbit.entier.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qbit.entier.product_service.entity.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Page<Attribute> findByTypeId(Long id, Pageable pageable);
}
