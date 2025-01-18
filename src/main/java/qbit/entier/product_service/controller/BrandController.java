package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.product_service.entity.Brand;
import qbit.entier.product_service.service.BrandService;

import java.io.IOException;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.ok(brandService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(brandService.getById(id));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestBody Brand brand) throws IOException {
        return ResponseEntity.ok(brandService.createOne(brand));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(
            @PathVariable Long id,
            @RequestBody Brand brand
    ) {
        try {
            return ResponseEntity.ok(brandService.updateOne(id, brand));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        catch (IOException ex) {
            return ResponseEntity.status(500).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            brandService.deleteOne(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
