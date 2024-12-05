package qbit.entier.product_service.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeProductDto {
    ProductTypeDto type;
    List<ProductDto> products;
}
