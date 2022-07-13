package com.bjd.demo.dto.train;

import com.bjd.demo.entity.TrainType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainDto {
    private Long id;
    private String number;
    private TrainType type;
}
