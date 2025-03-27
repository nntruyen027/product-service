package qbit.entier.product_service.dto;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductAttributeEditDto {
    private List<Long> ids;
}
