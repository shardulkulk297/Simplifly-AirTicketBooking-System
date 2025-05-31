package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Schedule;
import com.ats.simplifly.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule scheduleFlight(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Schedule schedule, String username){
        Schedule scheduleToUpdate = scheduleRepository.getByUsername(username);

        if(schedule.getArrivalTime()!=null){
            scheduleToUpdate.setArrivalTime(schedule.getArrivalTime());
        }
        if(schedule.getFare()!=0){
            scheduleToUpdate.setFare(schedule.getFare());
        }
        if(schedule.getFlight()!=null){
            scheduleToUpdate.setFlight(schedule.getFlight());
        }
        if(schedule.getDepartureTime()!=null){
            scheduleToUpdate.setDepartureTime(schedule.getDepartureTime());
        }
        return scheduleRepository.save(scheduleToUpdate);
    }

    public Schedule getScheduleByFlight(int flightId)
    {
        return scheduleRepository.getByFlight(flightId);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public void deleteSchedule(int scheduleId){

        if(scheduleRepository.findById(scheduleId) == null){
            throw new ResourceNotFoundException("Schedule Not found");
        }

        scheduleRepository.deleteById(scheduleId);
    }


}
