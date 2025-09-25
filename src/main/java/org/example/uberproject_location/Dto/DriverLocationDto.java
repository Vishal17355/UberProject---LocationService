package org.example.uberproject_location.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDto {
    String DriverId;
     Double latitude;
     Double longitude;
}
