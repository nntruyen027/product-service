package qbit.entier.product_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductImageDto {
    private Long id;
    private Long productVersionId;
    private String image;
    private Boolean isMain;
}

