package com.bjd.demo.service.train;

import com.bjd.demo.dto.train.TrainDto;
import com.bjd.demo.entity.TrainEntity;
import com.bjd.demo.entity.TrainType;
import com.bjd.demo.mapper.TrainMapper;
import com.bjd.demo.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;
    private final TrainMapper trainMapper;

    @Override
    public Optional<TrainEntity> findByNumberAndType(String number, TrainType type) {
        if (nonNull(number) && nonNull(type)) {
            return trainRepository.findByNumberAndType(number, type);
        }
        throw new IllegalArgumentException("TrainServiceImpl.findByNumberAndType() parameters can`t be null");
    }

    @Override
    @Transactional
    public TrainDto save(TrainDto trainDto) {
        if (nonNull(trainDto)) {
            TrainEntity trainEntity = trainMapper.mapTrainDtoToEntity(trainDto);
            return trainMapper.mapTrainEntityToDto(trainRepository.save(trainEntity));
        }
        throw new IllegalArgumentException("TrainServiceImpl.save() trainDto can`t be null");

    }
}
