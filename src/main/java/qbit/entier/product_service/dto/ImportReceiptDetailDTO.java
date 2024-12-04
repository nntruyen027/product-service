package qbit.entier.product_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportReceiptDetailDTO {

    private Long id;
    private ImportReceiptDto receiptId;
    private ProductVersionDto productVersionId;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}

