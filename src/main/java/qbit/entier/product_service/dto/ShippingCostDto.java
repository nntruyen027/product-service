package qbit.entier.product_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingCostDto {
    private Long id;
    private String region;
    private BigDecimal cost;
}
