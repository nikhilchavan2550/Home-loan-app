package com.oracle.loanapp.service.impl;

import com.oracle.loanapp.dto.request.PersonalDetailsRequestDto;
import com.oracle.loanapp.dto.response.PersonalDetailsResponseDto;
import com.oracle.loanapp.entity.PersonalDetails;
import com.oracle.loanapp.repository.PersonalDetailsRepository;
import com.oracle.loanapp.service.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalDetailsServiceImpl implements PersonalDetailsService {

    @Autowired
    private PersonalDetailsRepository repository;

    @Override
    public PersonalDetailsResponseDto saveDetails(PersonalDetailsRequestDto dto) {
        PersonalDetails entity = new PersonalDetails();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDob());
        entity.setGender(dto.getGender());
        entity.setAadharNo(dto.getAadharNo());
        entity.setPanNo(dto.getPanNo());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setNationality(dto.getNationality());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());

        PersonalDetails saved = repository.save(entity);
        return toResponseDto(saved);
    }

    @Override
    public List<PersonalDetailsResponseDto> getAllDetails() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    public PersonalDetailsResponseDto getDetailsById(Long id) {
        PersonalDetails entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PersonalDetails not found with ID: " + id));
        return toResponseDto(entity);
    }

    @Override
    public PersonalDetailsResponseDto updateDetails(Long id, PersonalDetailsRequestDto dto) {
        PersonalDetails entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PersonalDetails not found with ID: " + id));

        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setLastName(dto.getLastName());
        entity.setDateOfBirth(dto.getDob());
        entity.setGender(dto.getGender());
        entity.setAadharNo(dto.getAadharNo());
        entity.setPanNo(dto.getPanNo());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setNationality(dto.getNationality());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());

        PersonalDetails updated = repository.save(entity);
        return toResponseDto(updated);
    }

    @Override
    public void deleteDetails(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("PersonalDetails not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    private PersonalDetailsResponseDto toResponseDto(PersonalDetails entity) {
        PersonalDetailsResponseDto dto = new PersonalDetailsResponseDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setMiddleName(entity.getMiddleName());
        dto.setLastName(entity.getLastName());
        dto.setDob(entity.getDateOfBirth());
        dto.setGender(entity.getGender());
        dto.setAadharNo(entity.getAadharNo());
        dto.setPanNo(entity.getPanNo());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setNationality(entity.getNationality());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
