package com.ad.dao;

import com.ad.entity.Trips;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TripsDao extends CrudRepository<Trips, Integer> {
    List<Trips> findAllByDepartureAndDestinationAndDepartureDateBetween(String departure,
                                                                        String destination, Date begin, Date end);

    Trips findOne(Integer id);
}
