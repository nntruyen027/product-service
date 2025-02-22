package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.product_service.client.FileServerClient;
import qbit.entier.product_service.dto.BrandDto;
import qbit.entier.product_service.entity.Brand;
import qbit.entier.product_service.repository.BrandRepository;
import qbit.entier.product_service.util.FileUtil;

import java.io.IOException;


@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private FileServerClient fileServerClient;

    public Page<BrandDto> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable).map(i -> BrandDto
                .fromEntity(i, productService.getByBrand(i.getId())));
    }

    public BrandDto getById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Not found"));
        return BrandDto.fromEntity(brand, productService.getByBrand(brand.getId()));
    }

    public BrandDto createOne(Brand brand) throws IOException {
        Brand createdBrand = brandRepository.save(brand);
        return BrandDto.fromEntity(createdBrand);
    }

    public BrandDto updateOne(Long id, Brand brand) throws IOException {
        Brand updatedBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(brand.getName() != null)
            updatedBrand.setName(brand.getName());
        if(brand.getDescription() != null)
            updatedBrand.setDescription(brand.getDescription());
        if(brand.getImage() != null)
            updatedBrand.setImage(brand.getImage());
        return BrandDto.fromEntity(brandRepository.save(updatedBrand));
    }

    public void deleteOne(Long id) {
        Brand deletedBrand = brandRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(deletedBrand.getImage() != null) {
            String[] paths = deletedBrand.getImage().split("/");
            fileServerClient.deleteFile(paths[paths.length - 1]);
        }
        brandRepository.delete(deletedBrand);
    }
}
