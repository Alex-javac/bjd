package com.bjd.demo.dto.image;

import com.bjd.demo.dto.station.StationDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long id;
    private String name;
    private String type;
    private byte[] image;
    private StationDto station;
}
