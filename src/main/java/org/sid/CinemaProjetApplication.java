package org.sid;


import org.sid.entities.Film;
import org.sid.entities.Salle;
import org.sid.entities.Ticket;
import org.sid.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaProjetApplication implements CommandLineRunner {
	@Autowired private ICinemaInitService cinemaInitService;
	@Autowired private RepositoryRestConfiguration restConfiguration;
	public static void main(String[] args) {
	SpringApplication.run(CinemaProjetApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
	restConfiguration.exposeIdsFor(Film.class, Salle.class, Ticket.class);
	cinemaInitService.initVilles(); 
	cinemaInitService.initCinemas();
	cinemaInitService.initSalles(); 
	cinemaInitService.initPlaces();
	cinemaInitService.initSceances(); 
	cinemaInitService.initCategories();
	cinemaInitService.initFilms();
	cinemaInitService.initProjections();
	cinemaInitService.initTickets();
	}


}