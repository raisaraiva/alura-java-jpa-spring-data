package com.exemple.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.exemple.orm.Cargo;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Integer> {
}
