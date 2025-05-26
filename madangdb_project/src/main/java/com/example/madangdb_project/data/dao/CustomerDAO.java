package com.example.madangdb_project.data.dao;

import com.example.madangdb_project.data.repository.CustomerEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDAO {
    private final CustomerEntityRepository customerEntityRepository;
}
