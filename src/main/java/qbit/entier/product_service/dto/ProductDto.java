package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Product;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductVersionDto> versions;
    private List<TagDto> tags;
    private String image;
    private Boolean isOpened;
    private BrandDto brand;
    private ProductTypeDto type;
    private List<AttributeValueDto> attributeValues;

    public static ProductDto fromEntity(Product entity) {
        return ProductDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .brand(BrandDto.fromEntity(entity.getBrand()))
                .type(ProductTypeDto.fromEntity(entity.getType()))
                .build();
    }

    public static ProductDto fromEntity(Product entity, List<ProductVersionDto> versions) {
        return ProductDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .versions(versions)
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .brand(BrandDto.fromEntity(entity.getBrand()))
                .type(ProductTypeDto.fromEntity(entity.getType()))
                .build();
    }

    public static ProductDto fromEntity(Product entity, List<ProductVersionDto> versions,
                                        List<TagDto> tags) {
        return ProductDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .versions(versions)
                .tags(tags)
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .brand(BrandDto.fromEntity(entity.getBrand()))
                .type(ProductTypeDto.fromEntity(entity.getType()))
                .build();
    }

    public static ProductDto fromEntity(Product entity, List<ProductVersionDto> versions,
                                        List<TagDto> tags, List<AttributeValueDto> attributeValues) {
        return ProductDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .versions(versions)
                .tags(tags)
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .brand(BrandDto.fromEntity(entity.getBrand()))
                .type(ProductTypeDto.fromEntity(entity.getType()))
                .attributeValues(attributeValues)
                .build();
    }

}
