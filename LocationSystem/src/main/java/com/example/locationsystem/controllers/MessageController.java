package com.example.locationsystem.controllers;
import com.example.locationsystem.models.Message;
import com.example.locationsystem.models.Person;
import com.example.locationsystem.service.MessageService;
import com.example.locationsystem.service.PersonDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@AllArgsConstructor
public class MessageController {
    private final PersonDetailService personDetailService;
    private final MessageService messageService;

    @GetMapping("/chat/{receiverId}")
    public String chatPage(Model model, @PathVariable("receiverId") int receiverId){
        List<Message> messages = messageService.getMessagesForChat(receiverId);
        model.addAttribute("messages", messages);
        model.addAttribute("receiverId", receiverId);
        return "chat";
    }

    @PostMapping("/send-message")
    public String sendMessage(Model model,
                              @RequestParam("receiverId") int receiverId,
                              @RequestParam("messageContent") String messageContent) {

        Person person = personDetailService.getAuthPerson();
        int senderId = person.getId();
        model.addAttribute("senderId",senderId);
        messageService.sendMessage(senderId, receiverId, messageContent);
        return "redirect:/chat/" + receiverId;
    }
}
