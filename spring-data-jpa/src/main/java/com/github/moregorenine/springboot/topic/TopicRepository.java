package com.github.moregorenine.springboot.topic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface TopicRepository extends CrudRepository<Topic, String> {
}
