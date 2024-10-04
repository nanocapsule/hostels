package com.hostels.services;

import com.hostels.beans.Hostels;
import com.hostels.repo.HostelsRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class HostelsService {

    private final HostelsRepository hostelsRepository;

    @Transactional
    public Hostels save(Hostels hostel) {
        return hostelsRepository.save(hostel);
    }
}
