package com.example.electronico.service;

import com.example.electronico.model.Review;
import com.example.electronico.model.ReviewWrapper;
import com.example.electronico.repository.ReviewRepository;
import com.example.electronico.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review review(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public ReviewWrapper getReviews(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        double rating = 0.0;
        for(Review review : reviews) {
            rating+=review.getRating();
        }
        rating = Math.round((rating/reviews.size()) * 100.0) / 100.0;
        return new ReviewWrapper(reviews, rating);
    }
}
