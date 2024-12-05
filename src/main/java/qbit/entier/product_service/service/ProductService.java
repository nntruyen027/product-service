package qbit.entier.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.entity.Product;
import qbit.entier.product_service.repository.ProductRepository;
import qbit.entier.product_service.repository.ProductVersionRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVersionService productVersionService;

    public Page<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(i -> ProductDto.builder()
                .basePrice(i.getBasePrice())
                .description(i.getDescription())
                .id(i.getId())
                .name(i.getName())
                .versions(productVersionService.getByProductId(i.getId()))
                .build());
    }

    public Page<ProductDto> getByProductType(Long id, Pageable pageable) {
        return productRepository.findByTypeId(id, pageable).map(i -> ProductDto.builder()
                .basePrice(i.getBasePrice())
                .description(i.getDescription())
                .id(i.getId())
                .name(i.getName())
                .versions(productVersionService.getByProductId(i.getId()))
                .build());
    }

    public List<ProductDto> getByProductType(Long id) {
        return productRepository.findByTypeId(id).stream().map(i -> ProductDto.builder()
                .basePrice(i.getBasePrice())
                .description(i.getDescription())
                .id(i.getId())
                .name(i.getName())
                .versions(productVersionService.getByProductId(i.getId()))
                .build()).toList();
    }
}
