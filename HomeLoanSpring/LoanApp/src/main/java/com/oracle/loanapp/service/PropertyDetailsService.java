package com.oracle.loanapp.service;

import com.oracle.loanapp.dto.request.PropertyDetailsRequestDto;
import com.oracle.loanapp.dto.response.PropertyDetailsResponseDto;

import java.util.List;

public interface PropertyDetailsService {
    PropertyDetailsResponseDto saveDetails(PropertyDetailsRequestDto dto);
    List<PropertyDetailsResponseDto> getAllDetails();
    PropertyDetailsResponseDto getDetailsById(Long id);
    PropertyDetailsResponseDto updateDetails(Long id, PropertyDetailsRequestDto dto); // Added
    void deleteDetails(Long id); // Added
    PropertyDetailsResponseDto getByPersonalId(Long personalId);
}
