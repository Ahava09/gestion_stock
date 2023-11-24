/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  itu
 * Created: 20 nov. 2023
 */

create database gestion_stock_code;

\c gestion_stock_code;

CREATE SEQUENCE SeqUnite;
CREATE TABLE unite (
    id VARCHAR(7) not NULL PRIMARY key,
    nom  VARCHAR(50) ,
    abreviation VARCHAR(10) 
);
-- Ajout de données réelles à la table "unite"
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Unite', 'unite');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Cageot Bierre', 'Cageot');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Sachet biscuit', 'Sachet');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Carton fromage', 'Carton');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Sac 25kg', 'sac');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Boite', 'boite');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Kilogramme', 'kg');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Kapoka', 'kp');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Paquets', 'Pq');
INSERT INTO unite (id, nom, abreviation) VALUES ('UNT00' || NEXTVAL('SeqUnite'), 'Carton', 'carton');

CREATE SEQUENCE SeqType;
CREATE TABLE type (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    nom VARCHAR(20),
    description varchar(50)
);
-- Ajout de donnees reelles a la table "type"
INSERT INTO type (id, nom, description) VALUES ('TYPE0' || NEXTVAL('SeqType'), 'FiFo', 'First in First out');
INSERT INTO type (id, nom, description) VALUES ('TYPE0' || NEXTVAL('SeqType'), 'LiFo', 'Last in First out');

CREATE SEQUENCE SeqCategorie;
CREATE TABLE categorie (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    nom VARCHAR(50),
    code VARCHAR(20),
    id_type VARCHAR(7),
    FOREIGN KEY (id_type) REFERENCES type(id)
);
INSERT INTO categorie (id, nom, code, id_type) VALUES ('CAT0' || NEXTVAL('SeqCategorie'), 'Snack', 'S', 'TYPE01');
INSERT INTO categorie (id, nom, code, id_type) VALUES ('CAT0' || NEXTVAL('SeqCategorie'), 'Boisson', 'B', 'TYPE02');
INSERT INTO categorie (id, nom, code, id_type) VALUES ('CAT0' || NEXTVAL('SeqCategorie'), 'Laitiers', 'L', 'TYPE02');

CREATE SEQUENCE SeqArticle;
CREATE TABLE article (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    nom VARCHAR(50),
    reference VARCHAR(20),
    id_categorie VARCHAR(7),
    FOREIGN KEY (id_categorie) REFERENCES categorie(id)
);
-- Ajout de donnees reelles a la table "article"
INSERT INTO article (id, nom, reference, id_categorie) VALUES ('ART00' || NEXTVAL('SeqArticle'), 'Biscuits', '1010', 'CAT01');
INSERT INTO article (id, nom, reference, id_categorie) VALUES ('ART00' || NEXTVAL('SeqArticle'), 'THB', '1020', 'CAT02');
INSERT INTO article (id, nom, reference, id_categorie) VALUES ('ART00' || NEXTVAL('SeqArticle'), 'Fromage', '1030', 'CAT03');


------------

------------
CREATE SEQUENCE SeqUniteE;
CREATE TABLE uniteE (
    id VARCHAR(7) not NULL PRIMARY key,
    id_article  VARCHAR(50) ,
    id_unite VARCHAR(10) ,
    quantite decimal,
    FOREIGN KEY (id_unite) REFERENCES unite(id),
    FOREIGN KEY (id_article) REFERENCES article(id)
);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART001', 'UNT003',50);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART002', 'UNT002',8);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART003', 'UNT004',10);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART001', 'UNT001',1);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART002', 'UNT001',1);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART003', 'UNT001',1);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART0010', 'UNT0010',6);
INSERT INTO uniteE VALUES ('UNTE0' || NEXTVAL('SeqUniteE'), 'ART0010', 'UNT001',1);

CREATE SEQUENCE SeqMagasin;
CREATE TABLE magasin (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    nom VARCHAR(50),
    adresse VARCHAR(50),
    contact  VARCHAR(50)
);
-- Ajout de colonnes supplémentaires à la table "magasin"
ALTER TABLE magasin
ADD COLUMN capacite_max DECIMAL,
ADD COLUMN date_creation DATE;

-- Ajout de données réelles à la table "magasin"
INSERT INTO magasin (id, nom, adresse, contact, capacite_max, date_creation) VALUES ('MAG00' || NEXTVAL('SeqMagasin'), 'Magasin A', '123 Rue Principale', '0123456789', 500, '2023-01-15');
INSERT INTO magasin (id, nom, adresse, contact, capacite_max, date_creation) VALUES ('MAG00' || NEXTVAL('SeqMagasin'), 'Magasin B', '456 Avenue Centrale', '9876543210', 800, '2023-03-20');
INSERT INTO magasin (id, nom, adresse, contact, capacite_max, date_creation) VALUES ('MAG00' || NEXTVAL('SeqMagasin'), 'Magasin C', '789 Rue Secondaire', '5555555555', 1000, '2023-06-10');

