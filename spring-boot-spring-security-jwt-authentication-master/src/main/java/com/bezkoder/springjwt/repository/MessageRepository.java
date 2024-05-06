package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Chat;
import com.bezkoder.springjwt.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
