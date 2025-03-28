package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.client.FileServerClient;
import qbit.entier.product_service.dto.AttributeValueDto;
import qbit.entier.product_service.dto.ProductAttributeEditDto;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.ProductEditDto;
import qbit.entier.product_service.entity.*;
import qbit.entier.product_service.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVersionService productVersionService;

    @Autowired
    private ProductTagService productTagService;
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private FileServerClient fileServerClient;

    @Autowired
    private ProductAttributeValueRepository productAttributeValueRepository;

    @Autowired
    private AttributeValueRepository attributeValueRepository;



    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findByIsOpenedTrue(pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public Page<ProductDto> getByProductType(Long id, Pageable pageable) {
        return productRepository.findByTypeIdAndIsOpenedTrue(id, pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public List<ProductDto> getByProductType(Long id) {
        return productRepository.findByTypeIdAndIsOpenedTrue(id).stream().map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId()))).toList();
    }

    public Page<ProductDto> getByBrand(Long id, Pageable pageable) {
        return productRepository.findByBrandIdAndIsOpenedTrue(id, pageable).map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId())));
    }

    public List<ProductDto> getByBrand(Long id) {
        return productRepository.findByBrandIdAndIsOpenedTrue(id).stream().map(i -> ProductDto
                .fromEntity(i,
                        productVersionService.getByProductId(i.getId()),
                        productTagService.findByProductId(i.getId()))).toList();
    }

    public ProductDto findById(Long id) {
        List<AttributeValueDto> attributeValueDto = this.findAttributes(id);

        return ProductDto.fromEntity(productRepository.findById(id).orElseThrow(() ->  new EntityNotFoundException("Not found")),
                productVersionService.getByProductId(id), productTagService.findByProductId(id), attributeValueDto);
    }

    public ProductDto createOne(ProductEditDto product) {
        Brand brand = brandRepository.findById(product.getBrand()).orElseThrow(() -> new EntityNotFoundException("Not found brand"));
        ProductType type = productTypeRepository.findById(product.getType()).orElseThrow(() -> new EntityNotFoundException("Not found type"));

        Product savedProduct = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .brand(brand)
                .type(type)
                .image(product.getImage())
                .isOpened(true)
                .build();

        return ProductDto.fromEntity(productRepository.save(savedProduct));
    }

    public ProductDto updateOne(Long id, ProductEditDto productEditDto) {
        Product updatedProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found product"));

        if(productEditDto.getName() != null)
            updatedProduct.setName(productEditDto.getName());
        if(productEditDto.getDescription() != null)
            updatedProduct.setDescription(productEditDto.getDescription());
        if(productEditDto.getBrand() != null) {
            Brand brand = brandRepository.findById(productEditDto.getBrand()).orElseThrow(() -> new EntityNotFoundException("Not found brand"));
            updatedProduct.setBrand(brand);
        }
        if(productEditDto.getType() != null) {
            ProductType type = productTypeRepository.findById(productEditDto.getType()).orElseThrow(() -> new EntityNotFoundException("Not found type"));
            updatedProduct.setType(type);
        }
        if(productEditDto.getImage() != null) {
            if(updatedProduct.getImage() != null) {
                String[] parts = updatedProduct.getImage().split("/");
                fileServerClient.deleteFile(parts[parts.length - 1]);
            }
            updatedProduct.setImage(productEditDto.getImage());
        }

        return ProductDto.fromEntity(productRepository.save(updatedProduct));
    }

    public void deleteOne(Long id) {
        Product deletedProduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found product"));
        if(deletedProduct.getImage() != null) {
            String[] parts = deletedProduct.getImage().split("/");
            fileServerClient.deleteFile(parts[parts.length - 1]);
        }
        deletedProduct.setIsOpened(false);
        productAttributeValueRepository.deleteByProductId(id);
        productRepository.save(deletedProduct);
    }

    public List<AttributeValueDto> findAttributes(Long productId) {
        return productAttributeValueRepository.findByProductId(productId).stream().map(e -> AttributeValueDto.fromEntity(e.getAttributeValue()))
                .toList();
    }

    public ProductDto updateAttributeValues(Long productId, List<Long> attributes) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Không có product"));
        productAttributeValueRepository.deleteByProductId(productId);

        List<AttributeValueDto> result = new ArrayList<>();
        for(Long id : attributes) {
            Optional<AttributeValue> attributeValue = attributeValueRepository.findById(id);
            if(attributeValue.isEmpty()) {
                continue;
            }
            else {
                ProductAttributeValue value = new ProductAttributeValue();
                value.setAttributeValue(attributeValue.get());
                value.setProduct(product);
                result.add(AttributeValueDto.fromEntity(productAttributeValueRepository.save(value).getAttributeValue()));
            }
        }

        return findById(productId);
    }

}
