package qbit.entier.product_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.TagDto;
import qbit.entier.product_service.entity.Product;
import qbit.entier.product_service.entity.ProductTag;
import qbit.entier.product_service.repository.ProductTagRepository;

import java.util.List;

@Service
public class ProductTagService {
    @Autowired
    private ProductTagRepository productTagRepository;

    public List<TagDto> findByProductId(Long id) {
        return productTagRepository.findByProductId(id)
                .stream().map(ProductTag::getTag).map(TagDto::fromEntity).toList();
    }

    public List<ProductDto> findByTagId(Long id) {
        return productTagRepository.findByProductId(id)
                .stream().map(ProductTag::getProduct).map(ProductDto::fromEntity).toList();
    }
}
