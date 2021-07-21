package by.innowise.adminservice.service.impl;

import by.innowise.adminservice.model.Flight;
import by.innowise.adminservice.repository.FlightRepository;
import by.innowise.adminservice.service.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getAllFlight() {
        List<Flight> flightList = flightRepository.findAll();
        log.info("IN getAllFlight : {} flights found", flightList.size());
        return flightList;
    }

    @Override
    public Flight getByIdFlight(Long id) {
        Flight flight = flightRepository.getById(id);

        if(flight == null){
            log.warn("IN getByIdFlight - no flights found by id {} ", id);
            return null;
        }
        return flight;
    }


    @Override
    public void addFlight(Flight flight) {
        flightRepository.save(flight);
        log.info("IN addFlight - flight successfully save");
    }

    @Override
    public void deleteByIdFlight(Long id) {
        flightRepository.deleteById(id);
        log.info("IN deleteByIdFlight - flight whit id: {} successfully delete", id);
    }
}
