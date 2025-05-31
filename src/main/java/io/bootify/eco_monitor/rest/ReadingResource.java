package io.bootify.eco_monitor.rest;

import io.bootify.eco_monitor.model.ReadingDTO;
import io.bootify.eco_monitor.service.ReadingService;
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
@RequestMapping(value = "/api/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReadingResource {

    private final ReadingService readingService;

    public ReadingResource(final ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping
    public ResponseEntity<List<ReadingDTO>> getAllAlerts() {
        return ResponseEntity.ok(readingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadingDTO> getAlert(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(readingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAlert(@RequestBody @Valid final ReadingDTO readingDTO) {
        final Long createdId = readingService.create(readingDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAlert(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ReadingDTO readingDTO) {
        readingService.update(id, readingDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAlert(@PathVariable(name = "id") final Long id) {
        readingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
