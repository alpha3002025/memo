생각해보면 지금까지 Spring Batch 내에서는 JdbcTemplate 을 썼던 걸로 기억하는데
나 왜 `@Modifying`으로 한큐에 처리하려고 했을까 싶기도 했다.

이번에는 다음과 같이 처리했다. 
- 처리할 데이터를 읽어온다.
- 삭제한다.(deleteAll)

# Service
```java
@Slf4j
@RequiredArgsConstructor
@Service
public class TokenCleanupService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    /**
     * 만료된 토큰들을 정리
     * - 만료된 Refresh Token 삭제
     * - 만료된 Blacklist 항목 삭제
     */
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        log.info("🧹 Starting token cleanup at {}", now);

        var expiredRefreshTokens = refreshTokenRepository.findExpiredTokens(now);
        refreshTokenRepository.deleteAll(expiredRefreshTokens);
        log.info("Deleted {} expired refresh tokens", expiredRefreshTokens.size());

        var expiredBlacklistTokens = tokenBlacklistRepository.findExpiredTokens(now);
        tokenBlacklistRepository.deleteAll(expiredBlacklistTokens);
        log.info("Deleted {} expired blacklist tokens", expiredBlacklistTokens.size());

        log.info("✅ Token cleanup completed. Total deleted: {} tokens",
                expiredRefreshTokens.size() + expiredBlacklistTokens.size());
    }
}
```
<br/>


# Repository
TokenBlacklistRepository.java
```java
package click.dailyfeed.batch.domain.member.token.repository.jpa;

import click.dailyfeed.batch.domain.member.token.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    @Query("SELECT tb FROM TokenBlacklist tb WHERE tb.expiresAt < :now")
    List<TokenBlacklist> findExpiredTokens(@Param("now") LocalDateTime now);
}
```

RefreshTokenRepository.java
```java
package click.dailyfeed.batch.domain.member.token.repository.jpa;

import click.dailyfeed.batch.domain.member.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.expiresAt < :now")
    List<RefreshToken> findExpiredTokens(@Param("now") LocalDateTime now);
}
```
