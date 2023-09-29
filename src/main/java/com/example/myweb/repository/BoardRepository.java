package com.example.myweb.repository;

import com.example.myweb.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    void deleteAllByUserId(Long id);
}
