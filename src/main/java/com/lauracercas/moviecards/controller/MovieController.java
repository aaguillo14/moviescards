package com.lauracercas.moviecards.controller;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.model.Movie;
import com.lauracercas.moviecards.service.movie.MovieService;
import com.lauracercas.moviecards.util.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
@Controller
public class MovieController {

    private static final String ACTORS = "actors";
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String MOVIE = "movie";
    private static final String MOVIES = "movies";
    private static final String MOVIES_FORM = "movies/form";
    private static final String MOVIES_LIST = "movies/list";

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("movies")
    public String getMoviesList(Model model) {
        model.addAttribute(MOVIES, movieService.getAllMovies());
        return MOVIES_LIST;
    }

    @GetMapping("movies/new")
    public String newMovie(Model model) {
        model.addAttribute(MOVIE, new Movie());
        model.addAttribute(TITLE, Messages.NEW_MOVIE_TITLE);
        return MOVIES_FORM;
    }

    @PostMapping("saveMovie")
    public String saveMovie(@ModelAttribute Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return MOVIES_FORM;
        }
        Movie movieSaved = movieService.save(movie);
        if (movieSaved.getId() != null) {
            model.addAttribute(MESSAGE, Messages.UPDATED_MOVIE_SUCCESS);
        } else {
            model.addAttribute(MESSAGE, Messages.SAVED_MOVIE_SUCCESS);
        }

        model.addAttribute(MOVIE, movieSaved);
        model.addAttribute(TITLE, Messages.EDIT_MOVIE_TITLE);
        return MOVIES_FORM;
    }

    @GetMapping("editMovie/{movieId}")
    public String editMovie(@PathVariable Integer movieId, Model model) {
        Movie movie = movieService.getMovieById(movieId);
        List<Actor> actors = movie.getActors();
        model.addAttribute(MOVIE, movie);
        model.addAttribute(ACTORS, actors);

        model.addAttribute(TITLE, Messages.EDIT_MOVIE_TITLE);

        return MOVIES_FORM;
    }


}
