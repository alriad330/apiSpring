# Test De l'Application :
Au démarrage de l'application 3 produits sont crées par le script "data.sql"
dans la base H2 (http://localhost:8080/h2-console).
## via Postman :
### Test Partie 1 :

Créer un nouveau produit : 
* [POST] : http://localhost:8080/product
  * BODY :
  ```
    {
    "code": "TV_004",
    "name": "TCL",
    "description": "Television",
    "image": null,
    "category": "001",
    "price": 300.0,
    "quantity": 100,
    "internalReference": "TV001",
    "shellId": null,
    "inventoryStatus": "INSTOCK",
    "rating": 8.2,
    "createdAt": "1733942578",
    "updatedAt": null
  }
  ```
Afficher tous les produits :
* [GET] : http://localhost:8080/products

Afficher un produit :
* [GET] : http://localhost:8080/product/1

Modifier un produit :
* [PATCH] : http://localhost:8080/product/1
  * BODY :
  ```
    {
      "code": "TV_004_11",
      "name": "TCL",
      "description": "Television",
      "image": null,
      "category": "001",
      "price": 300.0,
      "quantity": 100,
      "internalReference": "TV001",
      "shellId": null,
      "inventoryStatus": "INSTOCK",
      "rating": 8.2,
      "createdAt": 1733942578,
      "updatedAt": null
    }
  ```
Supprimer un produit : 
* [DELETE] : http://localhost:8080/product/3

### Test Partie 2 :

* [POST] : http://localhost:8080/account
  * BODY :
  ``` 
  {
    "email" : "test@gmail.com",
    "password" :"test123",
    "firstname" : "toto",
    "username" : "test@gmail.com"
  }
  ```

* [POST] : http://localhost:8080/token
  * BODY :
  ``` 
  {
    "email" : "test@gmail.com",
    "password" : "test123"
  }
  ```

* Récupérer la session du compte connecté :
  * [GET] : http://localhost:8080/session
  * En utilisant le token généré précédement via le ([POST] : http://localhost:8080/token).
  * N.B :  sur Postman dans le rubrique "Authorization", onglet "Type"
  choisir le mode "Bearer Token" et entrez le token récupéré puis cliquez sur "send" du GET.
  
  * Résultat :
  ```
  {
    "Account": {
        "id": 1,
        "username": "test@gmail.com",
        "firstname": "toto",
        "email": "test@gmail.com",
        "password": "$2a$10$/XlDEXec8JVco11c4P8oa.g/RvcXkLyXquCp2CNc3XdU8KdCwsJhO"
    },
    "Username": "test@gmail.com"
  }
  ```

