package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.ProductVersion;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductVersionEditDto {
    private Long id;
    private Long product;
    private String versionName;
    private BigDecimal price;
    private Integer stockQuantity;
    private String images;

    public static ProductVersionEditDto fromEntity(ProductVersion entity) {
        return ProductVersionEditDto.builder()
                .id(entity.getId())
                .versionName(entity.getVersionName())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .product(entity.getProduct().getId())
                .build();
    }
}
