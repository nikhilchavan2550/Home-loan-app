package com.oracle.loanapp.service.impl;

import com.oracle.loanapp.dto.request.IncomeDetailsRequestDto;
import com.oracle.loanapp.dto.response.IncomeDetailsResponseDto;
import com.oracle.loanapp.entity.IncomeDetails;
import com.oracle.loanapp.entity.PersonalDetails;
import com.oracle.loanapp.repository.IncomeDetailsRepository;
import com.oracle.loanapp.repository.PersonalDetailsRepository;
import com.oracle.loanapp.service.IncomeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.stream.Collectors;

@Service
@Transactional
public class IncomeDetailsServiceImpl implements IncomeDetailsService {
    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private PersonalDetailsRepository personalDetailsRepository;

    @Override
    public IncomeDetailsResponseDto addIncomeDetails(IncomeDetailsRequestDto requestDto) {
        PersonalDetails personalDetails = personalDetailsRepository.findById(requestDto.getPersonalId())
                .orElseThrow(() -> new IllegalArgumentException("PersonalDetails not found for id: " + requestDto.getPersonalId()));
        IncomeDetails incomeDetails = mapToEntity(requestDto, personalDetails);
        IncomeDetails saved = incomeDetailsRepository.save(incomeDetails);
        return mapToResponseDto(saved);
    }

    @Override
    public List<IncomeDetailsResponseDto> getAllIncomeDetails() {
        return incomeDetailsRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public IncomeDetailsResponseDto getIncomeDetailsByPersonalId(Long personalId) {
        IncomeDetails income = incomeDetailsRepository.findByPersonalDetailsId(personalId)
            .orElseThrow(() -> new RuntimeException("Income details not found for personalId: " + personalId));

        return mapToResponseDto(income); // assuming you have a method for this
    }


    @Override
    public IncomeDetailsResponseDto getIncomeDetailsById(Long id) {
        IncomeDetails incomeDetails = incomeDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("IncomeDetails not found for id: " + id));
        return mapToResponseDto(incomeDetails);
    }

    @Override
    public IncomeDetailsResponseDto updateIncomeDetails(Long id, IncomeDetailsRequestDto requestDto) {
        IncomeDetails incomeDetails = incomeDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("IncomeDetails not found for id: " + id));
        PersonalDetails personalDetails = personalDetailsRepository.findById(requestDto.getPersonalId())
                .orElseThrow(() -> new IllegalArgumentException("PersonalDetails not found for id: " + requestDto.getPersonalId()));
        updateEntityFromDto(incomeDetails, requestDto, personalDetails);
        IncomeDetails updated = incomeDetailsRepository.save(incomeDetails);
        return mapToResponseDto(updated);
    }

    @Override
    public void deleteIncomeDetails(Long id) {
        if (!incomeDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("IncomeDetails not found for id: " + id);
        }
        incomeDetailsRepository.deleteById(id);
    }

    private IncomeDetails mapToEntity(IncomeDetailsRequestDto dto, PersonalDetails personalDetails) {
        IncomeDetails entity = new IncomeDetails();
        entity.setPersonalDetails(personalDetails);
        entity.setEmploymentType(dto.getEmploymentType());
        entity.setRetirementAge(dto.getRetirementAge());
        entity.setOrganizationType(dto.getOrganizationType());
        entity.setEmployerName(dto.getEmployerName());
        entity.setMonthlyIncome(dto.getMonthlyIncome());
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }

    private void updateEntityFromDto(IncomeDetails entity, IncomeDetailsRequestDto dto, PersonalDetails personalDetails) {
        entity.setPersonalDetails(personalDetails);
        entity.setEmploymentType(dto.getEmploymentType());
        entity.setRetirementAge(dto.getRetirementAge());
        entity.setOrganizationType(dto.getOrganizationType());
        entity.setEmployerName(dto.getEmployerName());
        entity.setMonthlyIncome(dto.getMonthlyIncome());
        entity.setCreatedDate(dto.getCreatedDate());
    }

    private IncomeDetailsResponseDto mapToResponseDto(IncomeDetails entity) {
        IncomeDetailsResponseDto dto = new IncomeDetailsResponseDto();
        dto.setId(entity.getId());
        dto.setPersonalId(entity.getPersonalDetails() != null ? entity.getPersonalDetails().getId() : null);
        dto.setEmploymentType(entity.getEmploymentType());
        dto.setRetirementAge(entity.getRetirementAge());
        dto.setOrganizationType(entity.getOrganizationType());
        dto.setEmployerName(entity.getEmployerName());
        dto.setMonthlyIncome(entity.getMonthlyIncome());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
