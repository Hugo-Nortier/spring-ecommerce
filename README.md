# Plateforme d'eCommerce : Hugo NORTIER
---

Simple project to demonstrate Microservice usage

Il y a 4 microservices :
- **client** qui tourne sur le port 8090
- **product** qui tourne sur le port 8091
- **cart** qui tourne sur le port 8092
- **order** qui tourne sur le port 8093

9 produits sont proposés à la vente sur la page 'index.html'. 
Un produit possède un id, un nom, une description et un prix.   
Au clic sur le nom du produit, on accède à sa page de détail 'productDetail.html'.
Un produit peut être placé dans le panier 'cart.html'.  
Si on place le même produit plus d'une fois dans le panier, alors sa quantité dans le panier est augmentée.  
Un produit peut être supprimé du panier.    
Puis, nous pouvons passer commande via le bouton "Commander". S'il n'y a pas de produit dans le panier, la page invite l'utilisateur à se rendre à l'accueil.      
Toutes les commandes sont listées dans la page 'orders.html'. Lors de la commande, la somme des produits (en fonction de leur quantité) est faite.  
Lorsque la commande a été passée, le panier est vidé.  
Si l'utilisateur fait une requête sur un objet non existant, alors est appelée la page 'error.html' qui rend compte de l'exception qui a été levée.
Un exemple serait une requête sur le détail du produit id n°15 (`http://localhost:8090/product-detail/15`) qui n'existe pas (Erreur 404).  

La persistance des objets en base de données (BD) est réalisée via Hibernate et la BD est 'H2', une BD volatile.  

Par soucis d'esthétisme, je me suis également énormément investi dans la réalisation de sublime pages HTML.
