package com.hans.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("ACCOUNT_MS");
    }
}
