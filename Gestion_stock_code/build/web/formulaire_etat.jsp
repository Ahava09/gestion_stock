
<%@page import="model.Magasin"%>
<%@page import="model.Categorie"%>
<%@page import="java.util.Vector"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Categorie[] produits = (Categorie[])request.getAttribute("produits");
    Magasin[] magasins = (Magasin[])request.getAttribute("magasins");
%>

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
        <form action="Traitement_etat" method="post">
        <% if (request.getAttribute("error") != null) { %>
            <h2 style="color: red;font-family: cursive; text-align:center"> <%= request.getAttribute("error") %> </h2>
        <% } %>
            <label for="id_article">Produit:</label>
            <select name="id_produit" value='PRDT02'>
                <% for(int i=0; i<produits.length; i++) { %>
                <option value=<%= produits[i].getPrimaryKey()%>> <%= produits[i].getNom() %>  </option>
                <% } %>
            </select>

            <label for="id_magasin">Magasin:</label>
            <select name="id_magasin" value='MAG002' >
                <% for(int i=0; i<magasins.length; i++) { %>
                <option value=<%= magasins[i].getPrimaryKey()%>> <%= magasins[i].getNom() %>  </option>
                <% } %>
            </select>

            <label for="dates">Date Debut:</label>
            <input type="date" id="dates" name="dateD" value='2023-11-04' required>
            
            <label for="dates">Date Fin:</label>
            <input type="date" id="dates" name="dateF" value='2023-11-08' required>

            <input type="submit" value="Voir l'etat">
        </form>
    </div>
</body>
</html>
