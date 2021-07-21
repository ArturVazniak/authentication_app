package by.innowise.adminservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tour_operator")
@Data
public class TourOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;
}
