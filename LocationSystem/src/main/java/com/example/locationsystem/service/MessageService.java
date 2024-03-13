package com.example.locationsystem.service;

import com.example.locationsystem.models.Message;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.repository.MessageRepository;
import com.example.locationsystem.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;
    private final PersonDetailService personDetailService;

    public MessageService(PersonRepository personRepository, MessageRepository messageRepository, PersonDetailService personDetailService) {
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
        this.personDetailService = personDetailService;
    }
    public void sendMessage(int senderId, int receiverId, String messageContent) {
        Person sender = personRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));
        Person receiver = personRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid receiver ID"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMessageContent(messageContent);
        message.setLocalDateTime(LocalDateTime.now());

        messageRepository.save(message);
    }
    public List<Message> getMessagesForChat(int receiverId){
        Person person = personDetailService.getAuthPerson();
        int authId = person.getId();

        return messageRepository.findMessagesBetweenUsers(authId,receiverId);
    }
}
