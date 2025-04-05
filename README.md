# API de Gestion des Livraisons

C'est une API simple pour gérer les modes de livraison et les réservations de créneaux horaires. Elle utilise Kafka pour la communication basée sur des événements et H2 comme base de données.

## Installation

### 1. Clonez le dépôt

git clone https://github.com/chaimabh-tech/DeliveryApp


### 2. Construire le projet

### 3. Démarrer Kafka et Zookeeper

Assurez-vous d'avoir **Kafka** et **Zookeeper** en cours d'exécution sur `localhost:9092`. L'application est configurée pour se connecter à Kafka sur ce port.

### 4. Lancer l'application

L'application s'exécute sur le port 8080 par défaut.

## Accéder à la base de données H2

Cette application utilise une **base de données H2 en mémoire**. Vous pouvez accéder à la console de la base de données à l'adresse suivante :

[http://localhost:8080/h2-console]

Connectez-vous avec :

- **URL JDBC** : `jdbc:h2:mem:testdb`
- **Nom d'utilisateur** : `chaima`
- **Mot de passe** : (vide)

## Points d'API

### 1. Obtenir les modes de livraison disponibles  
**GET** `/api/delivery/modes`

Retourne les modes de livraison disponibles (par exemple, `STANDARD`, `EXPRESS`).

### 2. Obtenir les créneaux horaires disponibles  
**GET** `/api/delivery/slots?mode=STANDARD&date=2025-04-06`

Récupère les créneaux horaires disponibles pour un mode et une date donnés.

### 3. Réserver un créneau horaire  
**POST** `/api/delivery/slots/{id}/reserve`

Réserve un créneau horaire par son ID. Si le créneau est déjà réservé, vous recevrez un message le signalant.

## Intégration Kafka

Lorsqu'un créneau horaire est réservé, un événement est envoyé à Kafka.

## Remarques

- L'application utilise **H2** (base de données en mémoire), donc les données sont perdues lorsque l'application redémarre. Vous pouvez accéder à la **console H2** via le lien ci-dessus pour consulter et interagir avec les données.
- Kafka envoie un message au topic `delivery-events` chaque fois qu'un créneau est réservé.
