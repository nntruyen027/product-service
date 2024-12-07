package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Brand;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BrandDto {
    private Long id;
    private String name;
    private String image;
    private String description;
    private List<ProductDto> products;

    public static BrandDto fromEntity(Brand entity) {
        return BrandDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .description(entity.getDescription())
                .build();
    }

    public static BrandDto fromEntity(Brand entity, List<ProductDto> products) {
        return BrandDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .description(entity.getDescription())
                .products(products)
                .build();
    }
}
