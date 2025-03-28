package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.AttributeValue;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AttributeValueDto {
    private Long id;

    private String name;

    private String description;

    private AttributeDto attribute;

    public static AttributeValueDto fromEntity(AttributeValue entity) {
        return AttributeValueDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .attribute(AttributeDto.fromEntity(entity.getAttribute()))
                .build();
    }
}
