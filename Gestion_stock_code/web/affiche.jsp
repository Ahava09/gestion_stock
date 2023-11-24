<%-- 
    Document   : affichage
    Created on : 23 nov. 2023, 11:26:46
    Author     : itu
--%>

<%@page import="model.Sortie"%>
<%@page import="model.Entree"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

    Sortie avant = (Sortie)request.getAttribute("avant");
    Sortie apres = (Sortie)request.getAttribute("apres");
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
    <body>
        <h2>Tableau des Entrées et Sorties</h2>
    <table>
        <thead>
            <tr>
                <th>Article</th>
                <th>Date</th>
                <th>Magasin</th>
                <th>Quantité initiale</th>
                <th>Quantité finale</th>
            </tr>
        </thead>
        <tbody>
                <tr>
                    <td><%= avant.getId_article()%></td>
                    <td><%= avant.getDate_validation()%></td>
                    <td><%= avant.getId_magasin()%></td>
                    <td><%= apres.getQuantite()%></td>
                    <td><%= avant.getQuantite()%></td>
                </tr>
            <!-- Ajouter d'autres lignes si nécessaire -->
        </tbody>
    </table>

    </body>
</html>
