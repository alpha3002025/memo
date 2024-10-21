package io.chagchagchag.example.gateway.filter;

import io.chagchagchag.example.gateway.resolver.IpUserIdKeyResolver;
import io.chagchagchag.example.gateway.resolver.UserIdKeyResolver;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.support.HasRouteId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomRequestRateLimiterFilter extends AbstractGatewayFilterFactory<CustomRequestRateLimiterFilter.Config> {
    private final RateLimiter<RedisRateLimiter.Config> rateLimiter;
    private final IpUserIdKeyResolver ipUserIdKeyResolver;
    private final UserIdKeyResolver userIdKeyResolver;

    public CustomRequestRateLimiterFilter(
            RateLimiter<RedisRateLimiter.Config> rateLimiter,
            IpUserIdKeyResolver ipUserIdKeyResolver,
            UserIdKeyResolver userIdKeyResolver
    ) {
        super(Config.class);
        this.rateLimiter = rateLimiter;
        this.ipUserIdKeyResolver = ipUserIdKeyResolver;
        this.userIdKeyResolver = userIdKeyResolver;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var response = exchange.getResponse();
            KeyResolver keyResolver = config.keyResolver;
            String routeId = config.routeId;

            return keyResolver.resolve(exchange)
                    .flatMap(key -> this.rateLimiter.isAllowed(routeId, key))
                    .flatMap(rateLimitResponse -> {
                        log.info("Rate limit response: {}", rateLimitResponse.isAllowed());
                        boolean allowed = rateLimitResponse.isAllowed();
                        if(allowed) return chain.filter(exchange);
                        else return responseTooManyRequest(response);
                    });
        };
    }

    public Mono<Void> responseTooManyRequest(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return response.writeWith(
                Mono.just(
                        response.bufferFactory().wrap(
                                """
                                |"success": false,
                                |"message": "Too Many Requests"
                                """.trim().getBytes(StandardCharsets.UTF_8)
                        )
                )
        );
    }

    @Getter
    @Setter
    static class Config implements HasRouteId {
        private KeyResolver keyResolver;
        private String routeId;

        public Config(){}

        public Config(KeyResolver keyResolver){
            this.keyResolver = keyResolver;
        }

        public Config(
                KeyResolver keyResolver,
                String routeId
        ){
            this.keyResolver = keyResolver;
            this.routeId = routeId;
        }

        @Override
        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        @Override
        public String getRouteId() {
            return this.routeId;
        }
    }
}
