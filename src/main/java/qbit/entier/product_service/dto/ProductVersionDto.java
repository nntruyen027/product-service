package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.ProductImage;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductVersionDto {
    private Long id;
    private Long productId;
    private String versionName;
    private BigDecimal price;
    private Integer stockQuantity;
    private List<ProductImage> images;
}
