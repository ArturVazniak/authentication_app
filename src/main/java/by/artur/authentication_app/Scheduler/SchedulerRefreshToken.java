package by.artur.authentication_app.Scheduler;

import by.artur.authentication_app.model.RefreshToken;
import by.artur.authentication_app.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class SchedulerRefreshToken {

    @Value("${jwt.token.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public SchedulerRefreshToken(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(fixedRate = 10000 * 60) // 10 min
    public void deleteRefreshToken() {
        List<RefreshToken> refreshTokenList = refreshTokenRepository.findAll();

        if (!refreshTokenList.isEmpty()) {
            refreshTokenList.stream()
                    .filter(r ->
                            (r.getExpiryDate().plusMillis(refreshTokenDurationMs)
                                    .equals(Instant.now()) || r.getExpiryDate().isAfter(Instant.now())))
                    .forEach(refreshToken ->
                            refreshTokenRepository.delete(refreshToken));
        }
    }
}

