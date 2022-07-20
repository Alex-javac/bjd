package com.bjd.demo.service.train;

import com.bjd.demo.dto.train.TrainDto;
import com.bjd.demo.entity.TrainEntity;
import com.bjd.demo.entity.TrainType;

import java.util.Optional;

public interface TrainService {

    Optional<TrainEntity> findByNumberAndType(String number, TrainType type);

    TrainDto save(TrainDto trainDto);
}
