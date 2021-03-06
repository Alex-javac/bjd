package com.bjd.demo.service.route;

import com.bjd.demo.dto.route.CreateRouteDto;
import com.bjd.demo.dto.route.FindRouteDto;
import com.bjd.demo.dto.route.RouteDto;
import com.bjd.demo.dto.train.TrainDto;
import com.bjd.demo.entity.RouteEntity;
import com.bjd.demo.entity.StationEntity;
import com.bjd.demo.entity.TrainEntity;
import com.bjd.demo.mapper.RouteMapper;
import com.bjd.demo.mapper.StationMapper;
import com.bjd.demo.mapper.TrainMapper;
import com.bjd.demo.repository.RouteRepository;
import com.bjd.demo.service.station.StationService;
import com.bjd.demo.service.train.TrainService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.bjd.demo.util.UtilConst.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final TrainService trainService;

    private final TrainMapper trainMapper;
    private final StationService stationService;
    private final StationMapper stationMapper;

    @Override
    public List<RouteDto> find(FindRouteDto findRouteDto) {
        if (nonNull(findRouteDto)) {
            List<RouteEntity> routeEntityList;
            if (nonNull(findRouteDto.getDepartureTime())) {
                routeEntityList = routeRepository.
                        findAllByDepartureStationNameAndArrivalStationNameAndDepartureTime(
                                findRouteDto.getNameDepartureStation(),
                                findRouteDto.getNameArrivalStation(),
                                findRouteDto.getDepartureTime()
                        );
            } else {
                routeEntityList = routeRepository.findAllByDepartureStationNameAndArrivalStationName(
                        findRouteDto.getNameDepartureStation(),
                        findRouteDto.getNameArrivalStation()
                );
            }
            return routeMapper.mapRouteEntityListToDtoList(routeEntityList);
        }
        throw new IllegalArgumentException("RouteServiceImpl.find() findRouteDto can`t be null");
    }

    @Override
    public List<RouteDto> findAll() {
        List<RouteEntity> routeEntityList = routeRepository.findAll();
        return routeMapper.mapRouteEntityListToDtoList(routeEntityList);
    }

    @Override
    public RouteDto findById(Long ticketId) {
        if (nonNull(ticketId)) {
            RouteEntity routeEntity = routeRepository.findById(ticketId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "RouteServiceImpl.findById() Route not found"));
            return routeMapper.mapRouteEntityToDto(routeEntity);
        }
        throw new IllegalArgumentException("RouteServiceImpl.findById() ticketId can`t be null");
    }

    @Override
    @Transactional
    public RouteDto create(CreateRouteDto createRouteDto) {
        if (nonNull(createRouteDto)) {
            TrainEntity trainEntity;
            Optional<TrainEntity> optionalTrain = trainService.findByNumberAndType(
                    createRouteDto.getTrainNumber(), createRouteDto.getType());
            if (optionalTrain.isPresent()) {
                trainEntity = optionalTrain.get();
            } else {
                TrainDto trainDto = trainService.save(
                        new TrainDto(createRouteDto.getTrainNumber(), createRouteDto.getType()));
                trainEntity = trainMapper.mapTrainDtoToEntity(trainDto);
            }
            StationEntity departureStationEntity = stationMapper.mapStationDtoToEntity(
                    stationService.findByName(createRouteDto.getDepartureStation()));
            StationEntity arrivalStationEntity = stationMapper.mapStationDtoToEntity(
                    stationService.findByName(createRouteDto.getArrivalStation()));
            RouteEntity routeEntity = RouteEntity.builder()
                    .departureStation(departureStationEntity)
                    .departureTime(createRouteDto.getDepartureTime())
                    .arrivalStation(arrivalStationEntity)
                    .arrivalTime(createRouteDto.getArrivalTime())
                    .train(trainEntity)
                    .price(createRouteDto.getPrice())
                    .build();
            RouteEntity savedRoute = routeRepository.save(routeEntity);
            return routeMapper.mapRouteEntityToDto(savedRoute);
        }
        throw new IllegalArgumentException("RouteServiceImpl.create() createRouteDto can`t be null");
    }

    @Override
    public void reloadFileCsv() {
        deleteFile("csv");
        try {
            prepareFileCsv();
        } catch (IOException e) {
            log.error("loadFile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reloadFilePdf() {
        deleteFile("pdf");
        try {
            prepareFilePdf();
        } catch (IOException e) {
            log.error("loadFile", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String[]> prepareRows() {
        return getRows(findAll());
    }

    @Override
    @Transactional
    public void delete(Long routeId) {
        routeRepository.deleteById(routeId);
    }

    private void deleteFile(String fileExtension) {
        File file = new File(DEFAULT_FILE_PATH, "schedule." + fileExtension);
        try {
            Files.deleteIfExists(file.toPath());
            log.info("file " + file.getName() + " has been deleted");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("file " + file.getName() + " hasn't been deleted!!!");
        }
    }

    private void prepareFileCsv() throws IOException {
        List<RouteDto> routeDtoList = findAll();
        if (isNull(routeDtoList) || routeDtoList.isEmpty()) {
            log.info("no data at the moment...");
            return;
        }
        File file = new File(DEFAULT_FILE_PATH, "schedule.csv");
        file.getParentFile().mkdirs();

        if (file.getParentFile().exists() && file.createNewFile()) {
            Path path = Paths.get(file.getName());
            try (CSVWriter writer = new CSVWriter(
                    new OutputStreamWriter(
                            Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8),
                    CSV_SEPARATOR, CSV_QUOTE_CHAR, CSV_ESCAPE_CHAR, CSVWriter.DEFAULT_LINE_END)
            ) {
                List<String[]> rows = getRows(routeDtoList);
                writer.writeAll(rows);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void prepareFilePdf() throws IOException {
        List<RouteDto> routeDtoList = findAll();
        File file = new File(DEFAULT_FILE_PATH, "schedule.pdf");
        file.getParentFile().mkdirs();
        if (file.getParentFile().exists() && file.createNewFile()) {

            Document document;


            try {
                document = new Document(PageSize.A4, 5, 5, 10, 5);
                document.addAuthor("AUTHOR");
                PdfWriter writer = PdfWriter.getInstance(document,
                        Files.newOutputStream(file.toPath()));
                BaseFont basefont = BaseFont.createFont("C:/Windows/Fonts/ARIAL.TTF",
                        BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                Font font = new Font(basefont, 12);

                document.open();
                document.add(new Paragraph(" ????????????????????", new Font(basefont, 16)));
                document.add(new Paragraph(" "));
                PdfPTable table = new PdfPTable(5);

                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                List<String[]> rows = getRows(routeDtoList);
                for (String[] row : rows) {
                    for (String s : row) {
                        table.addCell(new Phrase(s, font));
                    }
                }

                document.add(table);
                document.close();
                writer.close();
            } catch (Exception e) {
                log.error("Error in pdf creation " + file, e);
            }
        }
    }


    private List<String[]> getRows(List<RouteDto> routeDtoList) {
        List<String[]> rows = new ArrayList<>();
        log.info("------------------------preparing file...-----------------------");

        String[] header = new String[]{"?????????? ", "?????????????????????? ", "???????????????? ", "?????? ", "?????????????????? "};
        log.info(Arrays.toString(header));
        rows.add(header);
        for (RouteDto routeDto : routeDtoList) {
            String train = routeDto.getTrain().getNumber();
            String departure = routeDto.getDepartureTime() + " " + routeDto.getDepartureStation().getName() + " ";
            String arrival = routeDto.getArrivalTime() + " " + routeDto.getArrivalStation().getName() + " ";
            String type = routeDto.getTrain().getType().toString() + " ";
            String cost = routeDto.getPrice().toString() + " ??????.";
            String[] row = new String[]{train, departure, arrival, type, cost};
            log.info(Arrays.toString(row));
            rows.add(row);
        }
        log.info("-------------------------------------------------------------------------------");
        return rows;
    }
}

