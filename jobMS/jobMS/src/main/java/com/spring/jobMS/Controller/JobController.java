package com.spring.jobMS.Controller;





import com.spring.jobMS.Model.Job;
import com.spring.jobMS.Service.JobService;
import com.spring.jobMS.dto.JobDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;



    // POST: Create a new job
    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) {
        Job createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // GET: Get all jobs
    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    // GET: Get a job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    // PUT: Update all editable job fields
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @Valid @RequestBody Job job) {

        Job updatedJob = jobService.updateJob(id, job);
        return ResponseEntity.ok(updatedJob);
    }

    // PATCH: Update only the provided job fields
    @PatchMapping("/{id}")
    public ResponseEntity<Job> patchJob(
            @PathVariable Long id,
            @RequestBody Job job) {

        Job updatedJob = jobService.patchJob(id, job);
        return ResponseEntity.ok(updatedJob);
    }

    // DELETE: Delete a job by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobById(@PathVariable Long id) {
        jobService.deleteJobById(id);
        return ResponseEntity.noContent().build();
    }
}

