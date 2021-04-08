package com.example.electronico.controller;

import com.example.electronico.exception.ElectronicoExceptionHandler;
import com.example.electronico.model.Review;
import com.example.electronico.model.ReviewWrapper;
import com.example.electronico.service.interfaces.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController extends ElectronicoExceptionHandler {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public Review review(@RequestBody Review review) {
        return reviewService.review(review);
    }

    @GetMapping("/all/{productId}")
    public ReviewWrapper getReviews(@PathVariable Long productId) {
        return reviewService.getReviews(productId);
    }
}
