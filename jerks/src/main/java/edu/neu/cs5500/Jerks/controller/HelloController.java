package edu.neu.cs5500.Jerks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

	@RequestMapping(value = "/index/{latitude}/{longitude}", method = RequestMethod.GET)
	public String index (@PathVariable("latitude") String a,@PathVariable("longitude") String b, ModelMap model)
	{
		try{
			Double latitude = (double) Double.parseDouble(a);
			Double longitude = (double) Double.parseDouble(b);
			System.out.println("hello "+ latitude + " " + longitude );
			model.put("latitude", latitude);
			model.put("longitude", longitude);
			return "index";
		}
		catch(Exception e) {
			return "geolocator";
		}
	}
	@RequestMapping(value = "/login/{uname}", method = RequestMethod.GET)
	public String register (@PathVariable("uname") String a,ModelMap model)
	{
		System.out.println("hello : " + a);
		model.put("uname", a);
		return "index";
	}
	@RequestMapping(value = {"/geolocator","/index", "/index/"}, method = RequestMethod.GET)
	public String geolocator (ModelMap model)
	{
		return "geolocator";
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultMethod ()
	{
		return "geolocator";
	}
	/*@RequestMapping(value = "/resources/**")
	   public String redirect() {
	     System.out.println();
	      return "redirect:/resources/**";
	   }*/
}
