package io.bootify.eco_monitor.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StationDTO {

    private Long id;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String type;

    @Size(max = 50)
    private String geoCoord;

    private LocalDateTime instalationDate;

    @Size(max = 50)
    private String status;

}
