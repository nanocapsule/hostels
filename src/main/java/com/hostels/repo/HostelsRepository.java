package com.hostels.repo;

import com.hostels.beans.Hostels;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.annotation.Nullable;

import java.util.List;

@Repository
public interface HostelsRepository extends CrudRepository<Hostels, Long> {
    @Query(
        "SELECT h FROM Hostels h WHERE " +
        "(:hostelId IS NULL OR h.hostelId = :hostelId) AND " +
        "(:hostelName IS NULL OR h.hostelName LIKE CONCAT('%', :hostelName, '%')) AND " +
        "(:hostelAddress IS NULL OR h.hostelAddress LIKE CONCAT('%', :hostelAddress, '%'))"
    )
    List<Hostels> findHostelsByOptionalFields(
        @Nullable Long hostelId,
        @Nullable String hostelName,
        @Nullable String hostelAddress
    );
}