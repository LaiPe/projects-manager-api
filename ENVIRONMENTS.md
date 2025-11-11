# Guide des Environnements d'Exécution

Ce projet supporte trois environnements d'exécution distincts :

## 1. 🛠️ Environnement de Développement (dev)

**Base de données :** H2 en mémoire  
**Exécution :** Locale

### Lancement
```bash
# Via Maven avec profil
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# (Nécessite des guillements sur PowerShell)
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"

# Via IntelliJ/Eclipse
# Définir VM options: -Dspring-boot.run.profiles=dev
```

### Caractéristiques
- Base de données H2 en mémoire (se remet à zéro à chaque redémarrage)
- Console H2 accessible : http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:devdb`
  - User: `sa`
  - Password: (vide)
- Logs détaillés pour le debug
- DDL auto: `create-drop`

---

## 2. 🧪 Environnement de Pré-production (preprod)

**Base de données :** MySQL via conteneur Docker  
**Exécution :** Application buildée en conteneur Docker

### Prérequis
1. Créer le fichier `.env.prod` basé sur `.env.prod.example`
2. Entrer des mots de passes et configurations **factices**

### Lancement
```bash
# Construire et lancer via Docker Compose
docker-compose --env-file .env.prod up --build

# En arrière-plan
docker-compose --env-file .env.prod up -d --build

# Arrêter
docker-compose down
```

### Caractéristiques
- MySQL 8.4 dans un conteneur Docker
- Application buildée dans un conteneur Docker
- Simulation de configuration sécurisée via fichier .env.prod
- Base de données persistante (volume Docker)
- DDL auto: `update`
- Health checks configurés

---

## 3. 🚀 Environnement de Production (prod)

**Base de données :** MySQL via conteneur Docker  
**Exécution :** Image Docker depuis GitHub Container Registry

### Prérequis
1. Créer le fichier `.env.prod` basé sur `.env.prod.example`
2. Personnaliser les mots de passe et configurations

### Lancement
```bash
# Lancer avec Docker Compose
docker-compose --env-file .env.prod -f docker-compose.prod.yml up -d

# Arrêter
docker-compose --env-file .env.prod -f docker-compose.prod.yml down
```

### Caractéristiques
- MySQL 8.4 optimisé pour la production
- Image de l'application depuis GHCR
- Configuration sécurisée via fichier .env.prod
- DDL auto: `update`
- Logs minimaux
- Health checks configurés
- Connection pool optimisé

---

## 📋 Contenu de `.env.prod`

| Variable | Description |
|----------|-------------|
| `MYSQL_ROOT_PASSWORD` | Mot de passe administrateur MySQL |
| `MYSQL_DATABASE` | Nom de la base de données |
| `MYSQL_USER` | Utilisateur MySQL pour l'application |
| `MYSQL_PASSWORD` | Mot de passe de l'utilisateur MySQL |
| `DB_HOST` | Nom du conteneur MySQL |
| `DB_PORT` | Port de connexion MySQL |
| `DB_NAME` | Nom de la base de données |
| `DB_USER` | Utilisateur pour Spring Boot |
| `DB_PASSWORD` | Mot de passe pour Spring Boot |
| `JWT_SECRET` | Clé secrète pour signer les tokens JWT |
| `CORS_ALLOWED_ORIGINS` | Origines autorisées (séparées par virgules) |

### 🔐 Génération de mots de passe sécurisés :

````bash
# Générer une clé JWT sécurisée
openssl rand -base64 32

# Ou avec PowerShell
[System.Convert]::ToBase64String([System.Security.Cryptography.RandomNumberGenerator]::GetBytes(32))
````

## ⚠️ Notes importantes

1. **Sécurité** : Ne jamais commiter le fichier `.env.prod`, ne rentrez pas d'informations sensibles en pré-prod
2. **Données** : L'environnement dev remet à zéro les données à chaque redémarrage
3. **Performance** : L'environnement prod utilise un pool de connexions optimisé
4. **Logs** : Les logs sont plus verbeux en dev, minimaux en prod