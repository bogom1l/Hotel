package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.Test;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl implements HotelService{

    @Override
    public String bookRoom() {
        return "You booked a room";
    }

    @Override
    public Boolean isAvailable() {
        return true;
    }

    @Override
    public String addRoom(Test input) {
        return input.getName() + input.getType();
    }

    @Override
    public String removeRoom() {
        return "You removed a room";
    }

    @Override
    public String editRoom() {
        return "You edited a room";
    }
}
