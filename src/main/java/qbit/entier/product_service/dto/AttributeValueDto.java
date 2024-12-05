package qbit.entier.product_service.dto;

import lombok.*;

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

    private Attribute attribute;
}
