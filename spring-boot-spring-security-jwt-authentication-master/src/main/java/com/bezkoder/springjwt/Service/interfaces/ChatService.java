package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.exceptions.ChatAlreadyExistException;
import com.bezkoder.springjwt.exceptions.ChatNotFoundException;
import com.bezkoder.springjwt.exceptions.NoChatExistsInTheRepository;
import com.bezkoder.springjwt.exceptions.UserNotFoundException;
import com.bezkoder.springjwt.models.Chat;
import com.bezkoder.springjwt.models.Message;
import com.bezkoder.springjwt.models.User;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ChatService {



    Chat addChat(Chat chat) throws ChatAlreadyExistException;

    List<Chat> findallchats() throws NoChatExistsInTheRepository;

    Chat getById(int id)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatBySecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName)  throws ChatNotFoundException;

    Chat addMessage(Message add, int chatId)  throws ChatNotFoundException;

    Message addMessage2(Message message);

    List<Message> getAllMessagesInChat(int chatId) throws NoChatExistsInTheRepository;

    List<Message> searchMessagesByWord(int chatId, String word);

}
