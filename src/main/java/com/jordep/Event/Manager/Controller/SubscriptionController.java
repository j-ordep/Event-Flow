package com.jordep.Event.Manager.Controller;

import com.jordep.Event.Manager.DTO.ErrorMessage;
import com.jordep.Event.Manager.DTO.SubscriptionResponse;
import com.jordep.Event.Manager.Exceptions.EventNotFoundException;
import com.jordep.Event.Manager.Exceptions.IndicatorUserNotFoundException;
import com.jordep.Event.Manager.Exceptions.SubscriptionConflictException;
import com.jordep.Event.Manager.Model.User;
import com.jordep.Event.Manager.Service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{eventPrettyName}", "/subscription/{eventPrettyName}/{indicatorUserId}"})
    public ResponseEntity<?> createNewSubscription(@PathVariable String eventPrettyName, @RequestBody User subscriber, @PathVariable(required = false) Integer indicatorUserId) {
        try {
            SubscriptionResponse result = subscriptionService.createNewSubscription(eventPrettyName, subscriber, indicatorUserId);
            if (result != null) {
                return ResponseEntity.ok(result);
            }
        } catch (EventNotFoundException exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
        catch (SubscriptionConflictException exception) {
            return ResponseEntity.status(409).body(new ErrorMessage(exception.getMessage()));
        }
        catch (IndicatorUserNotFoundException exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{eventPrettyName}/ranking")
    public ResponseEntity<?> getCompleteRanking(@PathVariable String eventPrettyName) {
        try {
            return ResponseEntity.ok(subscriptionService.getCompleteRanking(eventPrettyName).subList(0, 3));
        } catch (EventNotFoundException exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
    }

    @GetMapping("/subscription/{eventPrettyName}/ranking/{indicationUserId}")
    public ResponseEntity<?> generateRankinByEventAndUser(@PathVariable String eventPrettyName, @PathVariable Integer indicationUserId) {
        try {
            return ResponseEntity.ok(subscriptionService.getRankingByUser(eventPrettyName, indicationUserId));
        } catch (Exception exception) {
            return ResponseEntity.status(404).body(new ErrorMessage(exception.getMessage()));
        }
    }

}
