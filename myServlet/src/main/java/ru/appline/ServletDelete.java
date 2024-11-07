package ru.appline;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.Model;
import logic.User;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {
Model model = Model.getInstance();
Gson gson = new GsonBuilder().setPrettyPrinting().create();

protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String idParam = request.getParameter("id");
	int id = Integer.parseInt(idParam);
	
	User removedUser = model.getFromList().remove(id);
	
	response.setContentType("application/json;charset=utf-8");
	PrintWriter pw = response.getWriter();
	
	if (removedUser != null) {
			pw.print(gson.toJson(removedUser));
		} else {
			Map<String, String> messageMap = new HashMap<>();
			messageMap.put("message", "Такого пользователя нет :(");
			pw.print(gson.toJson(messageMap));
		}
	}
}
