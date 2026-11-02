package com.spring.companyMS.Service;



import com.spring.companyMS.Model.Company;

import java.util.List;

public interface CompanyService {

    // Create a new company
    Company createCompany(Company company);

    // Get a company by ID
    Company getCompanyById(Long id);

    // Get all companies
    List<Company> getAllCompanies();

    // Update an existing company
    Company updateCompany(Long id, Company company);

    // Partially update a company
    Company patchCompany(Long id, Company company);

    // Delete a company by ID
    void deleteCompanyById(Long id);
}

