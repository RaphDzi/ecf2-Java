# ecf2-Java
Application de suivi d’observations par API call, d’espèces et de déplacements avec calcul d’émissions CO₂ et agrégations en base de données.



## 1 - Modélisation de l'application
- Gestion des diagrammes à retrouver dans le dossier diagramme 
    - Toutes les infos sont dans le fichier MD
```Bash
cd diagramme/diagramme.MD
```



## 2 - Développement
- Développement des entités et de leurs classes :
     - DTO
     - Repository
     - Service
     - Controller

- Gestion de tous les endpoints demandés
- Gestion et enregistrement des données en dtb
- Sécurisation des identifiant de connexion dtb dans un fichier .env
- Initialisation de données factices pour les tests

- Pour accéder aux documents et ouvrir le projet Java : 
```Bash
cd Environement
```




## 3 - Tests

- Tests des endpoints :
> Observation
> Specie
> Travellog

- Par défaut des données seront enregistrées dans la base dès l'exécution du code par le fichier **DataInitializer**.


## 4 - Accéder à l'application 

- Assurez vous d'avoir crée une base de donnée portant le nom **environement_db** avec les bons identifiants. Ils sont enregistrés dans le fichier **.env** à la racine du projet : 
```SQL
CREATE SCHEMA `environement_db` ;
```
```Bash
cd Environement/.env
```

- Lancer le projet

- Tester les endpoints de l'API avec une application comme PostMan :)