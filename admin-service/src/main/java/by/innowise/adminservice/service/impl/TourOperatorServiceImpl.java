package by.innowise.adminservice.service.impl;

import by.innowise.adminservice.model.TourOperator;
import by.innowise.adminservice.repository.TourOperatorRepository;
import by.innowise.adminservice.service.TourOperatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TourOperatorServiceImpl implements TourOperatorService {

    private final TourOperatorRepository tourOperatorRepository;

    @Autowired
    public TourOperatorServiceImpl(TourOperatorRepository tourOperatorRepository) {
        this.tourOperatorRepository = tourOperatorRepository;
    }

    @Override
    public List<TourOperator> getAllTourOperator() {
        List<TourOperator> tourOperatorList = tourOperatorRepository.findAll();
        log.info("IN getAllTourOperator : {} TourOperators found", tourOperatorList.size());
        return tourOperatorList;
    }

    @Override
    public TourOperator getByIdTourOperator(Long id) {
        TourOperator tourOperator = tourOperatorRepository.getById(id);

        if(tourOperator == null) {
            log.warn("IN getByIdTourOperator - no tourOperator found by id {} ", id);
            return null;
        }
        return tourOperator;
    }

    @Override
    public void addTourOperator(TourOperator tourOperator) {
        tourOperatorRepository.save(tourOperator);
        log.info("IN addTourOperator - tourOperator successfully save");
    }

    @Override
    public void deleteByIdTourOperator(Long id) {
        tourOperatorRepository.deleteById(id);
        log.info("IN deleteByIdTourOperator - tourOperator whit id: {} successfully delete", id);
    }
}
