package com.game.service;

import com.game.entity.Round;
import com.game.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoundService {
    RoundRepository roundRepository;

    @Autowired
    public RoundService(RoundRepository roundRepository) {
        this.roundRepository = roundRepository;
    }

    public List<Round> findAll() {
        return roundRepository.findAll();
    }

    public Page<Round> findAllByUserId(Long id, Pageable pageable) { return roundRepository.findAllByUserId(id, pageable); }
}
