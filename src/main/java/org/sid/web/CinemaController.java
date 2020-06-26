package org.sid.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sid.dao.CategorieRepository;
import org.sid.dao.CinemaRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.PlaceRepository;
import org.sid.dao.ProjectionRepository;
import org.sid.dao.SalleRepository;
import org.sid.dao.SceanceRepository;
import org.sid.dao.TicketRepository;
import org.sid.dao.VilleRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Cinema;
import org.sid.entities.Film;
import org.sid.entities.Place;
import org.sid.entities.Projection;
import org.sid.entities.Salle;
import org.sid.entities.Sceance;
import org.sid.entities.Ticket;
import org.sid.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class CinemaController {
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private SceanceRepository sceanceRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private TicketRepository ticketRepository;
	

	@GetMapping("/dashboard")
	public String dashboard() {
		
		return "dashboard";
	}
	
	/** FilmController Begins **/

	@GetMapping("/filmManagement")
	public String filmManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		String path="..\\images\\";
		Page<Film> pageFilms= filmRepository.findByTitreContains(mc, PageRequest.of(page,5));
		model.addAttribute("listFilms", pageFilms.getContent());
		model.addAttribute("pages", new int[pageFilms.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		model.addAttribute("path", path);
		return "filmManagement";		
		 
	}

	@GetMapping("/deleteFilm")
	public String deleteFilm(Long id, int page, String motCle){
		filmRepository.deleteById(id);
		return "redirect:/filmManagement?page="+page+"&motCle="+motCle;
	
	}
	


	/** FilmController Ends **/
	/** TicketController Begins **/

	@GetMapping("/ticketManagement")
	public String ticketManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Ticket> pageTickets= ticketRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listTickets", pageTickets.getContent());
		model.addAttribute("pages", new int[pageTickets.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "ticketManagement";
		
	}
	
	@GetMapping("/editTicket")
	public String editTicket(Long id, Model model){
		Ticket ticket = ticketRepository.findById(id).get();
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", ticket);
		return "editTicketForm";
	}
	
	@GetMapping("/deleteTicket")
	public String deleteTicket(Long id, int page, String motCle){
		ticketRepository.deleteById(id);
		return "redirect:/ticketManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formTicket")
	public String formTicket(Model model){
		List<Place> Places= placeRepository.findAll();
		List<Projection> Projections= projectionRepository.findAll();
		model.addAttribute("listPlaces", Places);
		model.addAttribute("listProjections", Projections);
		model.addAttribute("ticket", new Ticket());
		
		return "formTicket";
	}
	
	@PostMapping("/saveTicket")
	public String saveTicket(Model model, @Valid Ticket ticket, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formTicket";
		ticketRepository.save(ticket);
		return "redirect:/ticketManagement";
	}


	/** TicketController Ends **/
	
	/** CinemaController Begins **/

	@GetMapping("/cinemaManagement")
	public String cinemaManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Cinema> pageCinemas= cinemaRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listCinemas", pageCinemas.getContent());
		model.addAttribute("pages", new int[pageCinemas.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "cinemaManagement";
		
	}
	
	@GetMapping("/editCinema")
	public String editCinema(Long id, Model model){
		Cinema cinema = cinemaRepository.findById(id).get();
		List<Ville> Villes= villeRepository.findAll();
		model.addAttribute("listVilles", Villes);
		model.addAttribute("cinema", cinema);
		return "editCinemaForm";
	}
	
	@GetMapping("/deleteCinema")
	public String deleteCinema(Long id, int page, String motCle){
		cinemaRepository.deleteById(id);
		return "redirect:/cinemaManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formCinema")
	public String formCinema(Model model){
		List<Ville> Villes= villeRepository.findAll();
		model.addAttribute("listVilles", Villes);
		model.addAttribute("cinema", new Cinema());
		
		return "formCinema";
	}
	
	@PostMapping("/saveCinema")
	public String saveCinema(Model model, @Valid Cinema cinema, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formCinema";
		cinemaRepository.save(cinema);
		return "redirect:/cinemaManagement";
	}


	/** CinemaController Ends **/
	
	/** PlaceController Begins **/

	@GetMapping("/placeManagement")
	public String placeManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Place> pagePlaces= placeRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listPlaces", pagePlaces.getContent());
		model.addAttribute("pages", new int[pagePlaces.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "placeManagement";
		
	}
	
	@GetMapping("/editPlace")
	public String editPlace(Long id, Model model){
		Place place = placeRepository.findById(id).get();
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", place);
		return "editPlaceForm";
	}
	
	@GetMapping("/deletePlace")
	public String deletePlace(Long id, int page, String motCle){
		placeRepository.deleteById(id);
		return "redirect:/placeManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formPlace")
	public String formPlace(Model model){
		List<Salle> Salles= salleRepository.findAll();
		model.addAttribute("listSalles", Salles);
		model.addAttribute("place", new Place());
		
		return "formPlace";
	}
	
	@PostMapping("/savePlace")
	public String savePlace(Model model, @Valid Place place, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formPlace";
		placeRepository.save(place);
		return "redirect:/placeManagement";
	}


	/** CinemaController Ends **/
	
	/** ProjectionController Begins **/
	@GetMapping("/seanceManagement")
	public String seanceManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Sceance> pageSceances= sceanceRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listSceances", pageSceances.getContent());
		model.addAttribute("pages", new int[ pageSceances.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "seanceManagement";
		
	}
	

	
	/** PorjectionController Begins **/
	@GetMapping("/projectionManagement")
	public String projectionManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page){
		Page<Projection> pageProjections= projectionRepository.findAll(PageRequest.of(page,5));
		model.addAttribute("listProjections", pageProjections.getContent());
		model.addAttribute("pages", new int[ pageProjections.getTotalPages()]);
		model.addAttribute("currentPage", page);
		return "projectionManagement";
		
	}
	@GetMapping("/editProjection")
	public String editProjection(Long id, Model model){
		Projection projection = projectionRepository.findById(id).get();
		List<Film> film= filmRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("listFilms", film);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		model.addAttribute("projection", projection);
		return "editProjectionForm";
	}
	
	@GetMapping("/deleteProjection")
	public String deleteProjection(Long id, int page, String motCle){
		projectionRepository.deleteById(id);
		return "redirect:/projectionManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formProjection")
	public String formProjectio(Model model){
		List<Film> film= filmRepository.findAll();
		List<Salle> salle= salleRepository.findAll();
		List<Sceance> sceance= sceanceRepository.findAll();
		model.addAttribute("projection", new Projection());
		model.addAttribute("listFilms", film);
		model.addAttribute("listSalles", salle);
		model.addAttribute("listSceances", sceance);
		return "formProjection";
	}
	@PostMapping("/saveEditProjection")
	public String saveEditProjection(Model model, @Valid Projection projection, BindingResult bindingResult) throws ParseException{
		if(bindingResult.hasErrors()) return "editProjectionForm";
		
		@SuppressWarnings("deprecation")
		Date parsed = new Date(Integer.parseInt(projection.getSdateProjection().split("-")[0])-1900,Integer.parseInt(projection.getSdateProjection().split("-")[1])
				,Integer.parseInt(projection.getSdateProjection().split("-")[2]));

		projection.setDateProjection(parsed);
		projectionRepository.save(projection);
		return "redirect:/projectionManagement";
	}

	
	@PostMapping("/saveProjection")
	public String saveProjection(Model model, @Valid Projection projection, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formProjection";
		projectionRepository.save(projection);
		return "redirect:/projectionManagement";
	}
	/** ProjectionController Ends **/
	
	/** VilleController Begins **/
	@GetMapping("/villeManagement")
	public String villeManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Ville> pageVilles= villeRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listVilles", pageVilles.getContent());
		model.addAttribute("pages", new int[pageVilles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "villeManagement";
		
	}
	@GetMapping("/editVille")
	public String editVille(Long id, Model model){
		Ville ville = villeRepository.findById(id).get();
		model.addAttribute("ville", ville);
		return "editVilleForm";
	}
	
	@GetMapping("/deleteVille")
	public String deleteVille(Long id, int page, String motCle){
		villeRepository.deleteById(id);
		return "redirect:/villeManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formVille")
	public String formVille(Model model){
		model.addAttribute("ville", new Ville());
		return "formVille";
	}
	
	@PostMapping("/saveVille")
	public String saveVille(Model model, @Valid Ville ville, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formVille";
		villeRepository.save(ville);
		return "redirect:/villeManagement";
	}
	@PostMapping("/saveEditVille")
	public String saveEditVille(Model model, @Valid Ville ville, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "editVilleForm";
		villeRepository.save(ville);
		return "redirect:/villeManagement";
	}

	/** VilleController Ends **/
	
	/** CategorieController Begins **/
	@GetMapping("/categorieManagement")
	public String categorieManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Categorie> pageCategories= categorieRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listCats", pageCategories.getContent());
		model.addAttribute("pages", new int[pageCategories.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "categorieManagement";
		
	}
	@GetMapping("/editCategorie")
	public String editCategorie(Long id, Model model){
		Categorie categorie = categorieRepository.findById(id).get();
		model.addAttribute("categorie", categorie);
		return "editCategorieForm";
	}
	
	@GetMapping("/deleteCategorie")
	public String deleteCategorie(Long id, int page, String motCle){
		categorieRepository.deleteById(id);
		return "redirect:/categorieManagement?page="+page+"&motCle="+motCle;
	}
	@GetMapping("/formCategorie")
	public String formCategorie(Model model){
		model.addAttribute("categorie", new Categorie());
		return "formCategorie";
	}
	
	@PostMapping("/saveCategorie")
	public String saveCategorie(Model model, @Valid Categorie categorie, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formVille";
		categorieRepository.save(categorie);
		return "redirect:/categorieManagement";
	}

	/** CategorieController Ends **/

	/** SalleController Begins **/

	@GetMapping("/salleManagement")
	public String salleManagement(Model model,
			@RequestParam(name="page", defaultValue="0") int page,
			@RequestParam(name="motCle", defaultValue="") String  mc){
		Page<Salle> pageSalles= salleRepository.findByNameContains(mc, PageRequest.of(page,5));
		model.addAttribute("listSalles", pageSalles.getContent());
		model.addAttribute("pages", new int[pageSalles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		return "salleManagement";
		
	}
	
	@GetMapping("/editSalle")
	public String editSalle(Long id, Model model){
		Salle salle = salleRepository.findById(id).get();
		List<Cinema> Cinema= cinemaRepository.findAll();
		model.addAttribute("listCinemas", Cinema);
		model.addAttribute("salle", salle);
		return "editSalleForm";
	}
	
	@GetMapping("/deleteSalle")
	public String deleteSalle(Long id, int page, String motCle){
		salleRepository.deleteById(id);
		return "redirect:/salleManagement?page="+page+"&motCle="+motCle;
	}
	
	@GetMapping("/formSalle")
	public String formSalle(Model model){
		List<Cinema> Cinemas= cinemaRepository.findAll();
		model.addAttribute("listCinemas", Cinemas);
		model.addAttribute("salle", new Salle());
		
		return "formSalle";
	}
	
	@PostMapping("/saveSalle")
	public String saveSalle(Model model, @Valid Salle salle, BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formSalle";
		salleRepository.save(salle);
		return "redirect:/salleManagement";
	}


	/** SalleController Ends **/

}
