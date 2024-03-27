package com.example.security.Specification;

import com.example.security.Models.Course;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.domain.Specification;

import java.io.FileNotFoundException;

public class CourseSpecification {



    public static Specification<Course> getByTitle(String title)
    {
        return (
                Root<Course> root,
                CriteriaQuery<?> query,
                CriteriaBuilder builder
                )->{
            if(title!=null)
            return builder.like(root.get("title"),"%"+title+"%");
            else
                return null;
        };
    }

}
