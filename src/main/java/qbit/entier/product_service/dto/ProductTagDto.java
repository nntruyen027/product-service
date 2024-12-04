package qbit.entier.product_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductTagDto {
    private Long id;
    private ProductVersionDto productVersion;
    private TagDto tag;
}
