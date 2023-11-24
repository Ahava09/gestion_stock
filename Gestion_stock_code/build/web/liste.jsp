<%@page import="model.Sortie"%>
<%@page import="model.Magasin"%>
<%@page import="model.Article"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Article[] articles = (Article[])request.getAttribute("articles");
    Magasin[] magasins = (Magasin[])request.getAttribute("magasins");
    Sortie[] sorties = (Sortie[]) request.getAttribute("sorties");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulaire</title>
    <link rel="stylesheet" type="text/css" href="./css/index.css">
    <link rel="stylesheet" type="text/css" href="./css/liste.css">
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
<div id="list">
    <section>
    <h2>Recherche</h2>
    <form action="Traitement_liste" method="post">
        <label for="article">Article :</label>
        <select id="article" name="id_article">
            <option value="ART000"> Tout </option>>
            <% for (Article article : articles) { %>
                <option value="<%= article.getPrimaryKey()%>"><%= article.getNom() %></option>
            <% } %>
        </select>

        <br>

        <label for="magasin">Magasin :</label>
        <select id="magasin" name="id_magasin" >
            <option value="MAG000"> Tout </option>>
            <% for (Magasin magasin : magasins) { %>
                <option value="<%= magasin.getPrimaryKey()%>"><%= magasin.getNom() %></option>
            <% } %>
        </select>

        <br>

        <input type="submit" value="Enregistrer">
    </form>
</section>
        
    <h2>Liste des sorties non valider</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Date Insertion</th>
                <th>Nom Article</th>
                <th>Nom Magasin</th>
                <th>Quantité</th>
            </tr>
        </thead>
        <tbody>
            <!-- Insérer ici les lignes du tableau avec les données dynamiques -->
                
                <% for(int i=0; i<sorties.length; i++) { %>
                    <form action="Validation" method="post">
                    <tr>
                    <input type="hidden" value="<%= sorties[i].getPrimaryKey()%>" name="id_sortie"/>
                        <td><%= sorties[i].getPrimaryKey()%></td>
                        <td><%= sorties[i].getDate_insertion()%></td>
                        <td><%= sorties[i].getId_article() %></td>
                        <td><%= sorties[i].getId_magasin()%></td>
                        <td><%= sorties[i].getQuantite()%></td>
                        <td><input type="date" id="dateEntree" name="date"  /></td>
                        <td><input type="submit" value="valider"></td>
                    </tr>
                    </form>
                <% } %>
            <!-- Ajouter d'autres lignes si nécessaire -->
        </tbody>
    </table>
</div>


</body>
</html>

