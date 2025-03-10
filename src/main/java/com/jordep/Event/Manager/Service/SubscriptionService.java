package com.jordep.Event.Manager.Service;

import com.jordep.Event.Manager.DTO.SubscriptionResponse;
import com.jordep.Event.Manager.Exceptions.EventNotFoundException;
import com.jordep.Event.Manager.Exceptions.SubscriptionConflictException;
import com.jordep.Event.Manager.Model.Event;
import com.jordep.Event.Manager.Model.Subscription;
import com.jordep.Event.Manager.Model.User;
import com.jordep.Event.Manager.Repository.EventRepository;
import com.jordep.Event.Manager.Repository.SubscriptionRepository;
import com.jordep.Event.Manager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    // No mesmo serviço acessar os 3 repositorys ( eventos, usuarios e incrições )

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;


    // pega um evento que já existe e um usuario que está sendo cadastrado agora
    public SubscriptionResponse createNewSubscription(String eventPrettyName, User user) {

        // recuperar evento pelo prettyMame
        Event event = eventRepository.findByPrettyName(eventPrettyName);
        if (event == null) { // se o evento não existir no bando
            throw new EventNotFoundException("Evento " + eventPrettyName + " não encontrado");
        }

        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) { // se o usuario não existir no banco, ele  salva o usuario (buscado pelo email)
            foundUser = userRepository.save(user);
        }

        // Cria uma nova inscrição com o usuario e o evento
        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setUser(foundUser);

        Subscription alreadySubscribed = subscriptionRepository.findByEventAndUser(event, foundUser);
        if (alreadySubscribed != null) { // se o usuario já foi inscrito em um evento
            throw new SubscriptionConflictException("Usuário " + foundUser.getName() + " já está inscrito no evento " + event.getTitle());
        }

        Subscription result = subscriptionRepository.save(subscription); // salva no banco essa incrição

        return new SubscriptionResponse(result.getSubscriptionNumber(),"http://codecraft.com/" + result.getEvent().getPrettyName() + "/" + result.getUser().getId());

    }
}
