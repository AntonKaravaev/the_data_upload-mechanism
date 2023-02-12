package authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenProviderService {

    private volatile LocalDateTime tokenExpirationTime;
    private volatile String token;
    private final AuthorizationService authorizationService;
    private final Object lock = new Object();

    @Autowired
    public JwtTokenProviderService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public String get() {
        if (tokenExpirationTime == null || tokenExpirationTime.isBefore(LocalDateTime.now())) {
            synchronized (lock) {
                if (tokenExpirationTime == null || tokenExpirationTime.isBefore(LocalDateTime.now())) {
                    token = authorizationService.authorize();
                    tokenExpirationTime = LocalDateTime.now().plus(15, ChronoUnit.MINUTES);
                }
            }
        }
        return token;
    }

    public void refresh() {
        synchronized (lock) {
            token = authorizationService.authorize();
            tokenExpirationTime = LocalDateTime.now().plus(15, ChronoUnit.MINUTES);
        }
    }
}
