package com.kh.totalapp.service;


import com.kh.totalapp.dto.BoardDTO;
import com.kh.totalapp.entity.Board;
import com.kh.totalapp.entity.Category;
import com.kh.totalapp.entity.Member;
import com.kh.totalapp.repository.BoardRepository;
import com.kh.totalapp.repository.CategoryRepository;
import com.kh.totalapp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
   private final BoardRepository boardRepository;
   private final MemberRepository memberRepository;
   private final CategoryRepository categoryRepository;
   public boolean saveBoard(BoardDTO boardDTO) {
       try {
            Board board = new Board();
            Member member = memberRepository.findByEmail(boardDTO.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            Category category = categoryRepository.findById(boardDTO.getCategoryId()).orElseThrow(
                    () -> new RuntimeException("해당 카테고리가 존재하지 않습니다.")
            );

            board.setTitle(boardDTO.getTitle());
            board.setCategory(category);
            board.setContent(boardDTO.getContent());
            board.setImgPath(boardDTO.getImg());
            board.setMember(member);
            boardRepository.save(board);
            return true;
       } catch (Exception e){
           e.printStackTrace();
           return false;
       }
   }
   // 게시글 전체 조회
   public List<BoardDTO> getBoardList(){
       List<Board> boards = boardRepository.findAll();
       List<BoardDTO> boardDTOS = new ArrayList<>();
       for (Board board : boards){
           boardDTOS.add(convertEntityToDTO(board));
       }
       return  boardDTOS;
   }
   // 게시글 조회
   public BoardDTO getBoardDetail(Long id){
       Board board = boardRepository.findById(id).orElseThrow(
               ()-> new RuntimeException("해당 게시글이 존재하지 않습니다.")
       );
       return convertEntityToDTO(board);
   }
   // 게시글 수정
   public boolean modifyBoard(Long id, BoardDTO boardDTO){
       try {
           Board board = boardRepository.findById(id).orElseThrow(
                   ()-> new RuntimeException("해당 게시글이 존재하지않습니다.")
           );
           board.setTitle(boardDTO.getTitle());
           board.setContent(boardDTO.getContent());
           board.setImgPath(boardDTO.getImg());
           boardRepository.save(board);
           return true;
       } catch (Exception e) {
           e.printStackTrace();
           return false;
       }
   }
    // 게시글 삭제
   public boolean deleteBord(Long id) {
       try {
           boardRepository.deleteById(id);
           return true;
       } catch (Exception e){
           e.printStackTrace();
           return false;
       }
   }
   // 게시글 검색
   public List<BoardDTO> searchBoard(String keyword){
       List<Board> boards = boardRepository.findByTitleContaining(keyword);
       List<BoardDTO> boardDTOS = new ArrayList<>();
       for (Board board : boards) {
           boardDTOS.add(convertEntityToDTO(board));
       }
       return boardDTOS;
   }
   // 게시글 페이징
   public List<BoardDTO> getBoardList(int page, int size){
       Pageable pageable = PageRequest.of(page, size);
       List<Board> boards = boardRepository.findAll(pageable).getContent();
       List<BoardDTO> boardDTOS = new ArrayList<>();
       for (Board board : boards) {
           boardDTOS.add(convertEntityToDTO(board));
       }
       return boardDTOS;
   }
   // 페이지 수 조회
    public int getBoards(Pageable pageable){
       return boardRepository.findAll(pageable).getTotalPages();
    }
   // 게시글 엔티티를 DTO로 변환
   private BoardDTO convertEntityToDTO(Board board) {
       BoardDTO boardDTO = new BoardDTO();
       boardDTO.setBoardId(board.getBoardId());
       boardDTO.setTitle(board.getTitle());
       boardDTO.setContent(board.getContent());
       boardDTO.setImg(board.getImgPath());
       boardDTO.setEmail(board.getMember().getEmail());
       boardDTO.setRegDate(board.getRegDate());
       return boardDTO;
   }
}
