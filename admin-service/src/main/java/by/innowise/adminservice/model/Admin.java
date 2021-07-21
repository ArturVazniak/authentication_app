package by.innowise.adminservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder(toBuilder = true)
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String username;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role;


}
