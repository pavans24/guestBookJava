package com.task.gbbackend.controller;
import com.task.gbbackend.model.Guest;
import com.task.gbbackend.model.GuestLogin;
import com.task.gbbackend.response.GbLoginResponse;
import com.task.gbbackend.response.GbRemoveResponse;
import com.task.gbbackend.response.GbSignUpResponse;
import com.task.gbbackend.service.GuestBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gb")
public class GbController {
    @Autowired
    GuestBookService guestBookService;

    //creating post mapping that post the GuestBook SignUp detail in the database
    @PostMapping("/guest")
    private GbSignUpResponse signupGuest(@RequestBody Guest guest)
    {
        return guestBookService.create(guest);
    }

    //searching Guest Login Details in the database to check whether user exists
    @PostMapping("/guest-login")
    private GbLoginResponse searchGuest(@RequestBody GuestLogin guestLogin)
    {
        return guestBookService.findByLogin(guestLogin);
    }

    //creating to post the Guest Entry detail in the database
    @PostMapping("/guest-entry")
    private GbSignUpResponse updateGuest(@RequestBody Guest guest)
    {
        return guestBookService.createGuestEntry(guest);
    }

    //approving the Guest Entry detail in the database
    @PutMapping("/approveEntry")
    private Guest approveGuest(@RequestParam Integer id)
    {
        return guestBookService.approveGuestEntry(id);
    }

    //edit the Guest Entry detail in the database
    @PutMapping("/editEntry")
    private Guest editGuest(@RequestParam Integer id,@RequestBody Guest guest)
    {
        return guestBookService.editGuestEntry(guest,id);
    }

    //approving the Guest Entry detail in the database
    @DeleteMapping("/removeEntry")
    private GbRemoveResponse remeoveGuest(@RequestParam Integer id)
    {
        GbRemoveResponse response = guestBookService.removeGuestEntry(id);
         return response;
    }

    //fetching all Guest Entry Details from the database
    @GetMapping("/entries")
    private List<Guest> getEntries()
    {
        return guestBookService.getAllEntries();
    }

}
