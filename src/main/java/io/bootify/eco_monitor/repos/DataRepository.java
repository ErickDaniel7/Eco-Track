package io.bootify.eco_monitor.repos;

import io.bootify.eco_monitor.domain.Data;
import io.bootify.eco_monitor.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DataRepository extends JpaRepository<Data, Long> {

    Data findFirstBySensor(Station sensor);

}
