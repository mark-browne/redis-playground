package com.test.rest.service;

import com.test.rest.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetPersons {

    private final RestTemplate restTemplate;

    private String url = "http://localhost:3001/persons";

    @Scheduled(fixedRate = 10000)
    public void getPersons(){
       ResponseEntity<List<Person>> persons = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {});
        System.out.println(persons.getBody());
    }
}
