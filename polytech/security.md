---
marp: true
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
![bg right:60%](assets/spring-security-filters.png)

### Spring Security

- Architecture
    - Suite de filtre (Exemple : Basic, Digest, Custom)
    - Sortir de la FilterChainProxy avec un contexte d'authentification

---

## Filter*

[javax.servlet.Filter](https://docs.oracle.com/javaee/6/api/javax/servlet/Filter.html)

- Interface qui ne provient pas de spring
- Ne concerne pas uniquement http
- Argument :
    - ServletRequest request : Les données de la requetes
    - ServletResponse response : Les données de la reponse
    - FilterChain chain : Le prochain filtre a appeler avec ServletRequest et ServletResponse (Potentiellement d'autre que ceux en entrée)
- Implementation à connaitre:
    - GenericFilterBean
    - OncePerRequestFilter

---

### Activer Spring security

Ajout de la dépendance dans le build.gradle

```
  implementation 'org.springframework.boot:spring-boot-starter-security'
```

... et c'est tout

---

### Configurer spring security*

- Ajouter une SecurityFilterChain avec le builder HttpSecurity

```java
    @Bean
public SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception{
        http
        .authorizeHttpRequests((authz)->authz.anyRequest().authenticated()) //On demande que toute les sessions soit authentifiée
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//On rend les session stateless
        
        return http.build();
        }

```

---

### Pourquoi rendre la session stateless*

- Spring security active par defaut un token qui permet de rester authentifié
- Ce comportement est problematique si plusieurs backends sont deployée car les token sont en mémoire

---

### Exemple avec une partie de l'api ouverte, l'autre authentifiée

```java 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http.authorizeHttpRequests((authz) -> authz
                        .mvcMatchers("/public").permitAll()
                        .mvcMatchers("/private").authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//On rend les session stateless
}
```

---

### Liée Spring security à nos utilisateur

- Implementer UserDetailService :
    - Interface qui permet d'acceder au information d'un utilisateur ()

---

### Limiter les accés aux pages dans angular

- Ajouter des guards (interface: CanActivate) dans le routing :
    - Fait une verification avant d'acceder à une page
    - Sinon redirige vers une autre
    - Cas frequent : redirection vers la page de login
- Exemple : auth.guard.ts | app-routing.module.ts |

---

### Ajouter les headers à toute les requetes

- Ajouter des interceptors
    - Implementer l'interface `HttpInterceptor`
    - Ajouter dans le module un provider :
  ```json
   {
    provide: HTTP_INTERCEPTORS,
    useClass: HeaderInterceptor,
    multi: true
    }
  ``` 
- Exemple : header.interceptor.ts | app.component.ts 

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
    - La Gestion du Certificat est souvent compliqué
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
        - la signature -> `hash(base64UrlEncode(header) + "." + base64UrlEncode(payload), unSecret)`

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
```

```
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
    - L'authentification auprés de l'identity provider peut être renforcée sans gener l'ux
    - Compatible avec annuaires d'entreprise (LDAP/AD)

---

### Les Authentifications forte

Cumule les authentifications

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

### Gestion de roles Spring

- Utilisation de UserDetails#getAuthorithies > List des roles de l'utilisateur
- Dans le builder utiliser `.hasRole()` (Voir les exemples)
- Sur les methodes/Classe l'annotation `@RolesAllowed/@Secured`

---

### Mise en place dans la conf global*

Exemple : 5-role

```java
    @Bean
public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
        .authorizeHttpRequests((authz)->
        authz.antMatchers(HttpMethod.GET,"/**").authenticated()
        .antMatchers(HttpMethod.POST,"/**").hasAuthority("ADMIN"))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//On rend les session stateless
        return http.build();
        }

```

---

### Hierarchie de role

Ajouter un bean [Documentation Spring](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html#authz-hierarchical-roles)

```java
@Bean
AccessDecisionVoter hierarchyVoter(){
        RoleHierarchy hierarchy=new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAFF\n"+
        "ROLE_STAFF > ROLE_USER\n"+
        "ROLE_USER > ROLE_GUEST");
        return new RoleHierarchyVoter(hierarchy);
        }
```

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

### Mise en place d'abac dans spring*

- Utiliser les annotations @PreAuthorize
- Implementer un AccessDecisionManager (C'est compliqué)

---

### Rbac vs Abac

![Calin](assets/calin.gif)

- Gestion des authorization large avec les roles
- On affine les droits avec les attributs

---

### OAuth

- Protocole d'Autorisation
- Les acteurs - Exemple
    - Resource Provider - Gmail
    - Resource owner - martin.dupont@gmail.com
    - Authorization serveur - Google
    - Application - FishApp

---

### Oauth sequence

![Oauth](./assets/oauth2.png)
---

### Information dans le token jwt*

- sub:
- email:
- exp:
- name:
- iss
  -...

---

### Discovery service

Auto decouverte de la configuration openID
${issuer_url}/.well-known/openid-configuration
---

### OpenId Connect (OIDC)

- Protocole d'authentification
    - Basé sur Oauth
    - Principe similaire à OAuth
    - L'authorization serveur va partager les informations de l'utilisateur dans le token

---

## Accounting

- Suivre les actions de l'utilisateur
    - Pour des besoins d'audit
    - Via des logs
    - Via des informations en base
    - Dans certain contexte c'est une obligation

---
