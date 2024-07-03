package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.model.Test;

public interface HotelService {

    String bookRoom();

    Boolean isAvailable();

    String addRoom(Test input);

    String removeRoom();

    String editRoom();
}
