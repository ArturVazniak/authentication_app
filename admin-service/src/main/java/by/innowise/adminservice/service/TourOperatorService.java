package by.innowise.adminservice.service;

import by.innowise.adminservice.model.TourOperator;

import java.util.List;

public interface TourOperatorService {

    List<TourOperator> getAllTourOperator();

    TourOperator getByIdTourOperator(Long id);

    void addTourOperator(TourOperator tourOperator);

    void deleteByIdTourOperator(Long id);
}
