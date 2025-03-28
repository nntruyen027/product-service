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
public class ProductVersionDto {
    private Long id;
    private ProductDto product;
    private String versionName;
    private BigDecimal price;
    private Integer stockQuantity;
    private String images;

    public static ProductVersionDto fromEntity(ProductVersion entity) {
        return ProductVersionDto.builder()
                .id(entity.getId())
                .versionName(entity.getVersionName())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .product(ProductDto.fromEntity(entity.getProduct()))
                .images(entity.getImages())
                .build();
    }
}
