package qbit.entier.product_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SupplierDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
