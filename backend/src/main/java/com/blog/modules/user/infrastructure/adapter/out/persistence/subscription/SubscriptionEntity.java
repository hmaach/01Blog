package com.blog.modules.user.infrastructure.adapter.out.persistence.subscription;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "subscriptions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"subscriber_id", "subscribed_to_id"})
})
public class SubscriptionEntity {

    @EmbeddedId
    private SubscriptionKey id;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(UUID subscriberId, UUID subscribedToId) {
        this.id = new SubscriptionKey(subscriberId, subscribedToId);
    }

    public SubscriptionKey getId() {
        return id;
    }

    @Embeddable
    public static class SubscriptionKey implements Serializable {

        @Column(name = "subscriber_id", nullable = false)
        private UUID subscriberId;

        @Column(name = "subscribed_to_id", nullable = false)
        private UUID subscribedToId;

        public SubscriptionKey() {
        }

        public SubscriptionKey(UUID subscriberId, UUID subscribedToId) {
            this.subscriberId = subscriberId;
            this.subscribedToId = subscribedToId;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SubscriptionKey)) {
                return false;
            }
            SubscriptionKey that = (SubscriptionKey) obj;
            return Objects.equals(subscriberId, that.subscriberId)
                    && Objects.equals(subscribedToId, that.subscribedToId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(subscriberId, subscribedToId);
        }
    }
}
