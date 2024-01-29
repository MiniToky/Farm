create database farm;

-- \c farm

create sequence idUtilisateur;

create table utilisateur(
    id varchar(50) PRIMARY KEY ,
    nom varchar(250),
    email varchar(100),
    mdp varchar(100),
    type int
);

insert into utilisateur values
('user'||nextval('idUtilisateur'),'Rakoto', 'rakoto@gmail.com', 'rakoto', 1),
('user'||nextval('idUtilisateur'),'Rabe', 'rabe@gmail.com', 'rabe', 1),
('user'||nextval('idUtilisateur'),'Admin', 'admin@gmail.com', 'admin', 0);

create sequence idLocalisation;

create table localisation(
     id varchar(50) PRIMARY KEY ,
     nom varchar(100)
);

insert into localisation values
('loc'||nextval('idLocalisation'), 'Antananarivo'),
('loc'||nextval('idLocalisation'), 'Antsiranana'),
('loc'||nextval('idLocalisation'), 'Fianarantsoa'),
('loc'||nextval('idLocalisation'), 'Mahajanga'),
('loc'||nextval('idLocalisation'), 'Toamasina'),
('loc'||nextval('idLocalisation'), 'Toliara');

create sequence idCulture;

create table culture(
    id varchar(50) PRIMARY KEY,
    nom varchar(100),
    rendement numeric(10,2), -- kg/m2
    prix numeric(10,2), -- ar/kg
    duree numeric(10,2)
);

insert into culture values
('cult'||nextval('idCulture'), 'Riz', 0.6, 3000, 10),
('cult'||nextval('idCulture'), 'Pomme de terre', 5, 2600, 7),
('cult'||nextval('idCulture'), 'Tomate', 10, 3500, 7);

create sequence idTerrain;

create table terrain(
    id varchar(50) PRIMARY KEY,
    idLocalisation varchar(50),
    description varchar(250),
    idUtilisateur varchar(50),
    nbParcelle int,
    etat int, -- 0 en attente, 1 validé
    creation timestamp,
    foreign key (idUtilisateur) references  utilisateur(id),
    foreign key (idLocalisation) references localisation(id)
);

create table terrain_photo(
    idTerrain varchar(50),
    url varchar(250),
    foreign key (idTerrain) references terrain(id)
);

create sequence idParcelle;

create table parcelle(
    id varchar(50) PRIMARY KEY,
    idTerrain varchar(50),
    largeur numeric(10,2),
    longueur numeric(10,2),
    foreign key (idTerrain) references terrain(id)
);

create table parcelle_culture_possible(
    idParcelle varchar(50),
    idCulture varchar(50),
    foreign key (idParcelle) references parcelle(id),
    foreign key (idCulture) references culture(id)
);

create table etat_parcelle(
    idParcelle varchar(50) PRIMARY KEY,
    idCulture varchar(50),
    etat int, -- 0 en cours, 1 recoltable
    plantation timestamp,
    foreign key (idParcelle) references parcelle(id),
    foreign key (idCulture) references culture(id)
);

