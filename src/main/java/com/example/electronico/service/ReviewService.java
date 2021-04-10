package com.example.electronico.service;

import com.example.electronico.exception.NotFoundException;
import com.example.electronico.model.Review;
import com.example.electronico.model.ReviewWrapper;
import com.example.electronico.repository.ReviewRepository;
import com.example.electronico.service.interfaces.CrudStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements CrudStrategy<Review> {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review add(Review review) {
        return reviewRepository.save(review);
    }

    public ReviewWrapper getReviews(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        double rating = 0.0;
        for(Review review : reviews) {
            rating+=review.getRating();
        }
        rating = Math.round((rating/reviews.size()) * 100.0) / 100.0;
        return new ReviewWrapper(reviews, rating);
    }

    @Override
    public Review get(Long reviewId) throws NotFoundException {
        return reviewRepository.findById(reviewId).orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review update(Review review) throws NotFoundException {
        if(reviewRepository.existsById(review.getReviewId()))
            return reviewRepository.save(review);
        throw new NotFoundException();
    }

    @Override
    public ResponseEntity<String> delete(Long reviewId) throws NotFoundException {
        reviewRepository.deleteById(reviewId);
        return new ResponseEntity<>("Review deleted, id: " + reviewId, HttpStatus.OK);
    }
}
