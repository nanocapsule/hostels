package com.hostels.repo;

import com.hostels.beans.Hostels;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface HostelsRepository extends CrudRepository<Hostels, Long> {
}