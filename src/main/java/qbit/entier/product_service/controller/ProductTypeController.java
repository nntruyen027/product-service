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

    public ResponseEntity<?> createOne(@RequestBody ProductType productType)
            throws IOException {
        return ResponseEntity
                .ok(productTypeService.createOne(productType));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody ProductType productType)
            throws IOException {
        try {
            return ResponseEntity.ok(productTypeService.updateOne(
                    id, productType
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            productTypeService.deleteOne(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
