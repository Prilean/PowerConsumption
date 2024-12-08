package net.codejava.controller;

import org.springframework.stereotype.Controller;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.codejava.model.User;
import net.codejava.repository.UserRepository;

import net.codejava.service.PowerConsumptionService;
import net.codejava.service.UserDetailsServiceImpl;
import net.codejava.model.MyUserDetails;
import net.codejava.model.PowerConsumption;

@Controller
public class WebController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailsServiceImpl udi;
	
	@Autowired 
	private PowerConsumptionService pcs;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup(Model page) {
		User user = new User();
		user.setEnabled(true);
		page.addAttribute("User", user);
		return "signup";
	}
	@PostMapping("/signup")
	public String register(Model page, @ModelAttribute("User") User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setRole("ROLE_USER");
		udi.save(user);
		return "redirect:/signup";
	}
	@RequestMapping("/home")
	public String dashboard() {
		return "dashboard";
	}
	
	@RequestMapping("/liveview")
	public String liveview() {
		return "liveview";
	}
	
	@RequestMapping("/liveview/data")
	@ResponseBody
	public List<PowerConsumption> powercomsumption(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Long id = ((MyUserDetails) userDetails).getId();
		return pcs.getAllFromUser(id);
	}
	
	@PostMapping("/savedata")
	@ResponseBody
	public void savePower(@RequestBody PowerConsumption powerConsumption) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Long id = ((MyUserDetails) userDetails).getId();
		PowerConsumption pc = powerConsumption;
		pc.setUser_id(id);
		pcs.save(pc);
	}
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public void deletePower(@PathVariable Long id) {
		pcs.delete(id);
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEdit(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("edit");
		PowerConsumption pc = pcs.get(id);
		mav.addObject("PowerConsumption", pc);
		return mav;
	}
	@PostMapping("/update")
	public String update(@ModelAttribute("PowerConsumption") PowerConsumption pc) {
		PowerConsumption pc1 = pcs.get(pc.getId());
		pc.setUser_id(pc1.getUser_id());
		pcs.save(pc);
		return "redirect:/liveview";
	}
}
