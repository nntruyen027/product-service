package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import qbit.entier.product_service.dto.AssignTagDto;
import qbit.entier.product_service.dto.ProductAttributeEditDto;
import qbit.entier.product_service.dto.ProductDto;
import qbit.entier.product_service.dto.ProductEditDto;
import qbit.entier.product_service.service.ProductService;
import qbit.entier.product_service.service.ProductTagService;

import java.io.IOException;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTagService productTagService;

    @GetMapping("")
    public ResponseEntity<?> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            ProductDto result = productService.findById(id);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestBody ProductEditDto product)
            throws IOException {
        return ResponseEntity
                .ok(productService.createOne(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody ProductEditDto product)
            throws IOException {
        try {
            return ResponseEntity.ok(productService.updateOne(
                    id, product
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/{id}/tags")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> assignTags(@PathVariable Long id, @RequestBody AssignTagDto tags) {
        try {
            return ResponseEntity.ok(productTagService.assignTagToProduct(
                    id, tags.getTags()
            ));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/tags")
    public ResponseEntity<?> getTags(@PathVariable Long id) {
        return ResponseEntity.ok(productTagService.findByProductId(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            productService.deleteOne(id);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/attributes")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> assignAttribute(@PathVariable Long id, @RequestBody ProductAttributeEditDto ids) {
        try {
            return ResponseEntity.ok(productService.updateAttributeValues(id, ids.getIds()));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
