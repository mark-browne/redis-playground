package com.test.rest.service;

import com.test.rest.model.Person;
import com.test.rest.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetPersons {

    private final RestTemplate restTemplate;

    private final RedisTemplate redisTemplate;

    private final PersonRepository personRepository;
    private String url = "http://localhost:3001/persons";

    @Scheduled(fixedRate = 10000)
    public void getPersons(){
       ResponseEntity<List<Person>> persons = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {});
       System.out.println(persons.getBody());
       try {
           Long savedPerson =  redisTemplate.opsForList().leftPush("test1",persons.getBody().get(2));
           System.out.println("SAVED PERSON " +  savedPerson);
           System.out.println("SIZE "+ redisTemplate.opsForList().size("test1"));
           System.out.println("ITEM "+ redisTemplate.opsForList().index("test1",2));
           Person savedPersonPostgres =  personRepository.save(persons.getBody().get(2));
       }
       catch (Exception dae){
           System.out.println(dae.getClass());
       }
    }
}
