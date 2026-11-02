package com.spring.jobMS.mapper;

import com.spring.jobMS.Model.Job;
import com.spring.jobMS.dto.JobDTO;
import com.spring.jobMS.external.Company;
import com.spring.jobMS.external.Review;

import java.util.List;

public class JobMapper {
     public static JobDTO mapTOJobwithCompanyDTO(Job job, Company company, List<Review> review){
        JobDTO jobDTO=new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(review);
        return jobDTO;
    }
}
