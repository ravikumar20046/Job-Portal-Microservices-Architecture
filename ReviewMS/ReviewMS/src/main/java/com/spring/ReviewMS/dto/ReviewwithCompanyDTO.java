package com.spring.ReviewMS.dto;

import com.spring.ReviewMS.Model.Review;
import com.spring.ReviewMS.external.Company;

public class ReviewwithCompanyDTO {
        private Review review;
        private Company company;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

