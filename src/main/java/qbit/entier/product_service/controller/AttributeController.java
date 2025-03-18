package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import qbit.entier.product_service.dto.AttributeEditDto;
import qbit.entier.product_service.entity.Attribute;
import qbit.entier.product_service.entity.AttributeType;
import qbit.entier.product_service.service.AttributeService;
import qbit.entier.product_service.service.AttributeTypeService;

import java.util.Optional;


@RestController
@RequestMapping("/attributes")
public class AttributeController {
    @Autowired
    private AttributeService attributeService;

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.of(Optional.ofNullable(attributeService.findAll(pageable)));
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<?> findAll(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.of(Optional.ofNullable(attributeService.findByTypeId(id, pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(attributeService.findById(id));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> createOne(@RequestBody AttributeEditDto one) {
        return ResponseEntity.ok(attributeService.createOne(one));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody AttributeEditDto one) {
        try {
            return ResponseEntity.ok(attributeService.updateOne(id, one));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            attributeService.deleteOne(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
