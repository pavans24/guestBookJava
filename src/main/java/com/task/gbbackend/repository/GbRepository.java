package com.task.gbbackend.repository;
import com.task.gbbackend.model.Guest;
import com.task.gbbackend.model.GuestLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GbRepository extends JpaRepository<Guest, Integer> {

    //Spring Data JPA will automatically generate the query method
    Guest findByUsername(String username);
    Guest findByName(String username);



}
