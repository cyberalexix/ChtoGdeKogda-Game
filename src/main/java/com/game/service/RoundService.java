package com.game.service;

import com.game.entity.Round;
import com.game.repository.RoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Round> findByFinishedStatus(Pageable pageable) {
        return roundRepository.findByIsFinishedAndGivenAnswer(pageable);
    }

    public Optional<Round> findById(Long id) {
        return roundRepository.findById(id);
    }

    public Optional<Round> findLastUserRound(Long id) {
        return roundRepository.findByUserIdAndIsFinished(id);
    }

    @Transactional
    public void updateAnswer(Long id, String answer) {
        roundRepository.updateRound(id, answer);
    }

    @Transactional
    public void updateHint(Long id) {
        roundRepository.updateRound(id);
    }

    @Transactional
    public void finalUpdate(Long id, int score, boolean isFinished, boolean isWon) {
        roundRepository.updateRound(id, score, isFinished, isWon);
    }

    public void save(Round round) {
        roundRepository.save(round);
    }

    public Page<Round> findAllByUserId(Long id, Pageable pageable) { return roundRepository.findAllByUserId(id, pageable); }
}
