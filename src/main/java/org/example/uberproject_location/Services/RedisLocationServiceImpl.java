package org.example.uberproject_location.Services;

import org.example.uberproject_location.Dto.DriverLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service

public class RedisLocationServiceImpl implements LocationService {
    private static final Double SEARCH_RADIUS = 100.0; // in kilometers
    private static final String DRIVER_GEO_OPS_KEY = "drivers";

    private final StringRedisTemplate stringRedisTemplate;

    public RedisLocationServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public Boolean saveDriverLocation(String DriverId, Double latitude, Double longitude) {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();

        // Always pass longitude first, latitude second
        geoOps.add(
                DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(
                        DriverId,
                        new Point(
                                latitude,  // x = longitude
                                longitude  // y = latitude
                        )
                )
        );
        return true;
    }


    @Override
    public List<DriverLocationDto> getNearByDrivers(Double latitude, Double longitude) {
        GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
        Distance radius = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);

        // Correct order: longitude first, latitude second
        Circle within = new Circle(new Point(longitude, latitude), radius);

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, within);
        List<DriverLocationDto> drivers = new ArrayList<>();

        if (results != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                List<Point> points = geoOps.position(DRIVER_GEO_OPS_KEY, result.getContent().getName());
                if (points != null && !points.isEmpty()) {
                    Point point = points.get(0); // first element safely
                    DriverLocationDto driverLocationDto = DriverLocationDto.builder()
                            .DriverId(result.getContent().getName())
                            .latitude(point.getY())    // latitude = Y
                            .longitude(point.getX())   // longitude = X
                            .build();
                    drivers.add(driverLocationDto);
                }
            }
        }

        return drivers;
    }
}