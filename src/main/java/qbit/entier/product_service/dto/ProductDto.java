package qbit.entier.product_service.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private List<ProductVersionDto> versions;
}
