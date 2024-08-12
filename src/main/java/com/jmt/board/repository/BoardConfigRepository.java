package com.jmt.board.repository;

import com.jmt.board.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardConfigRepository extends JpaRepository<Board, String>, QuerydslPredicateExecutor<Board> {
}
