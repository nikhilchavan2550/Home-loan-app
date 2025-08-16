package com.oracle.loanapp.controller.user;

import com.oracle.loanapp.dto.request.PersonalDetailsRequestDto;
import com.oracle.loanapp.dto.response.PersonalDetailsResponseDto;
import com.oracle.loanapp.dto.response.PropertyDetailsResponseDto;
import com.oracle.loanapp.service.PersonalDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/personal")
@Tag(name = "Personal Details", description = "Endpoints for managing personal details")
@CrossOrigin(origins = "*")
public class PersonalDetailsController {
    @Autowired
    private PersonalDetailsService personalDetailsService;

    @Operation(summary = "Add new personal details")
    @PostMapping("/add")
    public ResponseEntity<PersonalDetailsResponseDto> addPersonal(@Valid @RequestBody PersonalDetailsRequestDto dto) {
        return ResponseEntity.ok(personalDetailsService.saveDetails(dto));
    }

    @Operation(summary = "Get all personal details")
    @GetMapping("/all")
    public ResponseEntity<List<PersonalDetailsResponseDto>> getAll() {
        return ResponseEntity.ok(personalDetailsService.getAllDetails());
    }

    @Operation(summary = "Get personal details by ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<PersonalDetailsResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personalDetailsService.getDetailsById(id));
    }
    @Operation(summary = "Update personal details by ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<PersonalDetailsResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PersonalDetailsRequestDto dto) {
        return ResponseEntity.ok(personalDetailsService.updateDetails(id, dto));
    }

    @Operation(summary = "Delete personal details by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personalDetailsService.deleteDetails(id);
        return ResponseEntity.noContent().build();
    }
    
    
    


}
