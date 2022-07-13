package com.bjd.demo.dto.station;

import com.bjd.demo.dto.image.ImageDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {
    private Long id;
    private String name;
    private ImageDto image;
}
