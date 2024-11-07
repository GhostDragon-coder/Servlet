package ru.appline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;
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

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

private AtomicInteger counter = new AtomicInteger(1);
Model model = Model.getInstance();
Gson gson = new GsonBuilder().setPrettyPrinting().create();

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	response.setContentType("text/html;charset=UTF-8");
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
	
	
	String name = jobj.get("name").getAsString();
	String surname = jobj.get("surname").getAsString();
	double salary = jobj.get("salary").getAsDouble();
	
	User user = new User(name, surname, salary);
	model.add(user, counter.getAndIncrement());
	
	response.setContentType("text/html;charset=utf-8");
	PrintWriter pw = response.getWriter();
	
	pw.print("<html>" + 
			"<h3>Пользователь " + name + " " + surname + " с зарплатой " + salary + " успешно создан! :)</h3>" +
			"<a href=\"addUser.html\"> Создать нового пользователя</a><br/>" +
			"<a href=\"index.jsp\"> Домой</a><br/>" +
			"</html>"
			);
	}
}
