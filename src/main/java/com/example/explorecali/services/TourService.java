package com.example.explorecali.services;

import com.example.explorecali.models.Difficulty;
import com.example.explorecali.models.Region;
import com.example.explorecali.models.Tour;
import com.example.explorecali.models.TourPackage;
import com.example.explorecali.repositories.TourPackageRepository;
import com.example.explorecali.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String description, String blurb, Integer price,
                            String duration, String bullets,
                            String keywords, String tourPackageName, Difficulty difficulty, Region region ) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist:" + tourPackageName));

        return tourRepository.save(
                Tour.builder()
                        .title(title)
                        .description(description)
                        .blurb(blurb)
                        .price(price)
                        .duration(duration)
                        .bullets(bullets)
                        .keywords(keywords)
                        .tourPackage(tourPackage).build()
        );
    }
}
