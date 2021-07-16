package by.artur.authentication_app.repository;

import by.artur.authentication_app.model.AccountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCodeRepository extends JpaRepository<AccountCode, Long> {

    AccountCode findByTwoFactorToken (String token);
}
