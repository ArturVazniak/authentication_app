package by.artur.authentication_app.security.twoFA.TwoFactorService;

import by.artur.authentication_app.model.AccountCode;
import by.artur.authentication_app.repository.AccountCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.rowset.spi.SyncFactory;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class SchedulerTwoFactorService {

    private final AccountCodeRepository accountCodeRepository;

    @Autowired
    public SchedulerTwoFactorService(AccountCodeRepository accountCodeRepository) {
        this.accountCodeRepository = accountCodeRepository;
    }

    @Scheduled(fixedRate = 5000)
    public void deleteTwoFactorToken() {
        Long now = new Date().getTime();
        List<AccountCode> accountCodeRepositories = accountCodeRepository.findAll();

        if (accountCodeRepositories != null) {
            accountCodeRepositories.stream()
                    .filter(a -> ((a.getDate().getTime() + TimeUnit.MINUTES.toMillis(5l)) >= now)).forEach(code -> {
                accountCodeRepository.delete(code);
            });
        }
    }
}
