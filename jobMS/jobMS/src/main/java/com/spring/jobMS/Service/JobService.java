package com.spring.jobMS.Service;




import com.spring.jobMS.Model.Job;
import com.spring.jobMS.dto.JobDTO;

import java.util.List;

public interface JobService {

    // Create a new job
    Job createJob(Job job);

    // Get a job by ID
    Job getJobById(Long id);

    // Get all jobs
    List<JobDTO> getAllJobs();

    // Update an existing job
    Job updateJob(Long id, Job job);

    // Partially update an existing job
    Job patchJob(Long id, Job job);

    // Delete a job by ID
    void deleteJobById(Long id);
}

