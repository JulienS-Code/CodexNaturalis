# Codex Naturalis

## Description

Codex est un jeu de collection de cartes où les joueurs collectent diverses ressources et artefacts pour accumuler des points et gagner la partie. Le jeu combine stratégie et gestion des ressources, avec un mécanisme de score basé sur les cartes collectées.

## Objectif du Jeu

Le but du jeu est de collecter le maximum de points en accumulant des cartes représentant des ressources naturelles (Animaux, Plantes, Champignons, Insectes) et des artefacts (Plume, Manuscrit, Encrier). Les joueurs doivent également gérer leurs ressources pour pouvoir acquérir des cartes dorées.

## Règles du Jeu

### Mise en Place

- Chaque joueur reçoit un inventaire initial avec des compteurs pour chaque type de ressource et artefact.
- Le jeu commence avec un deck de cartes mélangé contenant des cartes de ressources, des cartes de départ, des cartes dorées et des cartes de scoring.

### Types de Cartes

- **Cartes de Ressources** : Fournissent des ressources naturelles.
- **Cartes de Départ** : Fournissent des ressources de départ spécifiques.
- **Cartes Dorées** : Fournissent des points et des ressources, mais nécessitent des coûts en ressources pour être acquises.
- **Cartes de Scoring** : Appliquent des conditions spécifiques pour accumuler des points.

### Tour de Jeu

1. **Pioche** : Chaque joueur pioche une carte du deck.
2. **Ajout à l'Inventaire** : Les joueurs ajoutent les ressources de la carte piochée à leur inventaire.
3. **Gestion des Ressources** : Les joueurs peuvent utiliser leurs ressources pour acquérir des cartes dorées.
4. **Calcul des Points** : Les points sont calculés en fonction des cartes dans l'inventaire et des conditions de scoring.

### Fin de Partie

Le jeu se termine lorsque toutes les cartes du deck ont été piochées. Le joueur avec le score le plus élevé gagne la partie.

## Structure du Projet

```
src/fr/uge/codex : Contient les fichiers sources du jeu.
fr/uge/codex/deck/card : Contient les classes de définition des cartes.
fr/uge/codex/player : Contient les classes de gestion des joueurs et de leurs inventaires.
bin : Dossier de sortie pour les fichiers compilés.
```