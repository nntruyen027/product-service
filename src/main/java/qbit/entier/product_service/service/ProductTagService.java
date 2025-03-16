package qbit.entier.product_service.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.TagDto;
import qbit.entier.product_service.entity.Product;
import qbit.entier.product_service.entity.ProductTag;
import qbit.entier.product_service.entity.Tag;
import qbit.entier.product_service.repository.ProductRepository;
import qbit.entier.product_service.repository.ProductTagRepository;
import qbit.entier.product_service.repository.TagRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductTagService {
    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<TagDto> findByProductId(Long id) {
        return productTagRepository.findByProductId(id)
                .stream().map(ProductTag::getTag).map(TagDto::fromEntity).toList();
    }

    public List<ProductDto> findByTagId(Long id) {
        return productTagRepository.findByProductId(id)
                .stream().map(ProductTag::getProduct).map(ProductDto::fromEntity).toList();
    }

    @Transactional
    public ProductDto assignTagToProduct(Long productId, String tagIds) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        List<Long> tagIdList = Arrays.stream(tagIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();

        productTagRepository.deleteByProductId(productId);

        for (Long tagId : tagIdList) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + tagId));

            ProductTag productTag = ProductTag.builder()
                    .product(product)
                    .tag(tag)
                    .build();

            productTagRepository.save(productTag);
        }

        return ProductDto.fromEntity(product, null, findByProductId(productId));
    }
}
