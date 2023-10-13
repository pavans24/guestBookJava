package com.task.gbbackend.service;

import com.task.gbbackend.model.Guest;
import com.task.gbbackend.model.GuestLogin;
import com.task.gbbackend.repository.GbRepository;
import com.task.gbbackend.response.GbLoginResponse;
import com.task.gbbackend.response.GbSignUpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class GuestBookServiceTest {
    @InjectMocks
    private GuestBookService guestBookService;

    @Mock
    private GbRepository gbRepository;

    @InjectMocks
    private GuestLogin gbLoginService;

    @Test
    public void testCreateGuest() {

        Guest guest = new Guest();
        guest.setName("test");
        guest.setEmail("test@example.com");

        GbSignUpResponse expectedResponse = new GbSignUpResponse();
        expectedResponse.setStatus("success");
        expectedResponse.setStatusCode("200");

        Mockito.when(gbRepository.save(guest)).thenReturn(guest);

        GbSignUpResponse actualResponse = guestBookService.create(guest);

        Mockito.when(gbRepository.save(Mockito.any(Guest.class))).thenReturn(guest);

        // response check
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
    }

    @Test
    public void testFindByLoginUserExists() {
        GuestLogin guestLogin = new GuestLogin("testUser", "testPassword");

        Guest existingGuest = new Guest();
        existingGuest.setUsername("testUser");
        existingGuest.setPassword("testPassword");
        Mockito.when(gbRepository.findByUsername("testUser")).thenReturn(existingGuest);
        GbLoginResponse response = guestBookService.findByLogin(guestLogin);

        // response check
        assertEquals("200", response.getStatusCode());
        assertEquals("User Exists", response.getMessage());
    }

    @Test
    public void testCreateGuestEntry() {
        Guest guest = new Guest();
        guest.setName("testUser");
        guest.setEmail("testEmail");
        GbSignUpResponse response = new GbSignUpResponse();
        response.setStatusCode("200");

        Mockito.when(gbRepository.save(guest)).thenReturn(guest);

        GbSignUpResponse result = guestBookService.createGuestEntry(guest);

        // response check
        assertEquals("200", result.getStatusCode());
        assertEquals("Pending", guest.getMessage());
    }


    @Test
    public void testRemoveGuestEntry() {

        Guest guest = new Guest();
        guest.setName("test2");

        Mockito.when(gbRepository.findByName("test2")).thenReturn(guest);

        guestBookService.removeGuestEntry("test2");

        // check
        Mockito.verify(gbRepository).delete(guest);
    }

    @Test
    public void testGetAllEntries() {

        List<Guest> guestList = new ArrayList<>();
        Guest guest1 = new Guest();
        guest1.setName("test1");
        Guest guest2 = new Guest();
        guest1.setName("test1");
        guestList.add(guest1);
        guestList.add(guest2);

        Mockito.when(gbRepository.findAll()).thenReturn(guestList);

        List<Guest> result = guestBookService.getAllEntries();

        // check
        assertEquals(guestList, result);
    }
}
