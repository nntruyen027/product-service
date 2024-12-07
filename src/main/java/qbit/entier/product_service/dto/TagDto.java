package qbit.entier.product_service.dto;

import lombok.*;
import qbit.entier.product_service.entity.Tag;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TagDto {
    private Long id;
    private String name;
    private String description;

    public static TagDto fromEntity(Tag entity) {
        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}