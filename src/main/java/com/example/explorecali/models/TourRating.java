package com.example.explorecali.models;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@Getter @Setter
@Entity
public class TourRating {
    @EmbeddedId
    private TourRatingPk pk;

    private Integer score;

    private String comment;
}
