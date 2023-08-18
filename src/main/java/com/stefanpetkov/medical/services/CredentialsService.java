package com.stefanpetkov.medical.services;

import com.stefanpetkov.medical.repositories.CredentialsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CredentialsService {

    private final CredentialsRepository credentialsRepository;

}
