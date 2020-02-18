package com.apap.tu03.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tu03.model.MovieModel;
import com.apap.tu03.service.MovieService;

@Controller
public class MovieController {

	public MovieController() {
		
	}
	
	@Autowired
	private MovieService movieService;
	@RequestMapping("/movie/add")
	public String add(@RequestParam(value = "id",required = true)String id,
			@RequestParam(value = "title",required = true) String title,
			@RequestParam(value = "genre",required = true) String genre,
			@RequestParam(value = "budget",required = true) Long budget,
			@RequestParam(value = "duration",required = true) Integer duration) 
	{
		MovieModel movie = new MovieModel(id,title,genre,budget,duration);
		movieService.addMovie(movie);
		
		return "add";
	}


	@RequestMapping("/movie/view")
	public String view1(@RequestParam("id") String id, Model model) {
	    MovieModel archive = movieService.getMovieDetail(id);
	    model.addAttribute("movie", archive);
	    return "view-movie";
}
	@RequestMapping("/movie/viewall")
	public String viewAll(Model model) {
		List<MovieModel> archive = movieService.getMovieList();
		model.addAttribute("movies", archive);
		return "viewall-movie";
	}

    @RequestMapping(value="movie/view/{id}")
    public String view2(@PathVariable String id, Model model) {
    	MovieModel archive=movieService.getMovieDetail(id);
    	if(archive != null) {
    		model.addAttribute("movie", archive);
    		return "view-movie";
    	}
    	else {
    		return "notfound";
    	}
    }

	@RequestMapping(value = "movie/update/{id}/duration/{time}")
	public String updateDuration(@PathVariable String id, @PathVariable Integer time, Model model) {
		String message;
		if(movieService.getMovieDetail(id) !=null) {
			movieService.getMovieDetail(id).setDuration(time);
			message = "Durasi movie dengan id " + id + " berhasil diupdate!";
		}else {
			message = "Id Movie " + id + " tidak ditemukan";
		}
		model.addAttribute( "message" , message);
		return "pesan";
		}
	
	@RequestMapping(value = "movie/delete/{id}")
	public String delete(@PathVariable String id, Model model) {
		String message;
		if(movieService.getMovieDetail(id) !=null) {
			movieService.getMovieList().remove(movieService.getMovieDetail(id));
			message = "id Movie " + id + " berhasil dihapus!";
		}else {
			message = "Movie dengan id" + id + " tidak ditemukan";
		}
		model.addAttribute( "message" , message);
		return "pesan";
		}
	}