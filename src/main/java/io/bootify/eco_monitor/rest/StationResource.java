package io.bootify.eco_monitor.rest;

import io.bootify.eco_monitor.model.StationDTO;
import io.bootify.eco_monitor.service.SensorService;
import io.bootify.eco_monitor.util.ReferencedException;
import io.bootify.eco_monitor.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/sensors", produces = MediaType.APPLICATION_JSON_VALUE)
public class StationResource {

    private final SensorService sensorService;

    public StationResource(final SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<List<StationDTO>> getAllSensors() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDTO> getSensor(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(sensorService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSensor(@RequestBody @Valid final StationDTO sensorDTO) {
        final Long createdId = sensorService.create(sensorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSensor(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final StationDTO sensorDTO) {
        sensorService.update(id, sensorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSensor(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = sensorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        sensorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
