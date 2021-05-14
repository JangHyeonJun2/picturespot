package com.sparta.hanghae.picturespot.repository;

import com.sparta.hanghae.picturespot.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByUserIdOrderByModifiedDesc(Long userId);
    Optional<Board> findByIdOrderByModifiedDesc(Long boardId);
    Optional<Board> findById(Long BoardId);
    List<Board> findByTitleContainingOrContentContainingOrderByModifiedDesc(String title, String content);

    List<Board> findAllByOrderByModifiedDesc();

    @EntityGraph(attributePaths = {"comments","comments.user"})
    List<Board> findAllEntityGraphWithUserByIdLessThanOrderByIdDesc(Long lastBoardId, PageRequest request);

    @Query("select a from Board a join fetch a.comments s")
    List<Board> findAllJoinFetch();

//    @Query("select a " + "from Board a "+ "join fetch a.comments ")
//    List<Board> findAllByFetchJoin();
}