CREATE SEQUENCE SeqEntree;
CREATE TABLE entree (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    id_article VARCHAR(7) not null,
    id_magasin VARCHAR(7) not null, 
    date_entree timestamp,
    date_expiration timestamp,
    quantite decimal,
    prix_unitaire decimal,
    id_unite VARCHAR(7) not null, 
    FOREIGN KEY (id_unite) REFERENCES unite(id),
    FOREIGN KEY (id_article) REFERENCES article(id),
    FOREIGN KEY (id_magasin) REFERENCES magasin(id)
); 
-- Ajout de données réelles à la table "entree"
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART001', 'MAG001', '2023-11-05 12:00:00', '2024-11-05', 10,2000,'UNT005');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART002', 'MAG001', '2023-11-07 13:00:00', '2024-11-07', 5,2500,'UNT005');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART003', 'MAG001','2023-10-30 12:00:00', '2024-10-30', 20,2100,'UNT005');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART004', 'MAG001', '2023-11-01 06:00:00', '2024-11-01', 8,2200,'UNT007');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART001', 'MAG001', '2023-11-07 12:00:00', '2024-11-07', 7,2100,'UNT002');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART009', 'MAG002', '2023-11-01 09:00:00', '2024-11-01', 8,1000,'UNT002');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART0010', 'MAG002', '2023-11-07 12:00:00', '2024-11-07', 7,3000,'UNT001');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART009', 'MAG002', '2023-11-03 11:00:00', '2024-11-5', 10,1000,'UNT001');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART009', 'MAG002', '2023-11-05 12:00:00', '2024-11-03', 11,1100,'UNT001');
INSERT INTO entree (id, id_article, id_magasin, date_entree, date_expiration, quantite,prix_unitaire,id_unite) VALUES ('ENT00' || NEXTVAL('SeqEntree'), 'ART001', 'MAG001', '2023-11-08 12:00:00', '2024-11-03', 10,1100,'UNT005');

drop sequence SeqSortie;
drop table sortie;
CREATE SEQUENCE SeqSortie;
CREATE TABLE sortie (
    id VARCHAR(7) NOT NULL PRIMARY KEY,
    id_article VARCHAR(7) not null,
    id_magasin VARCHAR(7) not null,
    date_insertion timestamp,
    quantite decimal,
    etat int,
    date_validation timestamp DEFAULT null,
    id_unite VARCHAR(7) not null,  
    FOREIGN KEY (id_unite) REFERENCES unite(id),
    FOREIGN KEY (id_article) REFERENCES article(id),
    FOREIGN KEY (id_magasin) REFERENCES magasin(id)
);
CREATE OR REPLACE FUNCTION update_date_validation()
RETURNS TRIGGER AS $$
BEGIN
    -- Mettez à jour date_validation seulement si elle est actuellement NULL
    IF NEW.date_insertion IS NOT NULL AND NEW.date_validation IS NULL THEN
        NEW.date_validation := NEW.date_insertion;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER trigger_update_date_validation
BEFORE INSERT OR UPDATE ON sortie
FOR EACH ROW
EXECUTE PROCEDURE update_date_validation();

-- -- Ajout de données réelles à la table "sortie"
INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART001', 'MAG004', '2023-12-25 05:00:00', 500, 0,'UNT003');
-- INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART002', 'MAG001', '2023-11-07 06:00:00', 1, 0,'UNT007');
-- INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART003', 'MAG001','2023-10-30 07:00:00', 3, 0,'UNT007');
-- INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART004', 'MAG001', '2023-11-01 12:00:00', 5, 0,'UNT008');
-- INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART004', 'MAG001','2023-10-30 08:00:00', 5, 0, 'UNT007');
-- INSERT INTO sortie (id, id_article, id_magasin, date_insertion, quantite, etat,id_unite) VALUES ('SRT00' || NEXTVAL('SeqSortie'), 'ART004', 'MAG001', '2023-11-01 16:00:00', 5, 0,'UNT008');

create sequence SeqMouvement;
create table Mouvement (
    id varchar(7) primary key,
    id_sortie  varchar(7),
    id_entree  varchar(7),
    reste decimal,
    prix decimal,
    foreign key (id_entree) references entree(id),
    foreign key (id_sortie) references sortie(id)
);

