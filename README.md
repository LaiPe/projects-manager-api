# Projects Manager API

## Contexte

Projects Manager API est une application Spring Boot permettant de gérer des projets, des tâches et des utilisateurs. Elle expose une API REST pour la création, la modification, la suppression et la consultation de projets et de tâches, avec une gestion des statuts et des utilisateurs associés. Elle utilise une base de données MySQL.

## Utilisation en développement

1. **Prérequis**
   - Java 21
   - Maven
   - Docker et Docker Compose

2. **Lancer l'environnement de développement**

   - Clone le dépôt :
     ```sh
     git clone <repo_url>
     cd projects-manager-api
     ```
   - Lance les services (API + MySQL) :
     ```sh
     docker compose up --build
     ```
   - L'API est accessible sur : [http://localhost:8080](http://localhost:8080)
   - La configuration par défaut (dev) se trouve dans `docker-compose.yml` et utilise des variables non sensibles.

3. **Tests**
   - Pour lancer les tests :
     ```sh
     mvn test
     ```

## Déploiement en production

1. **Préparer les fichiers de configuration**
   - Renseigner les variables sensibles dans un fichier `.env.prod` (non versionné, à placer sur le serveur).
   - Exemple de variables à définir :
     ```env
     MYSQL_ROOT_PASSWORD=...
     MYSQL_DATABASE=...
     MYSQL_USER=...
     MYSQL_PASSWORD=...
     DB_HOST=mysql
     DB_PORT=3306
     DB_NAME=...
     DB_USER=...
     DB_PASSWORD=...
     ```

2. **Déployer avec Docker Compose**
    - Le pipeline CI/CD copie automatiquement `docker-compose.prod.yml` sur le serveur dans le dossier de l'app.
    - Vous n'avez donc rien à copier manuellement si vous utilisez le pipeline.
    - Pour un déploiement manuel, copiez ces deux fichiers puis lancez :
       ```sh
       docker compose -f docker-compose.prod.yml --env-file .env.prod up -d
       ```

3. **CI/CD**
   - Le pipeline GitHub Actions build, push l'image sur GHCR, copie les fichiers nécessaires et déploie automatiquement sur le VPS.

## Sécurité
- Ne versionnez jamais `.env.prod` ou tout fichier contenant des secrets.
- Les accès à la base et à l'API doivent être protégés en production.

---

Pour toute question ou contribution, ouvrez une issue ou une pull request sur le dépôt.
