---
marp: true
theme: gaia
--- 
# Security 
---
## AAA
 1. Authentification : Identifier l'utilisateur
 2. Authorization : Authoriser l'utilisateur à acceder à une resource 
 3. Accounting(tracing) : Suivre les actions de l'utilisateur 
---
## Authentification
---
### Ajouter un slide : Ce que l'utilisateur Sais/Possede/Est 
---
### Basic Auth
  - Format
     - `Authorization: Basic name:mdp` (name:mdp -> en Base64)
     - Exemple : `Authorization: Basic cGluZ29vOjEyMzQ1` name: pingoo, mdp:1234
  - Avantage :
    - Correspond au format classique utilisateur/mdp
  - Inconvenient : 
    - Le mot de passe utilisateur est dans la requête
    - On ne peux pas revoquer un terminal
    - Les mots de passe sont parfois faible
  - Usage :
    - Dans les ui pour recuperer un token
---
### Certificat
  - Format
    - 
  - Avantage :
    -
  - Inconvenient :
    - Il faut bien penser a mettre a jour le Certificat
    - Pas revocable
    - La Gestion du Certificat est souvent pas terrible (mail)
    - Un utilisateur ne peux pas le retenir
  - Usage :
    - Api serveur - serveur
---
### Token
  - Token Enregistre
    - Avantage :
      - Les tokens sont revocable
      - 
    - Inconvenient
      - Il faut aller en base voir si le token est valide 
      - Un utilisateur 
    - Usage :
      - Des api serveur - serveur avec une ui pour de management des tokens (exemple : github)
      - Des ui avec l'option "keep me connected" [facebook](le lien vers la page d'admin)
  - Token Signé
      - Avantage 
        - Pas besoin d'une base
      - Inconvenient
        - Un utilisateur ne peux pas le retenir
      - Usage :
        - Pour une ui avec des token de courte duree
---
### Les Authentifications forte
Exemple : 
   - jeton rsa 
   - Authenticator
   - Generateur de token
---
### Jwt

---
## Authorization
---
## Accounting 
---
