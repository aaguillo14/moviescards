package com.lauracercas.moviecards.service.actor;


import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.repositories.ActorJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    RestTemplate template;

    String url = "https://moviecards-service-aguilar.azurewebsites.net/actors";

     @Override
    public List<Actor> getAllActors() {
        Actor[] actores = template.getForObject(url,
        Actor[].class);
        return Arrays.asList(actores);
    }

    @Override
    public Actor save(Actor actor) {
        if (actor.getId() != null && actor.getId() > 0) {
            template.put(url, actor);
            } else {
            actor.setId(0);
            template.postForObject(url, actor, String.class);
            }
            return actor;
    }

    @Override
    public Actor getActorById(Integer actorId) {
        return template.getForObject(url+"/"+actorId, Actor.class);        
    }
}
