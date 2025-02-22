package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.AttributeTypeDto;
import qbit.entier.product_service.dto.TagDto;
import qbit.entier.product_service.entity.AttributeType;
import qbit.entier.product_service.entity.Tag;
import qbit.entier.product_service.repository.AttributeTypeRepository;

@Service
public class AttributeTypeService {
    @Autowired
    private AttributeTypeRepository attributeTypeRepository;

    public Page<AttributeTypeDto> findAll(Pageable pageable) {
        return attributeTypeRepository.findAll(pageable).map(AttributeTypeDto::fromEntity);
    }

    public AttributeTypeDto findById(Long id) {
        AttributeType type = attributeTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return AttributeTypeDto.fromEntity(type);
    }

    public AttributeTypeDto createType(AttributeType type) {
        AttributeType savedType = attributeTypeRepository.save(type);
        return AttributeTypeDto.fromEntity(savedType);
    }

    public AttributeTypeDto updateType(Long id, AttributeType type) {
        AttributeType updatedType = attributeTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        if(type.getName() != null)
            updatedType.setName(type.getName());
        if(type.getDescription() != null)
            updatedType.setDescription(type.getDescription());
        return AttributeTypeDto.fromEntity(attributeTypeRepository.save(updatedType));
    }

    public void deleteType(Long id) {
        AttributeType deletedType = attributeTypeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        attributeTypeRepository.delete(deletedType);
    }
}
