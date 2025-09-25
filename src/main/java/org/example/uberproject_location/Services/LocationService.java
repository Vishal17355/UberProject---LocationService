package org.example.uberproject_location.Services;

import org.example.uberproject_location.Dto.DriverLocationDto;

import java.util.List;

public interface LocationService {
    Boolean saveDriverLocation(String DriverId , Double latitude , Double longitude);
    List<DriverLocationDto> getNearByDrivers(Double latitude , Double longitude);

}
