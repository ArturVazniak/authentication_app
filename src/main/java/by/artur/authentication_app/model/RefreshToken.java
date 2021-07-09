package by.artur.authentication_app.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity(name = "refreshtoken")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "token")
    private String token;

    //todo add fingerprint

    @Column(name = "expiry_date")
    private Instant expiryDate;
}
