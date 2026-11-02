package com.spring.companyMS.Service;



import com.spring.companyMS.Model.Company;
import com.spring.companyMS.Repo.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }


    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Company not found with ID: " + id));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = getCompanyById(id);

        existingCompany.setName(updatedCompany.getName());
        existingCompany.setDescription(updatedCompany.getDescription());
        existingCompany.setLocation(updatedCompany.getLocation());

        return companyRepository.save(existingCompany);
    }

    @Override
    public Company patchCompany(Long id, Company updatedCompany) {
        Company existingCompany = getCompanyById(id);

        if (updatedCompany.getName() != null) {
            existingCompany.setName(updatedCompany.getName());
        }

        if (updatedCompany.getDescription() != null) {
            existingCompany.setDescription(
                    updatedCompany.getDescription());
        }

        if (updatedCompany.getLocation() != null) {
            existingCompany.setLocation(updatedCompany.getLocation());
        }

        return companyRepository.save(existingCompany);
    }

    @Override
    public void deleteCompanyById(Long id) {
        Company existingCompany = getCompanyById(id);
        companyRepository.delete(existingCompany);
    }
}

