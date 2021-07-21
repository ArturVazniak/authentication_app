package by.innowise.adminservice.service.impl;

import by.innowise.adminservice.model.Hotel;
import by.innowise.adminservice.repository.HotelRepository;
import by.innowise.adminservice.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> getAllHotel() {
        List<Hotel> hotelList = hotelRepository.findAll();
        log.info("IN getAllHotel : {} hotels found", hotelList.size());
        return hotelList;
    }

    @Override
    public Hotel getByIdHotel(Long id) {
        Hotel hotel = hotelRepository.getById(id);

        if(hotel == null){
            log.warn("IN getByIdHotel - no hotels found by id {} ", id);
            return null;
        }
        return hotel;
    }

    @Override
    public void addHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        log.info("IN addHotel - hotel successfully save");
    }

    @Override
    public void deleteByIdHotel(Long id) {
        hotelRepository.deleteById(id);
        log.info("IN deleteByIdHotel - hotel whit id: {} successfully delete", id);
    }
}
