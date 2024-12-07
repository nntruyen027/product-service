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
    private BigDecimal basePrice;
    private List<ProductVersionDto> versions;
    private List<TagDto> tags;

    public static ProductDto fromEntity(Product entity) {
        return ProductDto.builder()
                .basePrice(entity.getBasePrice())
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static ProductDto fromEntity(Product entity, List<ProductVersionDto> versions) {
        return ProductDto.builder()
                .basePrice(entity.getBasePrice())
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .versions(versions)
                .build();
    }

    public static ProductDto fromEntity(Product entity, List<ProductVersionDto> versions,
                                        List<TagDto> tags) {
        return ProductDto.builder()
                .basePrice(entity.getBasePrice())
                .description(entity.getDescription())
                .id(entity.getId())
                .name(entity.getName())
                .versions(versions)
                .tags(tags)
                .build();
    }

}
