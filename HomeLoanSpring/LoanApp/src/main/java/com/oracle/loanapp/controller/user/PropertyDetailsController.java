package com.oracle.loanapp.controller.user;

import com.oracle.loanapp.dto.request.PropertyDetailsRequestDto;
import com.oracle.loanapp.dto.response.PropertyDetailsResponseDto;
import com.oracle.loanapp.service.PropertyDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@Tag(name = "Property Details", description = "Endpoints for managing property details")
@CrossOrigin(origins = "*")
public class PropertyDetailsController {

    @Autowired
    private PropertyDetailsService service;

    @Operation(summary = "Add property details")
    @PostMapping("/add")
    public ResponseEntity<PropertyDetailsResponseDto> add(@Valid @RequestBody PropertyDetailsRequestDto dto) {
        return ResponseEntity.ok(service.saveDetails(dto));
    }

    @Operation(summary = "Get all property details")
    @GetMapping("/all")
    public ResponseEntity<List<PropertyDetailsResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAllDetails());
    }

    @Operation(summary = "Get property by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDetailsResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDetailsById(id));
    }

    @Operation(summary = "Update property details")
    @PutMapping("/update/{id}")
    public ResponseEntity<PropertyDetailsResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PropertyDetailsRequestDto dto) {
        return ResponseEntity.ok(service.updateDetails(id, dto));
    }

    @Operation(summary = "Delete property by ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteDetails(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Get property details by Personal ID")
    @GetMapping("/byPersonalId/{personalId}")
    public ResponseEntity<PropertyDetailsResponseDto> getByPersonalId(@PathVariable Long personalId) {
        return ResponseEntity.ok(service.getByPersonalId(personalId));
    }
}
