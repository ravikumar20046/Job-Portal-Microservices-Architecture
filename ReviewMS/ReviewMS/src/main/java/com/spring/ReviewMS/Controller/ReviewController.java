package com.spring.ReviewMS.Controller;


import com.spring.ReviewMS.Model.Review;
import com.spring.ReviewMS.Service.ReviewService;
import com.spring.ReviewMS.dto.ReviewwithCompanyDTO;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // POST: Create a review
    @PostMapping
    public ResponseEntity<Review> createReview(
            @Valid @RequestBody Review review) {

        Review createdReview = reviewService.createReview(review);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // GET: Get all reviews
    @GetMapping
    public ResponseEntity<List<ReviewwithCompanyDTO>> getAllReviews() {
        List<ReviewwithCompanyDTO> reviews = reviewService.getAllReviews();

        return ResponseEntity.ok(reviews);
    }

    // GET: Get a review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(
            @PathVariable Long id) {

        Review review = reviewService.getReviewById(id);

        return ResponseEntity.ok(review);
    }

    // GET: Get reviews for a company
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Review>> getReviewsByCompanyId(
            @PathVariable Long companyId) {

        List<Review> reviews =
                reviewService.getReviewsByCompanyId(companyId);

        return ResponseEntity.ok(reviews);
    }

    // PUT: Update a review
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody Review review) {

        Review updatedReview = reviewService.updateReview(id, review);

        return ResponseEntity.ok(updatedReview);
    }

    // PATCH: Partially update a review
    @PatchMapping("/{id}")
    public ResponseEntity<Review> patchReview(
            @PathVariable Long id,
            @RequestBody Review review) {

        Review updatedReview = reviewService.patchReview(id, review);

        return ResponseEntity.ok(updatedReview);
    }

    // DELETE: Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long id) {

        reviewService.deleteReviewById(id);

        return ResponseEntity.noContent().build();
    }
}
