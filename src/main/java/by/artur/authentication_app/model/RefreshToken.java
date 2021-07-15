package by.artur.authentication_app.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@Builder(toBuilder = true)
@Entity(name = "refreshtoken")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "create_at")
    private Date dateAt;

    //todo add fingerprint

    @Column(name = "expiry_date")
    private Instant expiryDate;
}
