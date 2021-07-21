package by.innowise.adminservice.service;

import by.innowise.adminservice.model.Flight;

import java.util.List;

public interface FlightService {

    List<Flight> getAllFlight();

    Flight getByIdFlight(Long id);

    void addFlight(Flight flight);

    void deleteByIdFlight(Long id);
}
