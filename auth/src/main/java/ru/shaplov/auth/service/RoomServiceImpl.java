package ru.shaplov.auth.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shaplov.auth.domain.Message;
import ru.shaplov.auth.domain.Person;
import ru.shaplov.auth.domain.Room;
import ru.shaplov.auth.exception.ForbiddenException;
import ru.shaplov.auth.exception.NotFoundException;
import ru.shaplov.auth.repository.MessageRepository;
import ru.shaplov.auth.repository.PersonRepository;
import ru.shaplov.auth.repository.RoomRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author shaplov
 * @since 02.10.2019
 */
@Service
@Transactional
public class RoomServiceImpl implements RoomService {

    private static final Logger LOG = LogManager.getLogger(RoomServiceImpl.class);

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final PersonRepository personRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, MessageRepository messageRepository, PersonRepository personRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Room create(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void delete(String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        messageRepository.deleteAllByRoomName(roomName);
        roomRepository.delete(room);
        LOG.info("Room with id {} deleted", room.getId());
    }

    @Override
    public Set<Person> enter(Person person, String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        room.getPersons().add(person);
        LOG.info("Person {} entered room {}", person.getLogin(), roomName);
        return room.getPersons();
    }

    @Override
    public Set<Person> leave(Person person, String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        room.getPersons().remove(person);
        LOG.info("Person {} leave room {}", person.getLogin(), roomName);
        return room.getPersons();
    }

    @Override
    public Set<Person> persons(HttpServletRequest request, String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        adminOrPresentUserCheck(request, room);
        return room.getPersons();
    }

    @Override
    public Message post(Message message, String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        Person person = message.getPerson();
        if (!room.getPersons().contains(person)) {
            throw new ForbiddenException();
        }
        message.setRoom(room);
        message.setCreated(LocalDateTime.now());
        LOG.info("{} posted message {}", person.getLogin(), message.getId());
        return messageRepository.save(message);
    }

    @Override
    public void delete(HttpServletRequest request, int messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow(NotFoundException::new);
        if (!request.isUserInRole("ROLE_ADMIN")) {
            if (!personRepository.findByLogin(request.getUserPrincipal().getName()).orElseThrow(NotFoundException::new)
            .equals(message.getPerson())) {
                throw new ForbiddenException();
            }
        }
        messageRepository.delete(message);
        LOG.info("Message {} deleted", message.getId());
    }

    @Override
    public List<Message> list(HttpServletRequest request, String roomName) {
        Room room = roomRepository.findByName(roomName).orElseThrow(NotFoundException::new);
        adminOrPresentUserCheck(request, room);
        return messageRepository.findAllByRoomNameOrderByIdDesc(roomName);
    }

    private void adminOrPresentUserCheck(HttpServletRequest request, Room room) {
        if (!request.isUserInRole("ROLE_ADMIN")) {
            Person person = personRepository.findByLogin(request.getUserPrincipal().getName())
                    .orElseThrow(NotFoundException::new);
            if (!room.getPersons().contains(person)) {
                throw new ForbiddenException();
            }
        }
    }
}
