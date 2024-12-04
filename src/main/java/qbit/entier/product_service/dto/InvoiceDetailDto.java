package qbit.entier.product_service.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InvoiceDetailDto {

    private Long id;
    private InvoiceDto invoice;
    private ProductVersionDto productVersion;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private BigDecimal totalPrice;
}
