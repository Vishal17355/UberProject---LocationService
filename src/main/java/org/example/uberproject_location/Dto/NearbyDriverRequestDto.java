package org.example.uberproject_location.Dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearbyDriverRequestDto {

    Double latitude;

    Double longitude;
}
