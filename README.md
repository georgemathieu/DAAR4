# DAAR4
M2 STL DAAR Project 4 : Moteur de recherche d'une bibliothèque


# Setup Spring API
Dans le dossier /daar : 
* mvn spring-boot:run

L'API se lance sur le port 8081

# Setup du client

Dans le dossier /library-search-engine :
* npm install
* ng s

Le site se lance sur le port 4200 : http://localhost/4200

# Setup du client multimachine

In the /library-search-engine :
* npm install
* ng s --host 0.0.0.0

Le site est accessible via l'IP dans le réseau local

## Obtenir l'IP à utiliser
Dans une fenêtre de commande :

* ipconfig
* Chercher "Adresse IPv4" dans "Carte réseau sans fil Wi-Fi" (format 192.168.x.x)
* Modifier l'url appelée dans les 4 composants Angular ( http://localhost:4200/basicsearch => http://192.168.x.x:4200/basicsearch )






