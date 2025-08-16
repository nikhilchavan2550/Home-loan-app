package com.oracle.loanapp.service;

import com.oracle.loanapp.dto.request.PersonalDetailsRequestDto;
import com.oracle.loanapp.dto.response.PersonalDetailsResponseDto;

import java.util.List;

public interface PersonalDetailsService {

    PersonalDetailsResponseDto saveDetails(PersonalDetailsRequestDto dto);

    List<PersonalDetailsResponseDto> getAllDetails();

    PersonalDetailsResponseDto getDetailsById(Long id);

    // ✅ These two must match the implementation
    PersonalDetailsResponseDto updateDetails(Long id, PersonalDetailsRequestDto dto);

    void deleteDetails(Long id);
}
