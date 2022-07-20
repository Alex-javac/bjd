package com.bjd.demo.dto.train;

import com.bjd.demo.entity.TrainType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrainDto {
    private Long id;
    private String number;
    private TrainType type;

    public TrainDto(String number, TrainType type) {
        this.number = number;
        this.type = type;
    }
}
