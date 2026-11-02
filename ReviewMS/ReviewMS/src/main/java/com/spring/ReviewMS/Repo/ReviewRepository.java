package com.spring.ReviewMS.Repo;


import com.spring.ReviewMS.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find all reviews belonging to a company
    List<Review> findByCompanyId(Long companyId);
}

