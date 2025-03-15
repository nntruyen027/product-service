package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Brand;
import qbit.entier.product_service.entity.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductEditDto {
    private Long id;
    private String name;
    private String description;
    private Long type;
    private Long brand;
    private String image;
    private Boolean isOpened;

    public static ProductEditDto fromEntity(Product entity) {
        return ProductEditDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getId())
                .brand(entity.getBrand().getId())
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .build();
    }

    public static ProductEditDto fromEntity(Product entity, List<ProductVersionDto> versions) {
        return ProductEditDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getId())
                .brand(entity.getBrand().getId())
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .build();
    }

    public static ProductEditDto fromEntity(Product entity, List<ProductVersionDto> versions,
                                            List<TagDto> tags) {
        return ProductEditDto.builder()
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType().getId())
                .brand(entity.getBrand().getId())
                .image(entity.getImage())
                .isOpened(entity.getIsOpened())
                .build();
    }

}
