package com.example.explorecali.models;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String blurb;

    private Integer price;

    private String duration;

    @Column(length = 2000)
    private String bullets;

    private String keywords;

    @ManyToOne
    private TourPackage tourPackage;

    @Enumerated
    private Difficulty difficulty;

    private Region region;

}
