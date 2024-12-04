package qbit.entier.product_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductVoucherDto {

    private Long id;
    private ProductVersionDto productVersion;
    private VoucherDto voucherId;
}

