package com.bezkoder.springjwt.Service.interfaces;

import com.bezkoder.springjwt.models.PerformanceEmployee;

import java.util.List;

public interface ISerivcePerformance {
    public List<PerformanceEmployee> getAllPerformances();
    public PerformanceEmployee getPerformanceById(Long id);
    public PerformanceEmployee savePerformance(PerformanceEmployee performance,Long id);
    public void deletePerformance(Long id);
}
