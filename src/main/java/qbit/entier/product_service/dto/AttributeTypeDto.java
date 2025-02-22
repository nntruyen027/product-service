package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.AttributeType;
import qbit.entier.product_service.entity.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttributeTypeDto {
    private Long id;
    private String name;
    private String description;

    public static AttributeTypeDto fromEntity(AttributeType entity) {
        return AttributeTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}