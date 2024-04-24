package com.bezkoder.springjwt.controllers;

import com.bezkoder.springjwt.Service.ServicePerformance;
import com.bezkoder.springjwt.Service.interfaces.ISerivcePerformance;
import com.bezkoder.springjwt.models.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/perfomanceEmpl")
public class PerformanceEmpController {
    @Autowired
    private ServicePerformance performanceService;
    ISerivcePerformance iSerivcePerformance;
    @PostMapping("/addperfomanceEmpl/{id}")
    public void savePerformance(@RequestBody PerformanceEmployee p,@PathVariable("id") Long id){
        iSerivcePerformance.savePerformance(p,id);
    }
//    @PutMapping("/updatePerformance")
//    public PerformanceEmployee updatePerformance(@RequestBody PerformanceEmployee p) throws Exception {
//        return iSerivcePerformance.savePerformance(p);
//    }
    @DeleteMapping("/deletePerformance/{p}")
    public void deletePerformance(@PathVariable("p") Long p) {
        iSerivcePerformance.deletePerformance(p);
    }
    @GetMapping("/getPerformanceById/{id}")
    public PerformanceEmployee getPerformanceById(@PathVariable("id") Long id){
        return iSerivcePerformance.getPerformanceById(id);
    }
    @GetMapping("/getAllPerformances")
    public List<PerformanceEmployee> getAllPerformances(){
        return iSerivcePerformance.getAllPerformances();
    }



    @GetMapping("/average-by-criteria")
    public ResponseEntity<Map<critereNote, Double>> getAverageByCriteria() {
        Map<critereNote, Double> averages = performanceService.getAverageByCriteria();
        return ResponseEntity.ok(averages);
    }
}
