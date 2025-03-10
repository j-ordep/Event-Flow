package com.jordep.Event.Manager.Repository;

import com.jordep.Event.Manager.Model.Event;
import com.jordep.Event.Manager.Model.Subscription;
import com.jordep.Event.Manager.Model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndUser(Event event, User user);
}
