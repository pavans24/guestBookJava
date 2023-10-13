package com.task.gbbackend.service;

import com.task.gbbackend.controller.GbController;
import com.task.gbbackend.model.Guest;
import com.task.gbbackend.model.GuestLogin;
import com.task.gbbackend.repository.GbRepository;
import com.task.gbbackend.response.GbLoginResponse;
import com.task.gbbackend.response.GbRemoveResponse;
import com.task.gbbackend.response.GbSignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class GuestBookService {

    private final GbRepository gbRepository;

    GbSignUpResponse gbSignUpResponse = new GbSignUpResponse();
    GbLoginResponse gbLoginResponse = new GbLoginResponse();


    @Autowired
    public GuestBookService(GbRepository gbRepository){
        this.gbRepository = gbRepository;
    }

    //to save the Guest SignUp Data
    public GbSignUpResponse create(Guest guest)
    {
        gbRepository.save(guest);
        gbSignUpResponse.setStatus("success");
        gbSignUpResponse.setStatusCode("200");
        return gbSignUpResponse;
    }

    //to search the Guest in Database
    public GbLoginResponse findByLogin(GuestLogin guestLogin)
    {
        Guest guestLoginResult = new Guest();
        guestLoginResult = gbRepository.findByUsername(guestLogin.getUsername());
        if(guestLoginResult != null && guestLogin.getUsername().equals(guestLoginResult.getUsername()) && guestLogin.getPassword().equals(guestLoginResult.getPassword())){
            gbLoginResponse.setStatusCode("200");
            gbLoginResponse.setMessage("User Exists");
        }
        else {
            gbLoginResponse.setStatusCode("400");
            gbLoginResponse.setMessage("User Not Exists");
        }

        return gbLoginResponse;
    }

    //to create the Guest Entry in the Database
    public GbSignUpResponse createGuestEntry(Guest guest)
    {
        guest.setMessage("Pending");
        gbRepository.save(guest);
        gbSignUpResponse.setStatusCode("200");
        return gbSignUpResponse;
    }

    //to create the Guest Entry in the Database
    public Guest approveGuestEntry(String name)
    {

        Guest guestSearchResult = gbRepository.findByName(name);
        guestSearchResult.setMessage("Approved");
        return gbRepository.save(guestSearchResult);
    }

    //to remove the Guest Entry from the Database
    public GbRemoveResponse removeGuestEntry(String name)
    {
        GbRemoveResponse response = new GbRemoveResponse();
        Guest guestSearchResult = gbRepository.findByName(name);
        gbRepository.delete(guestSearchResult);
        response.setStatus("success");
        response.setStatusCode("200");
        return response;
    }

    //to get all the Guest Entries from the Database
    public List<Guest> getAllEntries()
    {
        return gbRepository.findAll();
    }
}
