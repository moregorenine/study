package com.github.moregorenine.springboot.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CourseRepository extends CrudRepository<Course, String> {
	public List<Course> findByTopicId(String topicId);
}
