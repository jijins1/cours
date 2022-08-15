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
---
### Certificat
---
### Token
  - Token Signé
  - Token Enregistré
---
### Jwt

---
## Authorization
---
## Accounting 
---
