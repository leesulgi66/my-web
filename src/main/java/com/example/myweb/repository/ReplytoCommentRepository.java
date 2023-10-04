package com.example.myweb.repository;

import com.example.myweb.model.ReplyToComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplytoCommentRepository extends JpaRepository<ReplyToComment, Long> {
    void deleteAllByUserId(Long id);
}
