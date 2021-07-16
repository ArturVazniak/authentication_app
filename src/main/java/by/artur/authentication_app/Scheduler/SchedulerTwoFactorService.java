package by.artur.authentication_app.Scheduler;

import by.artur.authentication_app.model.AccountCode;
import by.artur.authentication_app.repository.AccountCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class SchedulerTwoFactorService {

    private final AccountCodeRepository accountCodeRepository;

    @Autowired
    public SchedulerTwoFactorService(AccountCodeRepository accountCodeRepository) {
        this.accountCodeRepository = accountCodeRepository;
    }

    @Scheduled(fixedRate = 1000 * 60) // 1 min
    public void deleteTwoFactorToken() {
        List<AccountCode> accountCodeRepositories = accountCodeRepository.findAll();

        if (!accountCodeRepositories.isEmpty()) {
            accountCodeRepositories.stream()
                    .filter(a -> ((a.getDateAt().getTime() + TimeUnit.MINUTES.toMillis(5)) <= new Date().getTime()))
                    .forEach(code ->
                        accountCodeRepository.delete(code)
                    );
        }
    }
}
