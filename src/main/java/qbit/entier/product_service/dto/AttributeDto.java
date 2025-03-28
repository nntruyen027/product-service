package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Attribute;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttributeDto {
    private Long id;
    private String name;
    private String description;
    private AttributeTypeDto type;

    public static AttributeDto fromEntity(Attribute entity) {
        return AttributeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(AttributeTypeDto.fromEntity(entity.getType()))
                .build();
    }
}
