package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKFloor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends CrudRepository<PKFloor, Long> {
}
