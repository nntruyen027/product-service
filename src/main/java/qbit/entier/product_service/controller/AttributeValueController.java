package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import qbit.entier.product_service.dto.AttributeValueEditDto;
import qbit.entier.product_service.service.AttributeValueService;

import java.util.Optional;


@RestController
@RequestMapping("/attribute-values")
@PreAuthorize("hasRole('admin')")
public class AttributeValueController {
    @Autowired
    private AttributeValueService attributeValueService;

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.of(Optional.ofNullable(attributeValueService.findAll(pageable)));
    }

    @GetMapping("/attribute/{id}")
    public ResponseEntity<?> findAll(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.of(Optional.ofNullable(attributeValueService.findByAttributeId(id, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(attributeValueService.findById(id));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestBody AttributeValueEditDto one) {
        return ResponseEntity.ok(attributeValueService.createOne(one));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody AttributeValueEditDto one) {
        try {
            return ResponseEntity.ok(attributeValueService.updateOne(id, one));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            attributeValueService.deleteOne(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
