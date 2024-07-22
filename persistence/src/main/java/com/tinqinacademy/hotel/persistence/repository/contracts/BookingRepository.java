package com.tinqinacademy.hotel.persistence.repository.contracts;

import com.tinqinacademy.hotel.persistence.model.Booking;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends GenericRepository<Booking>{
    List<Booking> findByRoomId(UUID roomId);
}
