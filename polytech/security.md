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
### Ce que l'utilisateur Sait/Possede/Est 
- Sait : Un mot de passe, une question secrete
- Possede : Un telephone, un token rsa, un carte d'identite
- Est : Une empreinte, un visage
---
### Authentification par mot de passe 
Plusieurs normes :
 - Basic Auth -> Header
 - Digest Auth -> Header hashé
 - Form-based Auth -> Form Field (requete dedié)

---
### Basic Auth
  - Format
     - Dans le Header des requêtes
     - `Authorization: Basic name:mdp` (name:mdp -> en Base64)
     - Exemple : `Authorization: Basic cGluZ29vOjEyMzQ1` name: pingoo, mdp:1234
  - Avantage :
    - Correspond au format classique utilisateur/mdp
  - Extension:
    - WWW-Authenticate : Ajoute des information sur l'authentification
---
### Digest Auth*
 - Principe identique au basic auth mais le contenu est hashé
 - Format
   - `Authorization: Digest md5(name:mdp)`
 - Avantage : 
   - On envoie pas le mdp
 - Inconvenient 
   - Le serveur ne connait pas le mdp envoyé
   - Un peu obsolete avec https
---
### Gestion d'un mot de passe*
- On ne stock jamais un mdp (Exemple : linkedin,...)   [haveibeenpwned](https://haveibeenpwned.com/)
- On stock un hash du mdp
- On ajoute un salt dans le hash
- Exemple : BCrypt (Salt, inclut, non reversible)
---
### Certificat
  - Certificat Client : Different du certificat d'https (Qui est un certificat serveur)
  - Inconvenient :
    - Il faut bien penser à mettre à jour le Certificat
    - La Gestion du Certificat est souvent pas terrible
    - (Pas revocable)
- Exemple :
    - Api Kubernetes par defaut
---
### Token Enregistré
  - Avantage :
    - Les tokens sont revocable
  - Inconvenient
    - Il faut aller en base voir si le token est valide 
  - Usage :
    - Des api serveur - serveur avec une ui pour de management des tokens (exemple : github)
    - Des ui avec l'option "keep me connected" [facebook](le lien vers la page d'admin)
---
### Token Signé
  - Avantage 
    - Pas besoin d'une base
  - Inconvenient
    - Un utilisateur ne peut pas le retenir
---
### Jwt (JSON Web Token)*
- Format
    - 3 Parties en base 64
        - Le header -> Un json avec l'algo de signature
        - body -> des informations sur l'utilisateur
        - la signature -> hash(base64UrlEncode(header) + "." + base64UrlEncode(payload), unSecret)
---
### Jwt Exemple*
```json
{
"alg": "HS256",
"typ": "JWT"
}.{
"sub": "1234567890",
"name": "John Doe",
"iat": 1516239022
}.secret

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.PcmVIPbcZl9j7qFzXRAeSyhtuBnHQNMuLHsaG5l804A
``` 

---
## Single Sign One with SAML
- Security Assertion Markup Language
![saml](assets/saml.png)
---
## Single Sign One with SAML
- Avantages
  - Les utilisateurs ont un mot de passe sur un pool d'appli
  - Les utilisateurs ne s'authentifient qu'une fois sur un pool d'appli
  - L'authentification aupres de l'identity provider peut être renforcée sans gener l'ux
  - Compatible avec annuaires d'entreprise (LDAP/AD)
---
### Les Authentifications forte
On cumule les authentifications
- Avantages 
  - On réduit les risques
- Désavantages
  - L'experience utilisateur moins simple
Exemple : 
   - Jeton rsa 
   - Authenticator (google)
   - Generateur de token
   - Sms 
   - ...

## Authorization
---
### Rbac (Role based access control)
 - Gestion par role
    - Admin
    - Commercial
    - ...
 - Exemple d'api :
   - l'Api Kubernetes
---
### Abac (Attributes based access control)
 - Gestion par attribut
    - Localisation (Netflix)
    - Age (Disney +)
    - Relation (Facebook)
    - ...
 - Exemple d'api : 
   - ...
---
### Rbac vs Abac
![Calin](https://giphy.com/gifs/moodman-kids-race-friendship-VduFvPwm3gfGO8duNN) 
- Gestion des authorization large avec les roles
  - On affine les droits avec les attributs
---
### OAuth
- Protocole d'Autorisation
- Les acteurs
  - Resource Provider - Gmail
  - Resource owner - martin.dupont@gmail.com
  - Authorization serveur - Google
  - Application - FishApp

---
### OpenId Connect (OIDC)
- Protocole d'authentification
  - Basé sur Oauth
  - Principe similaire à OAuth
  - L'authorization serveur va partager les informations de l'utilisateur dans le token
---
### Oauth sequence
![Oauth](./assets/oauth2.png)
---
## Accounting 
---
