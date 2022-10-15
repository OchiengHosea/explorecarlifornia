package com.example.explorecali.web;

import com.example.explorecali.models.Tour;
import com.example.explorecali.models.TourRating;
import com.example.explorecali.models.TourRatingPk;
import com.example.explorecali.repositories.TourRatingRepository;
import com.example.explorecali.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class TourRatingController {
    @Autowired
    private TourRatingRepository tourRatingRepository;
    @Autowired
    private TourRepository tourRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto){
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(
                TourRating.builder()
                        .pk(new TourRatingPk(tour, ratingDto.getCustomerId()))
                        .score(ratingDto.getScore())
                        .comment(ratingDto.getComment())
                        .build()
        );
    }

    @GetMapping
    public List<RatingDto> getAlRatingsForTour(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return tourRatingRepository.findByPkTourId(tourId).stream()
                .map(tourRating -> RatingDto.builder()
                        .comment(tourRating.getComment())
                        .customerId(tourRating.getPk().getCustomerId())
                        .score(tourRating.getScore())
                        .build()).collect(Collectors.toList());
    }


    @GetMapping("/average")
    public Map<String, Double> getAverage(@PathVariable(value = "toutId") int tourId) {
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() -> new NoSuchElementException("Tour does not exist "+ tourId))
        );
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourIr") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(rating.getScore());
        rating.setComment(rating.getComment());
        TourRating tourRating = tourRatingRepository.save(rating);
        return RatingDto.builder()
                .score(tourRating.getScore())
                .comment(tourRating.getComment())
                .build();
    }
    private Tour verifyTour(int tourId) {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist "+ tourId));
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour rating pair for request (" + tourId + " for customer " + customerId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    private String return400(NoSuchElementException ex){
        return ex.getMessage();
    }
}
