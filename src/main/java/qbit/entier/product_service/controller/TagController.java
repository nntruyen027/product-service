package qbit.entier.product_service.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import qbit.entier.product_service.entity.Tag;
import qbit.entier.product_service.service.TagService;

import java.util.Optional;


@RestController
@RequestMapping("/tags")
@PreAuthorize("hasRole('admin')")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("")
    public ResponseEntity<?> findAll(Pageable pageable) {
        return ResponseEntity.of(Optional.ofNullable(tagService.findAll(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(tagService.findById(id));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createOne(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.createTag(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody Tag tag) {
        try {
            return ResponseEntity.ok(tagService.updateTag(id, tag));
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            tagService.deleteTag(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
