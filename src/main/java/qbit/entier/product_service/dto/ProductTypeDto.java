package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Product;
import qbit.entier.product_service.entity.ProductType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductTypeDto {
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String image;
    private List<ProductDto> products;

    public static ProductTypeDto fromEntity(ProductType entity) {
        return ProductTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .build();
    };

    public static ProductTypeDto fromEntity(ProductType entity, List<ProductDto> products) {
        return ProductTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .description(entity.getDescription())
                .icon(entity.getIcon())
                .products(products)
                .build();
    }

}