package by.artur.authentication_app.repository;

import by.artur.authentication_app.model.AccountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountCodeRepository extends JpaRepository<AccountCode, Long> {

    void deleteByCode(String secretKey);

    @Transactional
    void deleteByTwoFactorToken(String twoFactorToken);

    AccountCode findByCode(String code);
}
