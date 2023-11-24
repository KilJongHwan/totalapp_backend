package com.kh.totalapp.service;


import com.kh.totalapp.dto.MovieDTO;
import com.kh.totalapp.entity.Movie;
import com.kh.totalapp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    // 영화 저장
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }
    // 영화 전체 삭제
    public void deleteAll() {
        movieRepository.deleteAll();
    }
    // 영화 전체 조회
    public List<MovieDTO> getMovieList() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDTO> movieDtos = new ArrayList<>();
        for(Movie movie : movies) {
            movieDtos.add(convertEntityToDto(movie));
        }
        return movieDtos;
    }
    // 페이지네이션
    public List<MovieDTO> getMovieList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Movie> movies = movieRepository.findAll(pageable).getContent();
        List<MovieDTO> movieDtos = new ArrayList<>();
        for(Movie movie : movies) {
            movieDtos.add(convertEntityToDto(movie));
        }
        return movieDtos;
    }
    // 페이지 수 조회
    public int getMoviePage(Pageable pageable) {
        return movieRepository.findAll(pageable).getTotalPages();
    }

    // DTO 변환
    private MovieDTO convertEntityToDto(Movie movie) {
        MovieDTO movieDto = new MovieDTO();
        movieDto.setRank(movie.getMovieRank());
        movieDto.setImage(movie.getImage());
        movieDto.setTitle(movie.getTitle());
        movieDto.setScore(movie.getScore());
        movieDto.setRate(movie.getRate());
        movieDto.setReservation(movie.getReservation());
        movieDto.setDate(movie.getDate());
        return movieDto;
    }
}
