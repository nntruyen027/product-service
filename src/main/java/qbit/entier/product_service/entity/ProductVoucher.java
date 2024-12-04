package qbit.entier.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_version_id", nullable = false)
    private ProductVersion productVersion;

    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;
}
