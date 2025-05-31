package io.bootify.eco_monitor.repos;

import io.bootify.eco_monitor.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StationRepository extends JpaRepository<Station, Long> {
}
