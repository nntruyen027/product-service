package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.AttributeDto;
import qbit.entier.product_service.dto.AttributeEditDto;
import qbit.entier.product_service.dto.AttributeTypeDto;
import qbit.entier.product_service.entity.Attribute;
import qbit.entier.product_service.entity.AttributeType;
import qbit.entier.product_service.repository.AttributeRepository;
import qbit.entier.product_service.repository.AttributeTypeRepository;

@Service
public class AttributeService {
    @Autowired
    private AttributeRepository attributeRepository;
    @Autowired
    private AttributeTypeRepository attributeTypeRepository;

    public Page<AttributeDto> findAll(Pageable pageable) {
        return attributeRepository.findAll(pageable).map(AttributeDto::fromEntity);
    }

    public Page<AttributeDto> findByTypeId(Long id, Pageable pageable) {
        return attributeRepository.findByTypeId(id, pageable).map(AttributeDto::fromEntity);
    }

    public AttributeDto findById(Long id) {
        Attribute one = attributeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return AttributeDto.fromEntity(one);
    }

    public AttributeDto createOne(AttributeEditDto one) {
        AttributeType type = attributeTypeRepository.findById(one.getType()).orElseThrow(() -> new EntityNotFoundException("Not found"));
        Attribute createdOne = Attribute.builder()
                .name(one.getName())
                .description(one.getDescription())
                .type(type)
                .build();

        return AttributeDto.fromEntity(attributeRepository.save(createdOne));
    }

    public AttributeDto updateOne(Long id, AttributeEditDto one) {
        Attribute updatedOne = attributeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));

        if(one.getName() != null)
            updatedOne.setName(one.getName());
        if(one.getDescription() != null)
            updatedOne.setDescription(one.getDescription());
        if(one.getType() != null) {
            AttributeType type = attributeTypeRepository.findById(one.getType()).orElseThrow(() -> new EntityNotFoundException("Not found"));
            updatedOne.setType(type);
        }

        return AttributeDto.fromEntity(attributeRepository.save(updatedOne));
    }

    public void deleteOne(Long id) {
        Attribute deletedType = attributeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        attributeRepository.delete(deletedType);
    }
}
