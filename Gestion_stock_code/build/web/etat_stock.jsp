<%-- 
    Document   : etat_stock
    Created on : 15 nov. 2023, 06:13:56
    Author     : itu
--%>

<%@page import="model.Categorie"%>
<%@page import="model.Etat_stocks"%>
<%@page import="java.util.Vector"%>
<%@page import="model.Etat_stock"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Etat_stocks[] stocks = (Etat_stocks[]) request.getAttribute("stocks");
    Categorie produit = (Categorie)request.getAttribute("produit");
%>
<html>
<head>
    <title>Menu Principal</title>
    <link rel="stylesheet" type="text/css" href="./css/index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px; /* Espacement entre le titre et le tableau */
        }

        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }

        h3 {
            margin-top: 20px; /* Espacement entre le tableau et le total */
        }

    </style>
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
<h2>Tableau des Entrées et Sorties</h2>
<table>
    <thead>
        <tr>
            <th>Article</th>
            <th>Date début</th>
            <th>Date fin</th>
            <th>Quantité initiale</th>
            <th>Quantité finale</th>
            <th>Prix unitaire</th>
            <th>Montant</th>
        </tr>
    </thead>
    <tbody>
        <!-- Insérer ici les lignes du tableau avec les données dynamiques -->
        <% double montant = 0; for(int i=0; i<stocks.length; i++) { montant = montant + stocks[i].getMontant(); %>
            <tr>
                <td><%= stocks[i].getNom_atricle()%></td>
                <td><%= stocks[i].getDate_debut()%></td>
                <td><%= stocks[i].getDate_fin()%></td>
                <td><%= stocks[i].getQuantite_intiale()%></td>
                <td><%= stocks[i].getQuantite_finale()%></td>
                <td><%= stocks[i].getPrix_unitaire()%></td>
                <td><%= stocks[i].getMontant()%></td>
            </tr>
        <% } %>
        <!-- Ajouter d'autres lignes si nécessaire -->
    </tbody>
</table>
        <h3> Montant total: <%= montant %> Ariary </h3>
</body>
</html>
