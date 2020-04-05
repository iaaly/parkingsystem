package com.iaaly.parkingsystem.respository;

import com.iaaly.parkingsystem.entity.PKTicket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<PKTicket, Long> {
}
