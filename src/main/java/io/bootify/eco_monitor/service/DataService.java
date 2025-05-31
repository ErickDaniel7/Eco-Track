package io.bootify.eco_monitor.service;

import io.bootify.eco_monitor.domain.Data;
import io.bootify.eco_monitor.domain.Station;
import io.bootify.eco_monitor.model.DataDTO;
import io.bootify.eco_monitor.repos.DataRepository;
import io.bootify.eco_monitor.repos.StationRepository;
import io.bootify.eco_monitor.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DataService {

    private final DataRepository dataRepository;
    private final StationRepository stationRepository;

    public DataService(final DataRepository dataRepository,
            final StationRepository stationRepository) {
        this.dataRepository = dataRepository;
        this.stationRepository = stationRepository;
    }

    public List<DataDTO> findAll() {
        final List<Data> datas = dataRepository.findAll(Sort.by("id"));
        return datas.stream()
                .map(data -> mapToDTO(data, new DataDTO()))
                .toList();
    }

    public DataDTO get(final Long id) {
        return dataRepository.findById(id)
                .map(data -> mapToDTO(data, new DataDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DataDTO dataDTO) {
        final Data data = new Data();
        mapToEntity(dataDTO, data);
        return dataRepository.save(data).getId();
    }

    public void update(final Long id, final DataDTO dataDTO) {
        final Data data = dataRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dataDTO, data);
        dataRepository.save(data);
    }

    public void delete(final Long id) {
        dataRepository.deleteById(id);
    }

    private DataDTO mapToDTO(final Data data, final DataDTO dataDTO) {
        dataDTO.setId(data.getId());
        dataDTO.setTemperature(data.getTemperature());
        dataDTO.setHumidity(data.getHumidity());
        dataDTO.setWindSpeed(data.getWindSpeed());
        dataDTO.setParameter(data.getParameter());
        dataDTO.setSensor(data.getSensor() == null ? null : data.getSensor().getId());
        return dataDTO;
    }

    private Data mapToEntity(final DataDTO dataDTO, final Data data) {
        data.setTemperature(dataDTO.getTemperature());
        data.setHumidity(dataDTO.getHumidity());
        data.setWindSpeed(dataDTO.getWindSpeed());
        data.setParameter(dataDTO.getParameter());
        final Station sensor = dataDTO.getSensor() == null ? null : stationRepository.findById(dataDTO.getSensor())
                .orElseThrow(() -> new NotFoundException("sensor not found"));
        data.setSensor(sensor);
        return data;
    }

}
