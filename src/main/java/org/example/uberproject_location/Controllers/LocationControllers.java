package org.example.uberproject_location.Controllers;

import org.example.uberproject_location.Dto.DriverLocationDto;
import org.example.uberproject_location.Dto.NearbyDriverRequestDto;
import org.example.uberproject_location.Dto.SaveDriverLocationDto;
import org.example.uberproject_location.Services.LocationService;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationControllers {


    private  LocationService locationService;

    public LocationControllers( LocationService locationService) {
        this.locationService=locationService;
    }

    /**
     * Save driver location in Redis using GEOADD
     */
    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationDto saveDriverLocationdto) {
        try {
           Boolean response =locationService.saveDriverLocation(saveDriverLocationdto.getDriverId() , saveDriverLocationdto.getLatitude(),saveDriverLocationdto.getLongitude());

            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Find nearby drivers within SEARCH_RADIUS km
     */
    @PostMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDto>> getNearDriver(@RequestBody NearbyDriverRequestDto nearbyDriverRequestDto) {
        try {

            List<DriverLocationDto> drivers = locationService.getNearByDrivers(nearbyDriverRequestDto.getLatitude() , nearbyDriverRequestDto.getLongitude());
            return new ResponseEntity<>(drivers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
