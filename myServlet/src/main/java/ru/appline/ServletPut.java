package ru.appline;

import java.io.BufferedReader;
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
import com.google.gson.JsonObject;
import logic.Model;
import logic.User;

@WebServlet(urlPatterns = "/update")
public class ServletPut extends HttpServlet {
Model model = Model.getInstance();
Gson gson = new GsonBuilder().setPrettyPrinting().create();

protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	StringBuffer jb = new StringBuffer();
	String line;
	try {
	BufferedReader reader = request.getReader();
	while ((line = reader.readLine()) != null) {
	jb.append(line);
	}
	} catch (Exception e) {
	System.out.println("Error reading request body");
	}
	
	JsonObject jobj = gson.fromJson(String.valueOf(jb), JsonObject.class);
	
	request.setCharacterEncoding("UTF-8");
	
	int id = jobj.get("id").getAsInt();
	String name = jobj.get("name").getAsString();
	String surname = jobj.get("surname").getAsString();
	double salary = jobj.get("salary").getAsDouble();
	
	User user = model.getFromList().get(id);
	
	response.setContentType("application/json;charset=utf-8");
	PrintWriter pw = response.getWriter();
	
	if (user != null) {
		user.setName(name);
		user.setSurname(surname);
		user.setSalary(salary);
	
		pw.print(gson.toJson(user));
		} else {
			Map<String, String> messageMap = new HashMap<>();
			messageMap.put("message", "Такого пользователя нет :(");
			pw.print(gson.toJson(messageMap));
		}
	}
}

