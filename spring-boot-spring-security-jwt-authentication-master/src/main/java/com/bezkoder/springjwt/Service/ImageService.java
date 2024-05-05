package com.bezkoder.springjwt.Service;


import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ImageService {

    ImageRepo imageRepository;
    AbsenceRepo absenceRepo;
    CongeRepo congeRepo;
    public List<Image> list(){
        return imageRepository.findByOrderById();
    }

    public Optional<Image> getOne(int id){
        return imageRepository.findById(id);
    }

    public void save(Image image,Long id){
        Absence absence = absenceRepo.findById(id).get();
        absence.setImage(image);
        absenceRepo.save(absence);
        imageRepository.save(image);
    }
    public void saveJustificationConge(Image image,Long id){
        Conge conge = congeRepo.findById(id).get();
        conge.setImage(image);
        congeRepo.save(conge);
        imageRepository.save(image);
    }

    public void delete(int id){
        imageRepository.deleteById(id);
    }

}
