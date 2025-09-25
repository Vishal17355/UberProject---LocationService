package org.example.uberproject_location.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SaveDriverLocationDto {
    String driverId;
    Double latitude ;
    Double longitude;
}
