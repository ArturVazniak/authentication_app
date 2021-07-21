package by.innowise.adminservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "hotel")
@Data
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "tour_operator_id", referencedColumnName = "id")
    private TourOperator tourOperator;
}
