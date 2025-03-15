package qbit.entier.product_service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import qbit.entier.product_service.dto.AttributeValueDto;
import qbit.entier.product_service.dto.AttributeValueEditDto;
import qbit.entier.product_service.entity.Attribute;
import qbit.entier.product_service.entity.AttributeValue;
import qbit.entier.product_service.repository.AttributeRepository;
import qbit.entier.product_service.repository.AttributeValueRepository;

@Service
public class AttributeValueService {
    @Autowired
    private AttributeValueRepository attributeValueRepository;
    @Autowired
    private AttributeRepository attributeRepository;

    public Page<AttributeValueDto> findAll(Pageable pageable) {
        return attributeValueRepository.findAll(pageable).map(AttributeValueDto::fromEntity);
    }

    public Page<AttributeValueDto> findByAttributeId(Long id, Pageable pageable) {
        return attributeValueRepository.findByAttributeId(id, pageable).map(AttributeValueDto::fromEntity);
    }

    public AttributeValueDto findById(Long id) {
        AttributeValue one = attributeValueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return AttributeValueDto.fromEntity(one);
    }

    public AttributeValueDto createOne(AttributeValueEditDto one) {
        Attribute attribute = attributeRepository.findById(one.getAttribute()).orElseThrow(() -> new EntityNotFoundException("Not found"));
        AttributeValue createdOne = AttributeValue.builder()
                .name(one.getName())
                .description(one.getDescription())
                .attribute(attribute)
                .build();

        return AttributeValueDto.fromEntity(attributeValueRepository.save(createdOne));
    }

    public AttributeValueDto updateOne(Long id, AttributeValueEditDto one) {
        AttributeValue updatedOne = attributeValueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));

        if(one.getName() != null)
            updatedOne.setName(one.getName());
        if(one.getDescription() != null)
            updatedOne.setDescription(one.getDescription());
        if(one.getAttribute() != null) {
            Attribute attribute = attributeRepository.findById(one.getAttribute()).orElseThrow(() -> new EntityNotFoundException("Not found"));
            updatedOne.setAttribute(attribute);
        }

        return AttributeValueDto.fromEntity(attributeValueRepository.save(updatedOne));
    }

    public void deleteOne(Long id) {
        AttributeValue deletedType = attributeValueRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        attributeValueRepository.delete(deletedType);
    }
}
