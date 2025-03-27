package qbit.entier.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_attribute_values")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductAttributeValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attribute_values_id", nullable = false)
    private AttributeValue attributeValue;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
