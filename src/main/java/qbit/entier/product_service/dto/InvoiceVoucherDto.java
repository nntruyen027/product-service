package qbit.entier.product_service.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InvoiceVoucherDto {

    private Long id;
    private InvoiceDto invoice;
    private VoucherDto voucher;
}

