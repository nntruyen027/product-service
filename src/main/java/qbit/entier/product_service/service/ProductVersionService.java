package qbit.entier.product_service.service;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.client.FileServerClient;
import qbit.entier.product_service.dto.ImageDto;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.ProductVersionDto;
import qbit.entier.product_service.dto.ProductVersionEditDto;
import qbit.entier.product_service.entity.Product;
import qbit.entier.product_service.entity.ProductVersion;
import qbit.entier.product_service.repository.ProductRepository;
import qbit.entier.product_service.repository.ProductVersionRepository;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVersionService {
    @Autowired
    private ProductVersionRepository productVersionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileServerClient fileServerClient;


    public Page<ProductVersionDto> getByProductId(Long id, Pageable pageable) {
        return productVersionRepository.findByProductId(id, pageable).map(i -> ProductVersionDto.builder()
                .product(ProductDto.fromEntity(i.getProduct()))
                .id(i.getId())
                .images(i.getImages())
                .price(i.getPrice())
                .stockQuantity(i.getStockQuantity())
                .versionName(i.getVersionName())
                .build());
    }

    public List<ProductVersionDto> getByProductId(Long id) {
        return productVersionRepository.findByProductId(id).stream()
                .map(i -> ProductVersionDto.builder()
                        .product(ProductDto.fromEntity(i.getProduct()))
                        .id(i.getId())
                        .images(i.getImages())
                        .price(i.getPrice())
                        .stockQuantity(i.getStockQuantity())
                        .versionName(i.getVersionName())
                        .build())
                .collect(Collectors.toList());
    }

    public ProductVersionDto findById(Long id) {
        return ProductVersionDto.fromEntity(
                productVersionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found")));
    }

    public ProductVersionDto createOne(ProductVersionEditDto productVersionEditDto) {
        Product product = productRepository.findById(productVersionEditDto.getProduct()).orElseThrow(() -> new EntityNotFoundException("Not found"));

        ProductVersion productVersion = ProductVersion.builder()
                .versionName(productVersionEditDto.getVersionName())
                .images(productVersionEditDto.getImages())
                .price(productVersionEditDto.getPrice())
                .product(product)
                .stockQuantity(0)
                .build();

        return ProductVersionDto.fromEntity(productVersionRepository.save(productVersion));
    }

    public ProductVersionDto updateOne(Long id, ProductVersionEditDto productVersionEditDto) {
        ProductVersion productVersion = productVersionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));

        if (productVersionEditDto.getVersionName() != null) {
            productVersion.setVersionName(productVersionEditDto.getVersionName());
        }
        if (productVersionEditDto.getImages() != null) {
            productVersion.setImages(productVersionEditDto.getImages());
        }
        if (productVersionEditDto.getPrice() != null) {
            productVersion.setPrice(productVersionEditDto.getPrice());
        }
        if (productVersionEditDto.getProduct() != null) {
            Product product = productRepository.findById(productVersionEditDto.getProduct()).orElseThrow(() -> new EntityNotFoundException("Not found"));
            productVersion.setProduct(product);
        }
        return ProductVersionDto.fromEntity(productVersionRepository.save(productVersion));
    }

    public ProductVersionDto addItem(Long id, int quantity) throws Exception {
        ProductVersion productVersion = productVersionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(quantity <= 0) {
            throw new Exception("Invalid value");
        }
        productVersion.setStockQuantity(productVersion.getStockQuantity() + quantity);
        return ProductVersionDto.fromEntity(productVersionRepository.save(productVersion));
    }

    public ProductVersionDto subtractItem(Long id, int quantity) throws Exception {
        ProductVersion productVersion = productVersionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(quantity > productVersion.getStockQuantity()) {
            throw new Exception("Invalid value");
        }
        productVersion.setStockQuantity(productVersion.getStockQuantity() - quantity);
        return ProductVersionDto.fromEntity(productVersionRepository.save(productVersion));
    }

    public void deleteOne(Long id) {
        ProductVersion productVersion = productVersionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if (productVersion.getImages() != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<ImageDto>>() {}.getType();
            List<ImageDto> images = gson.fromJson(productVersion.getImages(), listType);

            for (ImageDto image : images) {
                String[] parts = image.getUrl().split("/");
                fileServerClient.deleteFile(parts[parts.length - 1]);
            }
        }
        productVersionRepository.delete(productVersion);
    }
}
