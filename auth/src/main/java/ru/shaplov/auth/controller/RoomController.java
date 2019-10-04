package ru.shaplov.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.shaplov.auth.domain.Message;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Room;
import ru.shaplov.auth.service.PersonService;
import ru.shaplov.auth.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * @author shaplov
 * @since 02.10.2019
 */
@RestController
@RequestMapping("/room")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public class RoomController {

    private final RoomService roomService;
    private final PersonService personService;

    @Autowired
    public RoomController(RoomService roomService, PersonService personService) {
        this.roomService = roomService;
        this.personService = personService;
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return room.getId() != 0 ? ResponseEntity.badRequest().build()
                : new ResponseEntity<>(
                roomService.create(room),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{room}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String room) {
        roomService.delete(room);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{room}/enter")
    public ResponseEntity<Set<Person>> enter(Principal principal, @PathVariable String room) {
        Person person = personService.findByLogin(principal.getName()).orElseThrow();
        return new ResponseEntity<>(
                roomService.enter(person, room),
                HttpStatus.OK
        );
    }

    @PostMapping("/{room}/leave")
    public ResponseEntity<Set<Person>> leave(Principal principal, @PathVariable String room) {
        Person person = personService.findByLogin(principal.getName()).orElseThrow();
        return new ResponseEntity<>(
                roomService.leave(person, room),
                HttpStatus.OK
        );
    }

    @PostMapping("/{room}/post")
    public ResponseEntity<Message> post(Principal principal,
                                        @PathVariable String room,
                                        @RequestBody Message message) {
        Person person = personService.findByLogin(principal.getName()).orElseThrow();
        message.setPerson(person);
        return new ResponseEntity<>(
                roomService.post(message, room),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/message/{id}")
    public ResponseEntity<Void> deleteMessage(HttpServletRequest request, @PathVariable int id) {
        roomService.delete(request, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{room}/messages")
    public ResponseEntity<List<Message>> listMsg(HttpServletRequest request, @PathVariable String room) {
        return new ResponseEntity<>(
                roomService.list(request, room),
                HttpStatus.OK
        );
    }

    @GetMapping("/{room}/persons")
    public ResponseEntity<Set<Person>> listPerson(HttpServletRequest request, @PathVariable String room) {
        return new ResponseEntity<>(
                roomService.persons(request, room),
                HttpStatus.OK
        );
    }
}
