package qbit.entier.product_service.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ImportReceiptDto {
    private Long id;
    private SupplierDto supplierId;
    private String receiptDate;
    private BigDecimal totalAmount;
    private String createdAt;
    private String updatedAt;
}
