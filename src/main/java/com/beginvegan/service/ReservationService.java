package com.beginvegan.service;

import com.beginvegan.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reservationService")
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
}
