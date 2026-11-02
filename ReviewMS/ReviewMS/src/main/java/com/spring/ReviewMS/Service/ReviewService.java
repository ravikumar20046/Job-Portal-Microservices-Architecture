package com.spring.ReviewMS.Service;



import com.spring.ReviewMS.Model.Review;
import com.spring.ReviewMS.dto.ReviewwithCompanyDTO;

import java.util.List;

public interface ReviewService {

    Review createReview(Review review);

    Review getReviewById(Long id);

    List<ReviewwithCompanyDTO> getAllReviews();

    List<Review> getReviewsByCompanyId(Long companyId);

    Review updateReview(Long id, Review review);

    Review patchReview(Long id, Review review);

    void deleteReviewById(Long id);
}
