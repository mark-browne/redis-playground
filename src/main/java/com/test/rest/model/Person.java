package com.test.rest.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;

@RedisHash("Person")
@Data
public class Person implements Serializable{
    String name;
    String number;
    @Id
    int id;
}
