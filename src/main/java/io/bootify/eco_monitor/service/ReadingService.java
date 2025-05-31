package io.bootify.eco_monitor.service;

import io.bootify.eco_monitor.domain.Reading;
import io.bootify.eco_monitor.domain.Station;
import io.bootify.eco_monitor.model.ReadingDTO;
import io.bootify.eco_monitor.repos.ReadingRepository;
import io.bootify.eco_monitor.repos.StationRepository;
import io.bootify.eco_monitor.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final StationRepository stationRepository;

    public ReadingService(final ReadingRepository readingRepository,
                          final StationRepository stationRepository) {
        this.readingRepository = readingRepository;
        this.stationRepository = stationRepository;
    }

    public List<ReadingDTO> findAll() {
        final List<Reading> alerts = readingRepository.findAll(Sort.by("id"));
        return alerts.stream()
                .map(alert -> mapToDTO(alert, new ReadingDTO()))
                .toList();
    }

    public ReadingDTO get(final Long id) {
        return readingRepository.findById(id)
                .map(alert -> mapToDTO(alert, new ReadingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ReadingDTO readingDTO) {
        final Reading alert = new Reading();
        mapToEntity(readingDTO, alert);
        return readingRepository.save(alert).getId();
    }

    public void update(final Long id, final ReadingDTO readingDTO) {
        final Reading alert = readingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(readingDTO, alert);
        readingRepository.save(alert);
    }

    public void delete(final Long id) {
        readingRepository.deleteById(id);
    }

    private ReadingDTO mapToDTO(final Reading alert, final ReadingDTO readingDTO) {
        readingDTO.setId(alert.getId());
        readingDTO.setEvent(alert.getEvent());
        readingDTO.setTime(alert.getTime());
        readingDTO.setSensor(alert.getSensor() == null ? null : alert.getSensor().getId());
        return readingDTO;
    }

    private Reading mapToEntity(final ReadingDTO readingDTO, final Reading alert) {
        alert.setEvent(readingDTO.getEvent());
        alert.setTime(readingDTO.getTime());
        final Station sensor = readingDTO.getSensor() == null ? null : stationRepository.findById(readingDTO.getSensor())
                .orElseThrow(() -> new NotFoundException("sensor not found"));
        alert.setSensor(sensor);
        return alert;
    }

}
