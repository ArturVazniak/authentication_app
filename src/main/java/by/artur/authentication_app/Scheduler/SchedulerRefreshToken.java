package by.artur.authentication_app.Scheduler;

import by.artur.authentication_app.model.RefreshToken;
import by.artur.authentication_app.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SchedulerRefreshToken {

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public SchedulerRefreshToken(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(fixedRate = 10000 * 60) // 10 min
    public void deleteTwoFactorToken() {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findAll();

        if (!refreshTokenList.isEmpty()) {
            refreshTokenList.stream()
                    .filter(a -> (a.getDateAt().getTime() <= new Date().getTime()))
                    .forEach(refreshToken -> {
                        refreshTokenRepository.delete(refreshToken);
                    });
        }
    }
}

