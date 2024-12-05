package qbit.entier.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.ProductVersionDto;
import qbit.entier.product_service.entity.ProductImage;
import qbit.entier.product_service.entity.ProductVersion;
import qbit.entier.product_service.repository.ProductVersionImageRepository;
import qbit.entier.product_service.repository.ProductVersionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVersionService {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private ProductVersionImageRepository productVersionImageRepository;

    public Page<ProductVersionDto> getAll(Pageable pageable) {
        return productVersionRepository.findAll(pageable).map(i -> ProductVersionDto.builder()
                .productId(i.getProduct().getId())
                .id(i.getId())
                .images(productVersionImageRepository.findByProductVersionId(i.getId()))
                .price(i.getPrice())
                .stockQuantity(i.getStockQuantity())
                .versionName(i.getVersionName())
                .build());
    }

    public Page<ProductVersionDto> getByProductId(Long id, Pageable pageable) {
        return productVersionRepository.findByProductId(id, pageable).map(i -> ProductVersionDto.builder()
                .productId(i.getProduct().getId())
                .id(i.getId())
                .images(productVersionImageRepository.findByProductVersionId(i.getId()))
                .price(i.getPrice())
                .stockQuantity(i.getStockQuantity())
                .versionName(i.getVersionName())
                .build());
    }

    public List<ProductVersionDto> getByProductId(Long id) {
        return productVersionRepository.findByProductId(id).stream()
                .map(i -> ProductVersionDto.builder()
                        .productId(i.getProduct().getId())
                        .id(i.getId())
                        .images(productVersionImageRepository.findByProductVersionId(i.getId()))
                        .price(i.getPrice())
                        .stockQuantity(i.getStockQuantity())
                        .versionName(i.getVersionName())
                        .build())
                .collect(Collectors.toList());

    }
}
