package com.oracle.loanapp.service.impl;

import com.oracle.loanapp.dto.request.PropertyDetailsRequestDto;
import com.oracle.loanapp.dto.response.PropertyDetailsResponseDto;
import com.oracle.loanapp.entity.PersonalDetails;
import com.oracle.loanapp.entity.PropertyDetails;
import com.oracle.loanapp.repository.PersonalDetailsRepository;
import com.oracle.loanapp.repository.PropertyDetailsRepository;
import com.oracle.loanapp.service.PropertyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyDetailsServiceImpl implements PropertyDetailsService {

    @Autowired
    private PropertyDetailsRepository propertyDetailsRepository;

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Override
    public PropertyDetailsResponseDto saveDetails(PropertyDetailsRequestDto dto) {
        PropertyDetails entity = mapToEntity(dto);
        PropertyDetails saved = propertyDetailsRepository.save(entity);
        return toResponseDto(saved);
    }

    @Override
    public List<PropertyDetailsResponseDto> getAllDetails() {
        return propertyDetailsRepository.findAll()
                .stream().map(this::toResponseDto).toList();
    }

    @Override
    public PropertyDetailsResponseDto getDetailsById(Long id) {
        PropertyDetails entity = propertyDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PropertyDetails not found: " + id));
        return toResponseDto(entity);
    }

    @Override
    public PropertyDetailsResponseDto updateDetails(Long id, PropertyDetailsRequestDto dto) {
        PropertyDetails existing = propertyDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PropertyDetails not found: " + id));

        PersonalDetails personal = personalDetailsRepository.findById(dto.getPersonalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid personalId: " + dto.getPersonalId()));

        existing.setPersonalDetails(personal);
        existing.setPropertyName(dto.getPropertyName());
        existing.setPropertyLocation(dto.getPropertyLocation());
        existing.setCity(dto.getCity());
        existing.setState(dto.getState());
        existing.setPropertyArea(dto.getPropertyArea());
        existing.setEstimatedAmount(dto.getEstimatedAmount());
        existing.setConstructionCompletionDate(dto.getConstructionCompletionDate());
        existing.setCreatedDate(dto.getCreatedDate());

        PropertyDetails updated = propertyDetailsRepository.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public void deleteDetails(Long id) {
        if (!propertyDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("PropertyDetails not found: " + id);
        }
        propertyDetailsRepository.deleteById(id);
    }

    private PropertyDetails mapToEntity(PropertyDetailsRequestDto dto) {
        PersonalDetails personal = personalDetailsRepository.findById(dto.getPersonalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid personalId: " + dto.getPersonalId()));
        PropertyDetails entity = new PropertyDetails();
        entity.setPersonalDetails(personal);
        entity.setPropertyName(dto.getPropertyName());
        entity.setPropertyLocation(dto.getPropertyLocation());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setPropertyArea(dto.getPropertyArea());
        entity.setEstimatedAmount(dto.getEstimatedAmount());
        entity.setConstructionCompletionDate(dto.getConstructionCompletionDate());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }

    private PropertyDetailsResponseDto toResponseDto(PropertyDetails entity) {
        PropertyDetailsResponseDto dto = new PropertyDetailsResponseDto();
        dto.setId(entity.getId());
        dto.setPersonalId(entity.getPersonalDetails() != null ? entity.getPersonalDetails().getId() : null);
        dto.setPropertyName(entity.getPropertyName());
        dto.setPropertyLocation(entity.getPropertyLocation());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setPropertyArea(entity.getPropertyArea());
        dto.setEstimatedAmount(entity.getEstimatedAmount() != null ? entity.getEstimatedAmount().doubleValue() : null);
        dto.setConstructionCompletionDate(entity.getConstructionCompletionDate());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    
    @Override
    public PropertyDetailsResponseDto getByPersonalId(Long personalId) {
        PropertyDetails property = propertyDetailsRepository.findByPersonalDetailsId(personalId)
            .orElseThrow(() -> new RuntimeException("Property details not found for personalId: " + personalId));
        
        return toResponseDto(property); // assuming this exists
    }

}
