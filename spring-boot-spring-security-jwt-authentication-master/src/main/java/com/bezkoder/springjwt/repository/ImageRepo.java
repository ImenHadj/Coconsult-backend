package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ImageRepo extends JpaRepository<Image,Integer> {

    List<Image> findByOrderById();
}
