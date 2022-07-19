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
}
