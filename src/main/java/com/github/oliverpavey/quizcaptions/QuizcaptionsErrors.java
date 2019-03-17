package com.github.oliverpavey.quizcaptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.github.oliverpavey.quizcaptions.store.ValuePair;

@Component
public class QuizcaptionsErrors {

	public String problem(Model model, String problem) {
		model.addAttribute("problem", problem);
		return "system/problem";
	}

	public String getError(Model model, HttpServletRequest request) {
		
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Exception exception = (Exception) request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");
		if (exception==null) {
			exception = (Exception) request.getAttribute("javax.servlet.error.exception");
		}
		
		model.addAttribute("statusCode", statusCode==null ? "null" : String.format("%d",  statusCode) );
		model.addAttribute("status", statusCode==null ? "null" :  HttpStatus.valueOf(statusCode).toString() ); 
		model.addAttribute("exception", exception==null ? "null" : exception.getClass().getName() );
		model.addAttribute("message", exception==null ? "null" : exception.getMessage() );
		
		ArrayList<String> attrNames = Collections.list( request.getAttributeNames() );
		List<ValuePair> attrPairs = attrNames.stream().map(attrName -> {
			Object obj = request.getAttribute(attrName);
			String str = obj==null ? "null" : obj.toString();
			return new ValuePair(attrName, str);
		}).collect(Collectors.toList());
		
		model.addAttribute("attrNames", attrNames );
		model.addAttribute("attrPairs", attrPairs );
		
		return "system/error";
	}
}
