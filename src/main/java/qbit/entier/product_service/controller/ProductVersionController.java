package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.ProductEditDto;
import qbit.entier.product_service.dto.ProductVersionDto;
import qbit.entier.product_service.dto.ProductVersionEditDto;
import qbit.entier.product_service.service.ProductService;
import qbit.entier.product_service.service.ProductVersionService;

import java.io.IOException;


@RestController
@RequestMapping("products/{productId}/versions")
public class ProductVersionController {
    @Autowired
    private ProductVersionService productVersionService;

    @GetMapping("")
    public ResponseEntity<?> getAllByProduct(@PathVariable Long productId, Pageable pageable) {
        return ResponseEntity.ok(productVersionService.getByProductId(productId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            ProductVersionDto result = productVersionService.findById(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestBody ProductVersionEditDto product)
            throws IOException {
        return ResponseEntity
                .ok(productVersionService.createOne(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody ProductVersionEditDto product)
            throws IOException {
        try {
            return ResponseEntity.ok(productVersionService.updateOne(
                    id, product
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/add/{quantity}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> addItems(@PathVariable Long id, @PathVariable(required = false) Integer quantity)
            throws IOException {
        try {
            quantity = quantity != null ? quantity : 0;
            return ResponseEntity.ok(productVersionService.addItem(
                    id, quantity
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}/remove/{quantity}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> removeItems(@PathVariable Long id, @PathVariable(required = false) Integer quantity)
            throws IOException {
        try {
            quantity = quantity != null ? quantity : 0;
            return ResponseEntity.ok(productVersionService.subtractItem(
                    id, quantity
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            productVersionService.deleteOne(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
