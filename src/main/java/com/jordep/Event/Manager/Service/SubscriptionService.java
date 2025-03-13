package com.jordep.Event.Manager.Service;

import com.jordep.Event.Manager.DTO.SubscriptionRankingByUser;
import com.jordep.Event.Manager.DTO.SubscriptionRankingItem;
import com.jordep.Event.Manager.DTO.SubscriptionResponse;
import com.jordep.Event.Manager.Exceptions.EventNotFoundException;
import com.jordep.Event.Manager.Exceptions.IndicatorUserNotFoundException;
import com.jordep.Event.Manager.Exceptions.SubscriptionConflictException;
import com.jordep.Event.Manager.Model.Event;
import com.jordep.Event.Manager.Model.Subscription;
import com.jordep.Event.Manager.Model.User;
import com.jordep.Event.Manager.Repository.EventRepository;
import com.jordep.Event.Manager.Repository.SubscriptionRepository;
import com.jordep.Event.Manager.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class SubscriptionService {

    // No mesmo serviço acessar os 3 repositorys ( eventos, usuarios e incrições )

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public SubscriptionResponse createNewSubscription(String eventPrettyName, User user, Integer indicatorUserId) {

        // recuperar evento pelo prettyMame
        Event event = eventRepository.findByPrettyName(eventPrettyName);
        if (event == null) {
            throw new EventNotFoundException("Evento " + eventPrettyName + " não encontrado");
        }

        User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser == null) { // se o usuario não existir no banco, ele  salva o usuario (buscado pelo email)
            foundUser = userRepository.save(user);
        }

        User indicatorUser = null;
        if (indicatorUserId != null) { // se tiver ID do indicador no url
            indicatorUser = userRepository.findById(indicatorUserId).orElse(null); // busca no repositorio algum usuario com esse ID e armazena em indicatorUser
            if (indicatorUser == null) { // se nao encontrar o usuario, lança a exceção, se achar fica salvo em indicatorUser
                throw new IndicatorUserNotFoundException("Usuário indicador " + indicatorUserId + " não encontrado");
            }
        }

        // Cria uma nova inscrição com o usuario e o evento
        Subscription subscription = new Subscription();
        subscription.setEvent(event);
        subscription.setUser(foundUser);
        subscription.setIndication(indicatorUser);

        Subscription alreadySubscribed = subscriptionRepository.findByEventAndUser(event, foundUser);
        if (alreadySubscribed != null) { // se o usuario já foi inscrito em um evento
            throw new SubscriptionConflictException("Usuário " + foundUser.getName() + " já está inscrito no evento " + event.getTitle());
        }
        Subscription result = subscriptionRepository.save(subscription);

        return new SubscriptionResponse(result.getSubscriptionNumber(),"http://link.com/subscription" + result.getEvent().getPrettyName() + "/" + result.getUser().getId());
    }

    public List<SubscriptionRankingItem> getCompleteRanking(String eventPrettyName) {
        Event event = eventRepository.findByPrettyName(eventPrettyName);
        if (event == null) {
            throw new EventNotFoundException("Ranking do evento " + eventPrettyName + " não existe");
        }
        return subscriptionRepository.generateRanking(event.getEventId());
    }

    // retorna o usuário indicador, o id dele e quantas indicações aceitas ele tem
    public SubscriptionRankingByUser getRankingByUser(String eventPrettyName, Integer indicationUserId) {
        List<SubscriptionRankingItem> ranking = getCompleteRanking(eventPrettyName);

        SubscriptionRankingItem item = ranking.stream().filter(i -> i.indicationUserId().equals(indicationUserId)).findFirst().orElse(null);
        if (item == null) {
            throw new IndicatorUserNotFoundException("Não há incrições com indicação do usuário: " + indicationUserId);
        }

        Integer rankingPosition = IntStream.range(0, ranking.size())
                .filter(pos -> ranking.get(pos).indicationUserId().equals(indicationUserId))
                .findFirst().getAsInt();

        return new SubscriptionRankingByUser(item, rankingPosition + 1);
    }

}
