package com.blog.modules.user.infrastructure.adapter.out.persistence.subscription;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.blog.modules.user.domain.port.out.SubscriptionRepository;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SpringDataSubscriptionRepository jpaRepository;

    public SubscriptionRepositoryImpl(SpringDataSubscriptionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean isSubscribed(UUID currUserId, UUID targetUserId) {
        return jpaRepository.isSubscribed(currUserId, targetUserId);
    }

    @Override
    public void subscribe(UUID currUserId, UUID targetUserId) {
        jpaRepository.executeInsertQuery(currUserId, targetUserId);
    }

    @Override
    public void unsubscribe(UUID currUserId, UUID targetUserId) {
        jpaRepository.deleteById(new SubscriptionEntity.SubscriptionKey(currUserId, targetUserId));
    }

}
