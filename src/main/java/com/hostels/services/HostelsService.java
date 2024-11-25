package com.hostels.services;

import com.hostels.beans.Hostels;
import com.hostels.repo.HostelsRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class HostelsService {

    private final HostelsRepository hostelsRepository;

    @Transactional
    public Hostels save(Hostels hostel) {
        return hostelsRepository.save(hostel);
    }

    public List<Hostels> searchHostels(
        Long hostelId,
        String hostelName,
        String hostelAddress
    ) {
        return hostelsRepository.findHostelsByOptionalFields(hostelId, hostelName, hostelAddress);
    }

    public void deleteById(Long hostelId) {
        hostelsRepository.deleteById(hostelId);
    }
}
