package qbit.entier.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVersionService productVersionService;

    @Autowired
    private ProductTagService productTagService;

    public Page<ProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public Page<ProductDto> getByProductType(Long id, Pageable pageable) {
        return productRepository.findByTypeId(id, pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public List<ProductDto> getByProductType(Long id) {
        return productRepository.findByTypeId(id).stream().map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId()))).toList();
    }

    public Page<ProductDto> getByBrand(Long id, Pageable pageable) {
        return productRepository.findByBrandId(id, pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public List<ProductDto> getByBrand(Long id) {
        return productRepository.findByBrandId(id).stream().map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId()))).toList();
    }

}
