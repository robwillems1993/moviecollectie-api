# MovieCollectie API

## Inhoudsopgave

- [Inleiding](#inleiding)
- [Kernfunctionaliteiten](#kernfunctionaliteiten)
- [Projectstructuur](#projectstructuur)
- [Gebruikte technieken](#gebruikte-technieken)
- [Benodigdheden](#benodigdheden)
- [Installatie-instructies](#installatie-instructies)
    - [1. Database configuratie](#1-database-configuratie)
    - [2. Authenticatie (Keycloak)](#2-authenticatie-keycloak)
    - [3. Applicatie starten](#3-applicatie-starten)
- [Tests uitvoeren](#tests-uitvoeren)
- [Gebruikersrollen](#gebruikersrollen)

## Inleiding
Tegenwoordig bestaan er veel verschillende streamingdiensten, zoals Netflix, HBO Max, Videoland, Amazon Prime,
Disney+ enzovoort. Elke dienst biedt een eigen filmaanbod en houdt per gebruiker bij welke films zijn bekeken. Er
ontbreekt echter een overkoepelende applicatie die gebruikers één centraal overzicht geeft van hun persoonlijke
filmvoorkeuren, onafhankelijk van de gebruikte streamingdienst.

De **MovieCollectie** web-API biedt een centrale backend waarmee gebruikers hun persoonlijke filmcollectie
kunnen beheren. Gebruikers kunnen zich registreren en inloggen, informatie over films bekijken, films toevoegen
aan verschillende lijsten (nog te kijken, gezien en favorieten) en films beoordelen met een
rating. Beheerders (ADMIN) beheren het filmaanbod door films toe te voegen en te wijzigen. Om films visueel
herkenbaar te maken ondersteunt de API het uploaden en downloaden van een posterbestand per film. Door middel van
verschillende rollen zijn beheerfunctionaliteiten uitsluitend beschikbaar voor beheerders, terwijl
gebruikers (USER) alleen gegevens kunnen ophalen en hun eigen collectie-items kunnen aanmaken en wijzigen.

## Kernfunctionaliteiten
- Authenticatie en autorisatie via JWT, OAuth2 en Keycloak
- Beheerders kunnen genres, films en posters toevoegen zodat gebruikers deze kunnen ophalen
- Gebruikers kunnen genres, films en posters ophalen
- Gebruikers kunnen films aan hun persoonlijke watchlist toevoegen, beoordelen met een rating en als favoriet toevoegen
- Beveiliging op basis van rollen (USER en ADMIN)

## Projectstructuur
moviecollectie-api
└── src
    ├── main
    │ ├── java/nl/novi/eindopdrachtbackend/moviecollectieapi
    │ │ ├── config
    │ │ ├── controllers
    │ │ ├── dtos
    │ │ ├── entities
    │ │ ├── mappers
    │ │ ├── repositories
    │ │ ├── services
    │ │ └── MovieCollectieApiApplication.java
    └── test
      └── java/nl/novi/eindopdrachtbackend/moviecollectieapi
        └── servicesTests

## Gebruikte technieken
- Spring Boot
- PostgreSQL
- Postman
- PGAdmin
- Maven

## Benodigdheden
Om deze applicatie lokaal te kunnen draaien heb je het volgende nodig:
- Java 17 of hoger
- Maven
- PostgreSQL
- pgAdmin
- Postman
- Keycloak (Alleen voor het registreren van nieuwe gebruikers)

## Installatie-instructies

### 1. Database configuratie
Maak in PGAdmin een nieuwe database aan en noem deze:
"moviecollectiedatabase"

### 2. Authenticatie (Keycloak)

Deze API gebruikt JWT-tokens uitgegeven door Keycloak.

Importeer in Keycloak eventueel de meegeleverde realm-export, deze is te vinden in de resources map.

1. Start een Keycloak server

2. Maak een nieuwe realm aan: MovieCollector-api

3. Maak een nieuwe client aan met client-id: MovieCollector-api

4. Voeg de rollen toe: USER en ADMIN

5. Maak testgebruikers aan en ken rollen toe

De issuer-uri is geconfigureerd in application.properties:
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:9090/realms/MovieCollector-api

### 3. Applicatie starten

Gebruik de "Run" knop (of Shift+F10 op Windows) in IntelliJ om de applicatie te starten

De applicatie is daarna aanspreekbaar via poort 8080.

## Tests uitvoeren

De applicatie bevat unit-tests voor de service-laag en integratietests voor de API.

Tests uitvoeren:
'mvn test' in de terminal
of
rechter-muisknop op het testbestand en klik "Run * with coverage"

De service-laag (MovieService en GenreService) is getest met 100% line coverage.

## Gebruikersrollen
Rol	Beschrijving
USER	Kan films, genres en posters ophalen, kan films toevoegen aan watchlist, favoriet- en ratinglijst
ADMIN	Kan alles wat USER ook kan, kan films, genres en posters beheren (toevoegen, updaten, verwijderen)

Er vindt authenticatie plaats bij elke API-call. Alle endpoints vereisen een geldig JWT-token.

In Postman:
Vraag onder 'Authorization' een nieuwe token aan en log in via Keycloak.
Gebruik eerder aangemaakte gebruikers (Zie 2. Authenticatie (Keycloak)).
Als de meegeleverde realm is geïmporteerd kunnen onderstaande testgebruikers worden gebruikt:
USER    'testuser' met wachtwoord 'testuser'
ADMIN   'testadmin' met wachtwoord 'testadmin'