package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.TagDto;
import qbit.entier.product_service.entity.Tag;
import qbit.entier.product_service.repository.TagRepository;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Page<TagDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).map(TagDto::fromEntity);
    }

    public TagDto findById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return TagDto.fromEntity(tag);
    }

    public TagDto createTag(Tag tag) {
        Tag savedTag = tagRepository.save(tag);
        return TagDto.fromEntity(savedTag);
    }

    public TagDto updateTag(Long id, Tag tag) {
        Tag updatedTag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(tag.getName() != null)
            updatedTag.setName(tag.getName());
        if(tag.getDescription() != null)
            updatedTag.setDescription(tag.getDescription());
        return TagDto.fromEntity(tagRepository.save(updatedTag));
    }

    public void deleteTag(Long id) {
        Tag deletedTag = tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        tagRepository.delete(deletedTag);
    }
}
