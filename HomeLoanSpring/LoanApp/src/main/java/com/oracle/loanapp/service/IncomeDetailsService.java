package com.oracle.loanapp.service;

import com.oracle.loanapp.dto.request.IncomeDetailsRequestDto;
import com.oracle.loanapp.dto.response.IncomeDetailsResponseDto;
import java.util.List;

public interface IncomeDetailsService {
    IncomeDetailsResponseDto addIncomeDetails(IncomeDetailsRequestDto requestDto);
    List<IncomeDetailsResponseDto> getAllIncomeDetails();
    IncomeDetailsResponseDto getIncomeDetailsById(Long id);
    IncomeDetailsResponseDto updateIncomeDetails(Long id, IncomeDetailsRequestDto requestDto);
    IncomeDetailsResponseDto getIncomeDetailsByPersonalId(Long personalId);

    void deleteIncomeDetails(Long id);
}
