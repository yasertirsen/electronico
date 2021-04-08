package com.example.electronico.service.interfaces;

import com.example.electronico.model.Review;
import com.example.electronico.model.ReviewWrapper;

public interface ReviewService {

    Review review(Review review);

    ReviewWrapper getReviews(Long productId);
}
