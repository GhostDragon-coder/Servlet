package ru.appline;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Model;
import logic.User;

@WebServlet(urlPatterns = "/get")
public class ServletList  extends HttpServlet {
	
	Model model = Model.getInstance();
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	response.setContentType("application/json;charset=utf-8");
	PrintWriter pw = response.getWriter();
	
	int id = Integer.parseInt(request.getParameter("id"));
	
	if (id == 0) {
		pw.print(gson.toJson(model.getFromList()));
		
	} else if (id > 0) {
		if (id > model.getFromList().size()) {
			Map<String, String> messageMap = new HashMap<>();
			messageMap.put("message", "Такого пользователя нет :(");

			pw.print(gson.toJson(messageMap));
		} else {
			Map<Integer, User> userMap = model.getFromList();
			User user = userMap.get(id);
			pw.print(gson.toJson(user));
		}
	} else {
		Map<String, String> messageMap = new HashMap<>();
		messageMap.put("message", "ID должен быть больше нуля!");

		pw.print(gson.toJson(messageMap));
	}
	}
}
