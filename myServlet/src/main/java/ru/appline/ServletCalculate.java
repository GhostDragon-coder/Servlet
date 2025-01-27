package ru.appline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@WebServlet(urlPatterns = "/calculate")
public class ServletCalculate extends HttpServlet {
Gson gson = new GsonBuilder().setPrettyPrinting().create();

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
	response.setContentType("application/json;charset=UTF-8");
	
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
	
	double a = jobj.get("a").getAsDouble();
	double b = jobj.get("b").getAsDouble();
	String math = jobj.get("math").getAsString();
	
	double result = 0;
	switch (math) {
	case "+":
	result = a + b;
	break;
	case "-":
	result = a - b;
	break;
	case "*":
	result = a * b;
	break;
	case "/":
	if (b != 0) {
		result = a / b;
	} else {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		PrintWriter pw = response.getWriter();
		JsonObject error = new JsonObject();
		error.addProperty("error", "Division by zero is not allowed.");
		pw.print(gson.toJson(error));
		return;
	}
	break;
	default:
	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	PrintWriter pw = response.getWriter();
	JsonObject error = new JsonObject();
	error.addProperty("error", "Invalid arithmetic operation.");
	pw.print(gson.toJson(error));
	return;
	}
	
	JsonObject resultJson = new JsonObject();
	resultJson.addProperty("result", result);
	
	PrintWriter pw = response.getWriter();
	pw.print(gson.toJson(resultJson));
	}
}

