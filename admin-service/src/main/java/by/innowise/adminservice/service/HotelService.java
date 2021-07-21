package by.innowise.adminservice.service;

import by.innowise.adminservice.model.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> getAllHotel();

    Hotel getByIdHotel(Long id);

    void addHotel(Hotel hotel);

    void deleteByIdHotel(Long id);
}
