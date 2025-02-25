package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.product_service.client.FileServerClient;
import qbit.entier.product_service.dto.ProductTypeDto;
import qbit.entier.product_service.entity.ProductType;
import qbit.entier.product_service.repository.ProductTypeRepository;
import qbit.entier.product_service.util.FileUtil;

import java.io.IOException;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private FileServerClient fileServerClient;

    public Page<ProductTypeDto> findAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable).map(i ->
                ProductTypeDto.fromEntity(i, productService.getByProductType(i.getId())));
    }

    public ProductTypeDto findById(Long id) {
        ProductType type = productTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return ProductTypeDto.fromEntity(type, productService.getByProductType(type.getId()));
    }

    public ProductTypeDto createOne(ProductType productType) throws IOException {
        ProductType createdOne = productTypeRepository.save(productType);
        return ProductTypeDto.fromEntity(createdOne);
    }

    public ProductTypeDto updateOne(Long id, ProductType one) throws IOException {
        ProductType updatedOne = productTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));

        if(one.getName() != null)
            updatedOne.setName(one.getName());
        if(one.getIcon() != null)
            updatedOne.setIcon(one.getIcon());
        if(one.getDescription() != null)
            updatedOne.setDescription(one.getDescription());
        if(one.getImage() != null)
            updatedOne.setImage(one.getImage());

        productTypeRepository.save(updatedOne);
        return ProductTypeDto.fromEntity(updatedOne);
    }

    public void deleteOne(Long id) {
        ProductType deleteOne = productTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));

        if(deleteOne.getImage() != null ) {
            String[] parts = deleteOne.getImage().split("/");
            fileServerClient.deleteFile(parts[parts.length - 1]);
        }

        productTypeRepository.delete(deleteOne);
    }
}
