package com.jordep.Event.Manager.Repository;

import com.jordep.Event.Manager.DTO.SubscriptionRankingItem;
import com.jordep.Event.Manager.Model.Event;
import com.jordep.Event.Manager.Model.Subscription;
import com.jordep.Event.Manager.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndUser(Event event, User user);

    @Query(value =
            " SELECT count(subscription_number) as quantidade, indication_user_id, user_name " +
            " FROM tb_subscription INNER JOIN tb_user" +
            "    on tb_subscription.indication_user_id = tb_user.user_id" +
            " WHERE indication_user_id is not null " +
            "    AND event_id = :eventId" +
            " group by indication_user_id" +
            " order by quantidade desc", nativeQuery = true)
    public List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
