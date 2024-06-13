package com.example.racingdemo.repositories;

import com.example.racingdemo.entities.Participant;
import com.example.racingdemo.entities.jpaprojections.ParticipantProjection;
import com.example.racingdemo.entities.jpaprojections.TicketProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    @Query("SELECT participants FROM Participant participants")
    List<ParticipantProjection> selectUserNames();

    @Query("SELECT participantTicket FROM Participant participantTicket WHERE participantTicket.ticketNumber=:ticketNumber")
    TicketProjection findByTicketNumber(String ticketNumber);

}
