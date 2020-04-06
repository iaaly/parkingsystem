package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKFloorSlots;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorSlotsRepository extends CrudRepository<PKFloorSlots, Long> {
}
