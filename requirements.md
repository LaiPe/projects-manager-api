# TP : API de gestion de tâches

### 🎯 Objectif

Créer une API REST avec Spring Boot pour gérer des utilisateurs, projets et tâches. Les tests doivent être effectués via un fichier `test.http`.

---

### 📚 Fonctionnalités attendues

### 🔹 Utilisateur

- `POST /users` → Créer un utilisateur
- `GET /users/{id}` → Afficher un utilisateur
- `GET /users/{id}/projects` → Projets créés par l’utilisateur
- `GET /users/{id}/tasks` → Tâches assignées à l’utilisateur

### 🔹 Projet

- `POST /projects` → Créer un projet (avec ID du créateur)
- `GET /projects/{id}` → Détails d’un projet avec ses tâches

### 🔹 Tâche

- `POST /tasks` → Créer une tâche (avec ID du projet et de l’utilisateur assigné)
- `PATCH /tasks/{id}` → Modifier le statut (Enum : `TODO`, `IN_PROGRESS`, `DONE`)
- `GET /projects/{id}/tasks` → Lister les tâches d’un projet

---

### 🗃️ Données attendues (structure minimale)

```java
enum TaskStatus {
    TODO, IN_PROGRESS, DONE
}

class User {
    Long id;
    String username;
}

class Project {
    Long id;
    String name;
    User creator;
}

class Task {
    Long id;
    String title;
    TaskStatus status;
    Project project;
    User assignee;
}

```

---

### 🧪 Test requis

Créer un fichier `test.http` à la racine du projet, contenant des requêtes pour :

- Créer un utilisateur, un projet, une tâche
- Changer le statut d’une tâche
- Vérifier les endpoints de consultation

---

### 🛡️ Bonus : Sécurité

1. Mettre en place une authentification simple :
    - Utilisateur avec login / mot de passe
    - `POST /auth/login` → Retourne un token (JWT)
    - Protection des routes sauf `POST /auth/login` et `POST /users`
2. Ajouter un `Authorization: Bearer xxx` dans les requêtes sécurisées (si vous êtes à l’aise, n’hésitez pas à créer des fichiers avec variables et récupération dynamique comme j’ai pu le montrer par moments)