package com.jordep.Event.Manager.Controller;

import com.jordep.Event.Manager.DTO.ErrorMessage;
import com.jordep.Event.Manager.DTO.SubscriptionResponse;
import com.jordep.Event.Manager.Exceptions.EventNotFoundException;
import com.jordep.Event.Manager.Exceptions.SubscriptionConflictException;
import com.jordep.Event.Manager.Model.Subscription;
import com.jordep.Event.Manager.Model.User;
import com.jordep.Event.Manager.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscription/{eventPrettyName}")
    public ResponseEntity<?> createNewSubscription(@PathVariable String eventPrettyName, @RequestBody User subscriber) {
        try {
            SubscriptionResponse result = subscriptionService.createNewSubscription(eventPrettyName, subscriber);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (EventNotFoundException eventNotFoundException) {
            return ResponseEntity.status(404).body(new ErrorMessage(eventNotFoundException.getMessage()));
        }
        catch (SubscriptionConflictException subscriptionConflictException) {
            return ResponseEntity.status(409).body(new ErrorMessage(subscriptionConflictException.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }
}
