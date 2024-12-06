package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.product_service.dto.ProductTypeDto;
import qbit.entier.product_service.entity.ProductType;
import qbit.entier.product_service.service.ProductTypeService;

import java.io.IOException;


@RestController
@RequestMapping("/product-types")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(productTypeService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            ProductTypeDto result = productTypeService.findById(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value ="description", required = false) String description,
                                       @RequestParam(value = "icon", required = false) String icon,
                                       @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        return ResponseEntity
                .ok(productTypeService.createOne(ProductType
                        .builder()
                        .icon(icon)
                        .name(name)
                        .description(description)
                        .build(), image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable Long id,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value ="description", required = false) String description,
                                       @RequestParam(value = "icon", required = false) String icon,
                                       @RequestParam(value = "image", required = false) MultipartFile image)
            throws IOException {
        try {
            return ResponseEntity.ok(productTypeService.updateOne(
                    id, ProductType
                            .builder()
                            .icon(icon)
                            .name(name)
                            .description(description)
                            .build(), image
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            productTypeService.deleteOne(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
