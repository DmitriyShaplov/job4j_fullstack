package ru.shaplov.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shaplov.auth.domain.Message;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Room;
import ru.shaplov.auth.service.PersonService;
import ru.shaplov.auth.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean private RoomService roomService;
    @MockBean private PersonService personService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser
    public void whenCreateRoom() throws Exception {
        Room room = new Room();
        room.setName("123room");
        when(
                roomService.create(room)
        ).thenReturn(room);
        mvc.perform(
                post("/room/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(room))
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(mapper.writeValueAsString(room)));
        verify(roomService, times(1)).create(room);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void whenDeleteAsAdminThenOk() throws Exception {
        mvc.perform(delete("/room/123"))
                .andExpect(status().isOk());
        verify(roomService, times(1)).delete("123");
    }

    @Test
    @WithMockUser
    public void whenDeleteAsUserThen403() throws Exception {
        mvc.perform(delete("/room/123"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "login")
    public void whenEnterRoomThenOk() throws Exception {
        Room room = new Room();
        room.setName("123");
        Person person = new Person();
        person.setLogin("login");
        Set<Person> resultSet = Set.of(person);
        when(personService.findByLogin("login"))
                .thenReturn(Optional.of(person));
        when(roomService.enter(person, "123")).thenReturn(resultSet);
        mvc.perform(post("/room/123/enter"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(resultSet)));
        verify(roomService, times(1)).enter(person, "123");
    }

    @Test
    @WithMockUser(username = "login")
    public void whenLeaveRoomThenOk() throws Exception {
        Room room = new Room();
        room.setName("123");
        Person person = new Person();
        person.setLogin("login");
        Set<Person> resultSet = new HashSet<>();
        when(personService.findByLogin("login"))
                .thenReturn(Optional.of(person));
        when(roomService.leave(person, "test")).thenReturn(resultSet);
        mvc.perform(post("/room/123/leave"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(resultSet)));
        verify(roomService, times(1)).leave(person, "123");
    }

    @Test
    @WithMockUser(username = "login")
    public void whenPostMessageThenOkAndMessage() throws Exception {
        Message message = new Message();
        message.setContent("test message");
        Person person = new Person();
        person.setLogin("login");
        Message result = new Message();
        result.setContent("test message");
        result.setPerson(person);
        when(personService.findByLogin("login")).thenReturn(Optional.of(person));
        when(roomService.post(any(Message.class), eq("123")))
                .thenAnswer(invocation -> invocation.getArguments()[0]);
        mvc.perform(
                post("/room/123/post").contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(message))
        ).andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
        verify(personService).findByLogin("login");
        verify(roomService).post(any(Message.class), eq("123"));
    }

    @Test
    @WithMockUser
    public void whenDeleteMessageThenOk() throws Exception {
        mvc.perform(delete("/room/message/1"))
                .andExpect(status().isOk());
        verify(roomService).delete(any(HttpServletRequest.class), eq(1));
    }

    @Test
    @WithMockUser
    public void whenGetMessagesList() throws Exception {
        Message msg1 = new Message();
        msg1.setContent("msg1");
        Message msg2 = new Message();
        msg2.setContent("msg2");
        var result = List.of(msg1, msg2);
        when(roomService.list(any(HttpServletRequest.class), eq("123")))
                .thenReturn(result);
        mvc.perform(get("/room/123/messages"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
        verify(roomService).list(any(HttpServletRequest.class), eq("123"));
    }

    @Test
    @WithMockUser
    public void whenGetRoomPersons() throws Exception {
        Person person = new Person();
        person.setLogin("login");
        var result = Set.of(person);
        when(roomService.persons(any(HttpServletRequest.class), eq("123")))
                .thenReturn(result);
        mvc.perform(get("/room/123/persons"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(result)));
        verify(roomService).persons(any(HttpServletRequest.class), eq("123"));
    }
}