package com.ats.simplifly.service;

import com.ats.simplifly.exception.ResourceNotFoundException;
import com.ats.simplifly.model.Flight;
import com.ats.simplifly.model.Route;
import com.ats.simplifly.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route addRoute(Route route){
        return routeRepository.save(route);
    }

    public Route updateRoute(Route route, int routeId){
        Route routeToUpdate = routeRepository.findById(routeId).orElseThrow(()->new ResourceNotFoundException("Route Not found"));

        if(route.getOrigin()!=null){
            routeToUpdate.setOrigin(route.getOrigin());
        }
        if(route.getDestination()!=null){
            routeToUpdate.setDestination(route.getDestination());
        }
        if(route.getDuration()!=null){
            routeToUpdate.setDuration(route.getDuration());
        }
        return routeRepository.save(routeToUpdate);
    }

    public List<Route> getAllRoutes(){
        return routeRepository.findAll();
    }

    public Route getRouteById(int id){
        return routeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Route not found"));
    }

    public void deleteRoute(int id){

        if(routeRepository.findById(id)==null){
            throw new ResourceNotFoundException("Route not found");
        }

        routeRepository.deleteById(id);
    }





}
