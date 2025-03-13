package com.jordep.Event.Manager.DTO;

// parametro vem da query do db no repository subscription_number, indication_user_id, user_name
public record SubscriptionRankingItem(Long indicationCount, Integer indicationUserId, String indicationUserName) {
}
