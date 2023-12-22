package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Student;

import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
    public List<Student>findNotDelete(){
        return list("is_deleted",false);
    }

}
