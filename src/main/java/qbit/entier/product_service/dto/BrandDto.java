package qbit.entier.product_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class BrandDto {
    private Long id;
    private String name;
    private String image;
    private String description;
}
