package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.Service.interfaces.ChatService;
import com.bezkoder.springjwt.exceptions.ChatAlreadyExistException;
import com.bezkoder.springjwt.exceptions.ChatNotFoundException;
import com.bezkoder.springjwt.exceptions.NoChatExistsInTheRepository;
import com.bezkoder.springjwt.exceptions.UserNotFoundException;
import com.bezkoder.springjwt.models.Chat;
import com.bezkoder.springjwt.models.Message;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.repository.ChatRepository;
import com.bezkoder.springjwt.repository.MessageRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;


    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public Message addMessage2(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessagesInChat(int chatId) throws NoChatExistsInTheRepository {
        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isEmpty()) {
            throw new NoChatExistsInTheRepository();
        } else {
            return chat.get().getMessageList();
        }
    }

    @Override
    public List<Chat> findallchats() throws NoChatExistsInTheRepository {
        if (chatRepository.findAll().isEmpty()) {
            throw new NoChatExistsInTheRepository();
        } else {
            return chatRepository.findAll();
        }

    }

    @Override
    public Chat getById(int id) throws ChatNotFoundException {
        Optional<Chat> chatid = chatRepository.findById(id);
        if (chatid.isPresent()) {
            return chatid.get();
        } else {
            throw new ChatNotFoundException();
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserName(username);

        if (chat.isEmpty()) {
            throw new ChatNotFoundException();
        } else {
            return chat;
        }
    }

    @Override
    public HashSet<Chat> getChatBySecondUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatBySecondUserName(username);
        if (chat.isEmpty()) {
            throw new ChatNotFoundException();
        } else {
            return chat;
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserName(username);
        HashSet<Chat> chat1 = chatRepository.getChatBySecondUserName(username);

        chat1.addAll(chat);

        if (chat.isEmpty() && chat1.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chat1.isEmpty()) {
            return chat;
        } else {
            return chat1;
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserNameAndSecondUserName(firstUserName, secondUserName);
        HashSet<Chat> chat1 = chatRepository.getChatBySecondUserNameAndFirstUserName(firstUserName, secondUserName);
        if (chat.isEmpty() && chat1.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chat.isEmpty()) {
            return chat1;
        } else {
            return chat;
        }
    }

    @Override
    public Chat addMessage(Message add, int chatId) throws ChatNotFoundException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        Chat abc = chat.get();

        if (abc.getMessageList() == null) {
            List<Message> msg = new ArrayList<>();
            msg.add(add);
            abc.setMessageList(msg);
            return chatRepository.save(abc);
        } else {
            List<Message> rates = abc.getMessageList();
            rates.add(add);
            abc.setMessageList(rates);
            return chatRepository.save(abc);
        }
    }

    public List<Message> searchMessagesByWord(int chatId, String word) {
        // Récupérer le chat
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();

            // Récupérer tous les messages du chat
            List<Message> messages = chat.getMessageList();

            // Filtrer les messages qui contiennent le mot spécifié
            return messages.stream()
                    .filter(message -> {
                        String replyMessage = message.getReplymessage();
                        return replyMessage != null && replyMessage.contains(word);
                    })
                    .collect(Collectors.toList());
        } else {
            throw new ChatNotFoundException();
        }
    }

}




