package edu.neu.cs5500.Jerks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@RequestMapping(value = "/index/{latitude}/{longitude}", method = RequestMethod.GET)
	public String index (@PathVariable("latitude") String a,@PathVariable("longitude") String b, ModelMap model)
	{
		try{
			Double latitude = (double) Double.parseDouble(a);
			Double longitude = (double) Double.parseDouble(b);
			model.put("latitude", latitude);
			model.put("longitude", longitude);
			return "index";
		}
		catch(Exception e) {
			System.out.println("From controller catch block");
			return "geolocator";
		}
	}
	
	@RequestMapping(value = {"/geolocator","/index", "/index/"}, method = RequestMethod.GET)
	public String geolocator (ModelMap model)
	{
		System.out.println("Hello from geolocator");
		return "geolocator";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String b (@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("latitude") String latitude,
			@RequestParam("longitude") String longitude,   ModelMap model)
	{
		model.put("username", username);
		model.put("password", password);
		model.put("latitude", latitude);
		model.put("longitude", longitude);
		return "index";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultMethod ()
	{
		System.out.println("Hello from default");
		return "geolocator";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register()
	{
		System.out.println("Hello from default");
		return "register";
	}
	/*@RequestMapping(value = "/resources/**")
	   public String redirect() {
	     System.out.println();
	      return "redirect:/resources/**";
	   }*/

}
