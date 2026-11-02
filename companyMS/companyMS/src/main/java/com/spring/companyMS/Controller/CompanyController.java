package com.spring.companyMS.Controller;

import com.spring.companyMS.Model.Company;
import com.spring.companyMS.Service.CompanyService;
import com.spring.companyMS.Service.CompanyServiceImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.spring.companyMS.Service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }



    // POST: Create a new company
    @PostMapping
    public ResponseEntity<Company> createCompany(
            @Valid @RequestBody Company company) {

        Company createdCompany = companyService.createCompany(company);

        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // GET: Get all companies
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompanies();

        return ResponseEntity.ok(companies);
    }

    // GET: Get a company by ID
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(
            @PathVariable Long id) {

        Company company = companyService.getCompanyById(id);

        return ResponseEntity.ok(company);
    }

    // PUT: Update a company
    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody Company company) {

        Company updatedCompany = companyService.updateCompany(id, company);

        return ResponseEntity.ok(updatedCompany);
    }

    // PATCH: Partially update a company
    @PatchMapping("/{id}")
    public ResponseEntity<Company> patchCompany(
            @PathVariable Long id,
            @RequestBody Company company) {

        Company updatedCompany = companyService.patchCompany(id, company);

        return ResponseEntity.ok(updatedCompany);
    }

    // DELETE: Delete a company by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long id) {

        companyService.deleteCompanyById(id);

        return ResponseEntity.noContent().build();
    }
}

