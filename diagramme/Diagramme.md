# Diagrammes MLD et UML


## MLD
Ce diagramme comprend trois tables principales : **Specie**, **Observation** et **TravelLog**

>Tables : 
>- **Specie** : représente les espèces observées (id, commonName, scientificName, category).  
>- **Observation** : enregistre les observations (id, specie_id, observerName, location, latitude, longitude, date, comment).  
>- **TravelLog** : enregistre les déplacements liés à une observation (id, observation_id, distanceKm, mode, estimatedCo2Kg).  

> Cardinalités :  
> - Une espèce peut avoir **0 ou plusieurs** observations.  
> - Une observation concerne **une seule** espèce.  
> - Une observation peut avoir **0 ou plusieurs** déplacements.  
> - Un déplacement est lié à **une seule** observation.


## UML

Ce diagramme comprend 25 classes : 
- 3 Controller
- 3 Service
- 3 Repository
- 3 Model
- 2 DtoReceive
- 3 DtoResponse
- 1 Exception
- 2 Paginate
- 1 EnvironementApplication
- 1 Stat
- 2 Enum




## Accès

- Pour accéder au diagramme MLD en drawio
```bash
cd MLD 
```

- Pour accéder au diagramme de classe en drawio
```bash
cd diagramme_de_classe 
```