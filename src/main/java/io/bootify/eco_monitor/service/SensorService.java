package io.bootify.eco_monitor.service;

import io.bootify.eco_monitor.domain.Reading;
import io.bootify.eco_monitor.domain.Data;
import io.bootify.eco_monitor.domain.Station;
import io.bootify.eco_monitor.model.StationDTO;
import io.bootify.eco_monitor.repos.ReadingRepository;
import io.bootify.eco_monitor.repos.DataRepository;
import io.bootify.eco_monitor.repos.StationRepository;
import io.bootify.eco_monitor.util.NotFoundException;
import io.bootify.eco_monitor.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SensorService {

    private final StationRepository stationRepository;
    private final DataRepository dataRepository;
    private final ReadingRepository readingRepository;

    public SensorService(final StationRepository stationRepository,
            final DataRepository dataRepository, final ReadingRepository readingRepository) {
        this.stationRepository = stationRepository;
        this.dataRepository = dataRepository;
        this.readingRepository = readingRepository;
    }

    public List<StationDTO> findAll() {
        final List<Station> sensors = stationRepository.findAll(Sort.by("id"));
        return sensors.stream()
                .map(sensor -> mapToDTO(sensor, new StationDTO()))
                .toList();
    }

    public StationDTO get(final Long id) {
        return stationRepository.findById(id)
                .map(sensor -> mapToDTO(sensor, new StationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StationDTO sensorDTO) {
        final Station sensor = new Station();
        mapToEntity(sensorDTO, sensor);
        return stationRepository.save(sensor).getId();
    }

    public void update(final Long id, final StationDTO sensorDTO) {
        final Station sensor = stationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sensorDTO, sensor);
        stationRepository.save(sensor);
    }

    public void delete(final Long id) {
        stationRepository.deleteById(id);
    }

    private StationDTO mapToDTO(final Station sensor, final StationDTO sensorDTO) {
        sensorDTO.setId(sensor.getId());
        sensorDTO.setName(sensor.getName());
        sensorDTO.setType(sensor.getType());
        sensorDTO.setGeoCoord(sensor.getGeoCoord());
        sensorDTO.setInstalationDate(sensor.getInstalationDate());
        sensorDTO.setStatus(sensor.getStatus());
        return sensorDTO;
    }

    private Station mapToEntity(final StationDTO sensorDTO, final Station sensor) {
        sensor.setName(sensorDTO.getName());
        sensor.setType(sensorDTO.getType());
        sensor.setGeoCoord(sensorDTO.getGeoCoord());
        sensor.setInstalationDate(sensorDTO.getInstalationDate());
        sensor.setStatus(sensorDTO.getStatus());
        return sensor;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Station sensor = stationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Data sensorData = dataRepository.findFirstBySensor(sensor);
        if (sensorData != null) {
            referencedWarning.setKey("sensor.data.sensor.referenced");
            referencedWarning.addParam(sensorData.getId());
            return referencedWarning;
        }
        final Reading sensorAlert = readingRepository.findFirstBySensor(sensor);
        if (sensorAlert != null) {
            referencedWarning.setKey("sensor.alert.sensor.referenced");
            referencedWarning.addParam(sensorAlert.getId());
            return referencedWarning;
        }
        return null;
    }

}
