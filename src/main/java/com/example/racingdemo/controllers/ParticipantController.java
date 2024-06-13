package com.example.racingdemo.controllers;

import com.example.racingdemo.entities.Car;
import com.example.racingdemo.entities.Participant;
import com.example.racingdemo.entities.jpaprojections.ParticipantProjection;
import com.example.racingdemo.entities.jpaprojections.TicketProjection;
import com.example.racingdemo.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/racers")
public class ParticipantController {
    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/add") // Map ONLY POST Requests
    public String addParticipant(@ModelAttribute Participant participantPayload, Model model) {
        //saving participant
        participantRepository.save(participantPayload);

        /*
         * After saving the racer, we have to retrieve his/her generated ID from the DB.
         * To do that, we need to retrieve his/her details from the ticket number which we already have at the moment
         */
        String ticketNumber = participantPayload.getTicketNumber();
        // retrieving the full name for displaying in the add car form(for aesthetics)
        String racerName = participantPayload.getFirstName() + " " + participantPayload.getSecondName();
        Long racerID = Long.valueOf(participantRepository.findByTicketNumber(ticketNumber).getId());

        // generating the action url needed for saving the car e.g /cars/add/4325
        String newRaceCarFormActionUrl = "/cars/add/"+racerID;

        model.addAttribute("racerID", racerID);
        model.addAttribute("racerName", racerName);
        model.addAttribute("actionUrl", newRaceCarFormActionUrl);

        Car raceCar = new Car();
        model.addAttribute("raceCar", raceCar);

        return "newRaceCar";
    }

    @GetMapping
    public @ResponseBody List<ParticipantProjection> retrieveAllParticipants(){
        return participantRepository.selectUserNames();
    }

    @GetMapping("/{id}")
    public Participant retrieveParticipantById(@PathVariable Long id){
        return participantRepository.findById(id).orElse(null);
    }

    @GetMapping("/ticket/{ticket_number}")
    public @ResponseBody TicketProjection retrieveParticipantIdByTicketNo(@PathVariable String ticket_number){
        System.out.println(participantRepository.findByTicketNumber(ticket_number).getId());
        return participantRepository.findByTicketNumber(ticket_number);
    }

    @GetMapping("/ui/add")
    public String participantAdditionForm(Model model) {
        Participant racer = new Participant();
        model.addAttribute("racer", racer);
        return "newParticipant";
    }

}

