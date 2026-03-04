# Medication Stock API (Java / Spring Boot)

Dieses Projekt ist eine **REST-API zur Verwaltung von Medikamentenbeständen**, entwickelt mit **Java und Spring Boot**.

Die Anwendung ermöglicht grundlegende CRUD-Operationen zur Verwaltung von Medikamentendaten und demonstriert den Aufbau einer einfachen Backend-Anwendung mit einer klaren Schichtenarchitektur.

## Funktionen

- REST-API zur Verwaltung von Medikamenten
- Erstellen, Abrufen, Aktualisieren und Löschen von Einträgen (CRUD)
- JSON-basierte Kommunikation
- Strukturierte Backend-Architektur mit Spring Boot

## Technologien

- Java
- Spring Boot
- Maven
- REST API
- JSON

## Projektstruktur

src/main/java

- api  
  REST-Controller zur Bereitstellung der API-Endpunkte

- application  
  Geschäftslogik und Service-Schicht

- domain  
  Domänenmodelle der Anwendung

- persistence  
  Datenzugriff und Repository-Klassen

Diese Struktur trennt: - API-Schicht
- Geschäftslogik
- Domänenmodell
- Persistenzschicht

## Beispiel-Endpunkte

### Medikament erstellen
POST /medications

### Alle Medikamente abrufen
GET /medications

### Medikament aktualisieren
PUT /medications/{id}

### Medikament löschen
DELETE /medications/{id}

## Hintergrund

Dieses Projekt entstand während eines Auslandssemesters im Rahmen eines Java-Kurses.  
Dabei wurde eine REST-API mit Spring Boot entwickelt, um grundlegende Konzepte der Backend-Entwicklung, API-Struktur und Softwarearchitektur praktisch umzusetzen.
