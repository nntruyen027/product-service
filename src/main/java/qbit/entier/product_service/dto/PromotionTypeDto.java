package qbit.entier.product_service.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PromotionTypeDto {
    private Long id;

    private String name;

    private String description;
}
