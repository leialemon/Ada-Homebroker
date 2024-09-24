package tech.ada.jjh.homebroker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.jjh.homebroker.model.Broker;

@Repository
public interface BrokerRepository extends JpaRepository<Broker, Long> {
}