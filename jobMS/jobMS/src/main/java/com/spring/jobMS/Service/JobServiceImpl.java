package com.spring.jobMS.Service;


import com.spring.jobMS.Model.Job;
import com.spring.jobMS.Repo.JobRepository;
import com.spring.jobMS.dto.JobDTO;
import com.spring.jobMS.external.Company;
import com.spring.jobMS.external.Review;
import com.spring.jobMS.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public JobServiceImpl(JobRepository jobRepository,CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.jobRepository = jobRepository;
        this.circuitBreakerFactory=circuitBreakerFactory;
    }

    @Override
    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found with ID: " + id));
    }

    @Override
    public List<JobDTO> getAllJobs() {

        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobWithCompanyDtos = new ArrayList<>();
        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Autowired
    RestTemplate restTemplate;
//    private JobDTO convertToDto(Job job){
//        Company company = restTemplate.getForObject(
//                    "http://company-service:8082/api/companies/" + job.getCompanyId(),
//                    Company.class
//            );
//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://review-service:8083/api/reviews/company/" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                }
//        );
//        List<Review> review=reviewResponse.getBody();
//        JobDTO jobDTO = JobMapper.mapTOJobwithCompanyDTO(job, company, review != null ? review : new ArrayList<>());
//
//        return jobDTO;
//    }
private JobDTO convertToDto(Job job) {

    // Call Company Service using Circuit Breaker
    Company company = circuitBreakerFactory
            .create("companyService")
            .run(
                    () -> restTemplate.getForObject(
                            "http://COMPANY-SERVICE/api/companies/"
                                    + job.getCompanyId(),
                            Company.class
                    ),
                    exception -> {
                        System.out.println(
                                "Company Service is unavailable. "
                                        + "Executing fallback."
                        );
                        return null;
                    }
            );

    // Call Review Service using Circuit Breaker
    List<Review> reviews = circuitBreakerFactory
            .create("reviewService")
            .run(
                    () -> {
                        ResponseEntity<List<Review>> response =
                                restTemplate.exchange(
                                        "http://REVIEW-SERVICE/api/reviews/company/"
                                                + job.getCompanyId(),
                                        HttpMethod.GET,
                                        null,
                                        new ParameterizedTypeReference<List<Review>>() {}
                                );

                        return response.getBody() != null
                                ? response.getBody()
                                : new ArrayList<>();
                    },
                    exception -> {
                        System.out.println(
                                "Review Service is unavailable. "
                                        + "Executing fallback."
                        );
                        return new ArrayList<>();
                    }
            );

    // Convert into DTO
    return JobMapper.mapTOJobwithCompanyDTO(
            job,
            company,
            reviews
    );
}

    @Override
    public Job updateJob(Long id, Job updatedJob) {
        Job existingJob = getJobById(id);

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setMinSalary(updatedJob.getMinSalary());
        existingJob.setMaxSalary(updatedJob.getMaxSalary());
        existingJob.setLocation(updatedJob.getLocation());
        existingJob.setCompanyId(updatedJob.getCompanyId());

        return jobRepository.save(existingJob);
    }

    @Override
    public Job patchJob(Long id, Job updatedJob) {
        Job existingJob = getJobById(id);

        if (updatedJob.getTitle() != null) {
            existingJob.setTitle(updatedJob.getTitle());
        }

        if (updatedJob.getDescription() != null) {
            existingJob.setDescription(updatedJob.getDescription());
        }

        if (updatedJob.getMinSalary() != null) {
            existingJob.setMinSalary(updatedJob.getMinSalary());
        }

        if (updatedJob.getMaxSalary() != null) {
            existingJob.setMaxSalary(updatedJob.getMaxSalary());
        }

        if (updatedJob.getLocation() != null) {
            existingJob.setLocation(updatedJob.getLocation());
        }

        if (updatedJob.getCompanyId() != null) {
            existingJob.setCompanyId(updatedJob.getCompanyId());
        }

        return jobRepository.save(existingJob);
    }

    @Override
    public void deleteJobById(Long id) {
        Job existingJob = getJobById(id);
        jobRepository.delete(existingJob);
    }
}

