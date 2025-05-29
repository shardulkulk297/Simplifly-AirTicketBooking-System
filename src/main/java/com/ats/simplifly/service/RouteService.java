package com.ats.simplifly.service;

import com.ats.simplifly.model.Route;
import com.ats.simplifly.repository.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route addRoute(Route route){
        return routeRepository.save(route);
    }

}
