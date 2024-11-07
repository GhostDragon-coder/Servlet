<%@ page import="logic.Model" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Title</title>
</head>
<body>
<h1>Домашняя страница по работе с пользователями</h1>
<p>Введите ID пользователя (0 - для вывода всего списка пользователей)</p>
<br/>
<p>Доступно: <% Model model = Model.getInstance();
	out.print(model.getFromList().size());
	%></p>
<form method="get" action="get">
	<label>ID:
		<input type="text" name="id"><br/>
	</label>
	<button type="submit">Создать</button>
</form>
<a href="addUser.html">Создать нового пользователя</a>
</body>
</html>