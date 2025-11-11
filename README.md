# Projects Manager API

## Contexte

Projects Manager API est une application Spring Boot permettant de gérer des projets, des tâches et des utilisateurs. Elle expose une API REST pour la création, la modification, la suppression et la consultation de projets et de tâches, avec une gestion des statuts et des utilisateurs associés.

## 🚀 Environnements d'exécution

Cette application supporte **trois environnements distincts** :

- **🛠️ Développement** : Base de données H2 en mémoire, exécution locale
- **🧪 Pré-production** : MySQL via Docker, application conteneurisée
- **🚀 Production** : MySQL optimisé, image depuis GitHub Container Registry

📖 **[Guide détaillé des environnements](ENVIRONMENTS.md)**

## Démarrage rapide

### Développement local (H2)
```sh
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# (Avec Powershell)
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```
Console H2 : http://localhost:8080/h2-console

### Pré-production (Docker)
```sh
docker-compose up --build
```

### Production
```sh
# 1. Configurer .env.prod basé sur .env.prod.example
# 2. Lancer
docker-compose -f docker-compose.prod.yml up -d
```

## 📋 API Endpoints

### 🔐 Authentification (`/api/auth`)
| Méthode | Endpoint | Description | Authentification | JSON Body |
|---------|----------|-------------|------------------|-----------|
| `POST` | `/api/auth/login` | Connexion utilisateur | ❌ | `{"username": "john", "password": "password123"}` |
| `POST` | `/api/auth/register` | Inscription utilisateur | ❌ | `{"username": "john", "password": "password123"}` |
| `GET` | `/api/auth/verify` | Vérification du token | ✅ | - |
| `POST` | `/api/auth/logout` | Déconnexion utilisateur | ✅ | - |

### 👥 Utilisateurs (`/api/users`)
| Méthode | Endpoint | Description | Authentification | JSON Body |
|---------|----------|-------------|------------------|-----------|
| `POST` | `/api/users` | ~~Créer utilisateur~~ (deprecated) | ❌ | - |
| `GET` | `/api/users/{id}` | Récupérer un utilisateur | ✅ | - |
| `GET` | `/api/users/{id}/projects` | Projets d'un utilisateur | ✅ (propriétaire) | - |
| `GET` | `/api/users/{id}/tasks` | Tâches assignées à un utilisateur | ✅ (propriétaire) | - |

### 🗂️ Projets (`/api/projects`)
| Méthode | Endpoint | Description | Authentification | JSON Body |
|---------|----------|-------------|------------------|-----------|
| `POST` | `/api/projects` | Créer un projet | ✅ (créateur) | `{"name": "Mon Projet", "creatorId": 1}` |
| `GET` | `/api/projects/{id}` | Récupérer un projet | ✅ | - |
| `GET` | `/api/projects/{id}/tasks` | Tâches d'un projet | ✅ (créateur) | - |
| `PATCH` | `/api/projects/{id}` | Modifier un projet | ✅ (créateur) | `{"name": "Nouveau nom", "creatorId": 1}` |
| `DELETE` | `/api/projects/{id}` | Supprimer un projet | ✅ (créateur) | - |

### ✅ Tâches (`/api/tasks`)
| Méthode | Endpoint | Description | Authentification | JSON Body |
|---------|----------|-------------|------------------|-----------|
| `POST` | `/api/tasks` | Créer une tâche | ✅ (créateur du projet) | `{"title": "Ma tâche", "status": "TODO", "projectId": 1, "assigneeId": 2}` |
| `PATCH` | `/api/tasks/{id}/status` | Modifier le statut d'une tâche | ✅ (créateur ou assigné) | `{"status": "IN_PROGRESS"}` |
| `PATCH` | `/api/tasks/{id}` | Modifier une tâche | ✅ (créateur du projet) | `{"title": "Nouveau titre", "assigneeId": 3}` |
| `DELETE` | `/api/tasks/{id}` | Supprimer une tâche | ✅ (créateur du projet) | - |

### 📝 Légende
- **✅ Authentification requise** : Token JWT requis via cookie `access_token`
- **❌ Public** : Accessible sans authentification
- **🔒 Autorisations** :
  - *Créateur* : Seul le créateur du projet peut effectuer l'action
  - *Propriétaire* : Seul le propriétaire du compte peut effectuer l'action  
  - *Assigné* : L'utilisateur assigné à la tâche peut effectuer l'action
- **📊 Statuts des tâches** : `TODO`, `IN_PROGRESS`, `DONE`

## Sécurité
- Ne versionnez jamais `.env.prod` ou tout fichier contenant des secrets.
- Les accès à la base et à l'API doivent être protégés en production.

---

Pour toute question ou contribution, ouvrez une issue ou une pull request sur le dépôt.
