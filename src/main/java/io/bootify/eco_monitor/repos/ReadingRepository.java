package io.bootify.eco_monitor.repos;

import io.bootify.eco_monitor.domain.Reading;
import io.bootify.eco_monitor.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReadingRepository extends JpaRepository<Reading, Long> {

    Reading findFirstBySensor(Station sensor);

}
