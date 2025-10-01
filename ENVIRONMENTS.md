# Guide des Environnements d'Exécution

Ce projet supporte trois environnements d'exécution distincts :

## 1. 🛠️ Environnement de Développement (dev)

**Base de données :** H2 en mémoire  
**Exécution :** Locale

### Lancement
```bash
# Option 1: Via variable d'environnement (Windows PowerShell)
$env:SPRING_PROFILES_ACTIVE="dev"; mvn spring-boot:run

# Option 2: Via Maven avec profil
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# (Nécessite des guillements sur PowerShell)
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"

# Option 3: Via variable d'environnement (Windows CMD)
set SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run

# Option 4: Via IntelliJ/Eclipse
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

### Lancement
```bash
# Construire et lancer via Docker Compose
docker-compose up --build

# En arrière-plan
docker-compose up -d --build

# Arrêter
docker-compose down
```

### Caractéristiques
- MySQL 8.4 dans un conteneur Docker
- Application buildée dans un conteneur Docker
- Base de données persistante (volume Docker)
- DDL auto: `update`
- Configuration proche de la production

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
docker-compose -f docker-compose.prod.yml up -d

# Arrêter
docker-compose -f docker-compose.prod.yml down
```

### Caractéristiques
- MySQL 8.4 optimisé pour la production
- Image de l'application depuis GHCR
- Configuration sécurisée via fichier .env.prod
- DDL auto: `validate` (ne modifie pas la structure)
- Logs minimaux
- Health checks configurés
- Connection pool optimisé

---

## 📋 Configuration des Profils

### Fichiers de configuration
- `application.properties` : Configuration par défaut
- `application-dev.properties` : Profil développement (H2)
- `application-preprod.properties` : Profil pré-production (MySQL)
- `application-prod.properties` : Profil production (MySQL optimisé)

### Variables d'environnement importantes
- `SPRING_PROFILES_ACTIVE` : Définit le profil actif
- `DB_HOST`, `DB_PORT`, `DB_NAME` : Configuration base de données
- `DB_USER`, `DB_PASSWORD` : Identifiants base de données

---

## 🐛 Debug et Monitoring

### Environnement de développement
- Console H2 : http://localhost:8080/h2-console
- Logs SQL détaillés
- Hot reload avec Spring DevTools

### Environnements conteneurisés
```bash
# Voir les logs de l'application
docker-compose logs app

# Logs en temps réel
docker-compose logs -f app

# Accéder au conteneur
docker-compose exec app bash
```

---

## 🔄 Migration entre environnements

### De dev vers preprod
1. Tester les migrations de base de données
2. Vérifier que toutes les données de test s'insèrent correctement
3. Valider les performances

### De preprod vers prod
1. Créer/mettre à jour le fichier `.env.prod`
2. Effectuer les migrations de base de données nécessaires
3. Deployer l'image sur GHCR
4. Lancer en production

---

## ⚠️ Notes importantes

1. **Sécurité** : Ne jamais commiter le fichier `.env.prod`
2. **Données** : L'environnement dev remet à zéro les données à chaque redémarrage
3. **Performance** : L'environnement prod utilise un pool de connexions optimisé
4. **Logs** : Les logs sont plus verbeux en dev, minimaux en prod