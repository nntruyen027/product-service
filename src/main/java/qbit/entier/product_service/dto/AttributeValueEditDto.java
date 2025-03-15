package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.AttributeValue;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AttributeValueEditDto {
    private Long id;

    private String name;

    private String description;

    private Long attribute;

    public static AttributeValueEditDto fromEntity(AttributeValue entity) {
        return AttributeValueEditDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .attribute(entity.getAttribute().getId())
                .build();
    }
}
