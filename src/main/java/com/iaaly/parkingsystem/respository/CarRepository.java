package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKCar;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<PKCar, Long> {
}
