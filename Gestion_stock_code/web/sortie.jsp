<%@page import="model.Unite"%>
<%@page import="model.Magasin"%>
<%@page import="model.Article"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Article[] articles = (Article[])request.getAttribute("articles");
    Magasin[] magasins = (Magasin[])request.getAttribute("magasins");
    Unite[] unites = (Unite[])request.getAttribute("unites");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Menu Principal</title>
    <link rel="stylesheet" type="text/css" href="./css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>

<h1>Menu Principal</h1>
    <header>
        <nav>
            <ul>
                <li><a href="index.jsp"><div><i class="fas fa-plus"></i> Ajout Entrée</div></a></li>
                <li><a href="Sorties"><div><i class="fas fa-minus"></i> Ajout Sortie</div></a></li>
                <li><a href="Liste"><div><i class="fas fa-list"></i> Liste Sorties</div></a></li>
                <li><a href="Formulaire_etat"><div><i class="fas fa-chart-bar"></i> État de Stock</div></a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <% if (request.getAttribute("error") != null) { %>
            <h2 style="color: red;font-family: cursive; text-align:center"> <%= request.getAttribute("error") %> </h2>
        <% } %>
        <form action="Insertion_sortie" method="post">
            <% if (request.getAttribute("error") != null) { %>
                <h2 style="color: red;font-family: cursive; text-align:center"> <%= request.getAttribute("error") %> </h2>
            <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <h2 style="color: red;font-family: cursive; text-align:center"> <%= request.getAttribute("error") %> </h2>
        <% } %>
            <label for="id_article">Article:</label>
            <select name="id_article" >
                <% for(int i=0; i<articles.length; i++) { %>
                <option value=<%= articles[i].getPrimaryKey()%>> <%= articles[i].getNom() %>  </option>
                <% } %>
            </select>

            <label for="id_magasin">Magasin:</label>
            <select name="id_magasin">
                <% for(int i=0; i<magasins.length; i++) { %>
                <option value=<%= magasins[i].getPrimaryKey()%>> <%= magasins[i].getNom() %>  </option>
                <% } %>
            </select>

            <label for="dates">Date:</label>
            <input type="date" id="dates" name="dates" >

            <label for="magasin">Unite de mesure :</label>
            <select id="magasin" name="id_unite">
                <% for(int i=0; i<unites.length; i++) { %>
                    <option value=<%= unites[i].getPrimaryKey()%>> <%= unites[i].getNom() %>  </option>
                <% } %>
            </select>
            
            <label for="quantite">Quantité:</label>
            <input type="text" id="quantite" name="quantite" required>
            

            <input type="submit" value="Soumettre">
        </form>
    </div>
</body>
</html>
