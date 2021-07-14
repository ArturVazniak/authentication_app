package by.artur.authentication_app.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;


@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@ToString(doNotUseGetters = true, onlyExplicitlyIncluded = true)
@Table(name = "account_code")
public class AccountCode implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code")
    String code;

    @Column(name = "two_factor_token")
    String twoFactorToken;

    @Column(name = "create_at")
    Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

}