package com.spring.ReviewMS.Service;

import com.spring.ReviewMS.Model.Review;
import com.spring.ReviewMS.Repo.ReviewRepository;
import com.spring.ReviewMS.dto.ReviewwithCompanyDTO;
import com.spring.ReviewMS.external.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public ReviewServiceImpl(ReviewRepository reviewRepository,CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.reviewRepository = reviewRepository;
        this.circuitBreakerFactory=circuitBreakerFactory;
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Review not found with ID: " + id));
    }

    @Override
    public List<ReviewwithCompanyDTO> getAllReviews() {
        List<Review> reviews=reviewRepository.findAll();
        List<ReviewwithCompanyDTO>  reviewwithComapnyDTO=new ArrayList<>();
        return reviews.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Autowired
    RestTemplate restTemplate;
    private ReviewwithCompanyDTO convertToDto(Review review){
        ReviewwithCompanyDTO dto=new ReviewwithCompanyDTO();
        dto.setReview(review);
//        Company company=restTemplate.getForObject("http://company-service:8082/api/companies/" + review.getCompanyId(),Company.class);
//        dto.setCompany(company);
        Company company = circuitBreakerFactory
                .create("companyService")
                .run(
                        () -> restTemplate.getForObject(
                                "http://COMPANY-SERVICE/api/companies/"
                                        + review.getCompanyId(),
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

        dto.setCompany(company);
        return dto;
    }

    @Override
    public List<Review> getReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public Review updateReview(Long id, Review updatedReview) {
        Review existingReview = getReviewById(id);

        existingReview.setTitle(updatedReview.getTitle());
        existingReview.setDescription(updatedReview.getDescription());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setCompanyId(updatedReview.getCompanyId());

        return reviewRepository.save(existingReview);
    }

    @Override
    public Review patchReview(Long id, Review updatedReview) {
        Review existingReview = getReviewById(id);

        if (updatedReview.getTitle() != null) {
            existingReview.setTitle(updatedReview.getTitle());
        }

        if (updatedReview.getDescription() != null) {
            existingReview.setDescription(updatedReview.getDescription());
        }

        if (updatedReview.getRating() != null) {
            existingReview.setRating(updatedReview.getRating());
        }

        if (updatedReview.getCompanyId() != null) {
            existingReview.setCompanyId(updatedReview.getCompanyId());
        }

        return reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReviewById(Long id) {
        Review existingReview = getReviewById(id);
        reviewRepository.delete(existingReview);
    }
}
