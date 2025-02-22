package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Attribute;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AttributeEditDto {
    private Long id;
    private String name;
    private String description;
    private Long type;

    public static AttributeEditDto fromEntity(Attribute entity) {
        return AttributeEditDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType().getId())
                .build();
    }
}
