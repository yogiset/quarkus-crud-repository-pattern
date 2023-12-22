package org.acme;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.StudentDto;
import org.acme.entity.Student;
import org.acme.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Path("students")
public class StudentResource {
    @Inject
   private StudentRepository studentRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<StudentDto> getStudents(){
        List<Student>students = studentRepository.listAll();

        return students.stream()
                .map(item -> new StudentDto(item.getId(),item.getName(),item.getMajor()))
                .collect(Collectors.toList());
         //      .toList(); // bisa pake yg ini juga
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response storeStudent(@Valid StudentDto req){
        Student student = new Student();
        student.setName(req.name());
        student.setMajor(req.major());

        studentRepository.persist(student);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id")Long id, StudentDto req){
        Optional<Student>studentOptional = studentRepository.findByIdOptional(id);
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setName(req.name());
            student.setMajor(req.major());

            studentRepository.persist(student);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteStudent(@PathParam("id")Long id){
        Optional<Student>studentOptional = studentRepository.findByIdOptional(id);
        if(studentOptional.isPresent()){
            studentRepository.delete(studentOptional.get());
           return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
