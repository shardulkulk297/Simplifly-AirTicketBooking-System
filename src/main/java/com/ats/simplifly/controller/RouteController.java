package com.ats.simplifly.controller;

import com.ats.simplifly.model.Route;
import com.ats.simplifly.service.RouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/api/flight/route/add")
    public ResponseEntity<?> addRoute(@RequestBody Route route){
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.addRoute(route));
    }

    @PutMapping("/api/flight/route/update/{routeId}")
    public ResponseEntity<?> updateRoute(@RequestBody Route route, @PathVariable int routeId){
        return ResponseEntity.status(HttpStatus.CREATED).body(routeService.updateRoute(route, routeId));
    }

    @GetMapping("/api/flight/route/getAll")
    public ResponseEntity<?> getAllRoutes(){
        return ResponseEntity.status(HttpStatus.OK).body(routeService.getAllRoutes());
    }

    @GetMapping("/api/flight/route/getById/{routeId}")
    public ResponseEntity<?> getRouteById(@PathVariable int routeId){
        return ResponseEntity.status(HttpStatus.FOUND).body(routeService.getRouteById(routeId));
    }

    @DeleteMapping("/api/flight/route/delete/{routeId}")
    public ResponseEntity<?> deleteRoute(@PathVariable int routeId){
        routeService.deleteRoute(routeId);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }
}
