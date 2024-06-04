package fr.uge.codex.player;

import java.util.NoSuchElementException;
import java.util.Objects;

import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;
import fr.uge.codex.deck.card.ScoringType;
import fr.uge.codex.deck.card.StarterCard;

public class Inventary {
    private int animal;
    private int plant;
    private int fungi;
    private int insect;
    private int quill;
    private int manuscript;
    private int inkwell;
    private int score;

    public Inventary() {
        this.animal = 0;
        this.plant = 0;
        this.fungi = 0;
        this.insect = 0;
        this.quill = 0;
        this.manuscript = 0;
        this.inkwell = 0;
    }

    // Animal
    public int animal() {
        return animal;
    }

    public void addNbAnimal(int nb) {
        animal += nb;
    }

    public void removeNbAnimal(int nb) {
        if (animal - nb < 0) {
            throw new NoSuchElementException("Missing resource Animal");
        }
        animal -= nb;
    }

    // Plant
    public int plant() {
        return plant;
    }

    public void addNbPlant(int nb) {
        plant += nb;
    }

    public void removeNbPlant(int nb) {
        if (plant - nb < 0) {
            throw new NoSuchElementException("Missing resource Plant");
        }
        plant -= nb;
    }

    // Fungi
    public int fungi() {
        return fungi;
    }

    public void addNbFungi(int nb) {
        fungi += nb;
    }

    public void removeNbFungi(int nb) {
        if (fungi - nb < 0) {
            throw new NoSuchElementException("Missing resource Fungi");
        }
        fungi -= nb;
    }

    // Insect
    public int insect() {
        return insect;
    }

    public void addNbInsect(int nb) {
        insect += nb;
    }

    public void removeNbInsect(int nb) {
        if (insect - nb < 0) {
            throw new NoSuchElementException("Missing resource Insect");
        }
        insect -= nb;
    }

    // Quill
    public int quill() {
        return quill;
    }

    public void addNbQuill(int nb) {
        quill += nb;
    }

    public void removeNbQuill(int nb) {
        if (quill - nb < 0) {
            throw new NoSuchElementException("Missing artifact Quill");
        }
        quill -= nb;
    }

    // Manuscript
    public int manuscript() {
        return manuscript;
    }

    public void addNbManuscript(int nb) {
        manuscript += nb;
    }

    public void removeNbManuscript(int nb) {
        if (manuscript - nb < 0) {
            throw new NoSuchElementException("Missing artifact Manuscript");
        }
        manuscript -= nb;
    }

    // Inkwell
    public int inkwell() {
        return inkwell;
    }

    public void addNbInkwell(int nb) {
        inkwell += nb;
    }

    public void removeNbInkwell(int nb) {
        if (inkwell - nb < 0) {
            throw new NoSuchElementException("Missing artifact Inkwell");
        }
        inkwell -= nb;
    }

    // Score
    public int score() {
        return score;
    }

    public void addCardToInventary(Card card) {
        Objects.requireNonNull(card);
        
        System.out.println(card);
        if (card.turned()) {
            // Gérer le verso de la carte
            if (card instanceof StarterCard) {
                StarterCard starterCard = (StarterCard) card;
                for (ResourceType resource : starterCard.resources()) {
                    addResource(resource, 1);
                }
            } else {
                addResource(card.getKingdom(), 1);
            }
        } else {
            // Ajout des ressources du recto
            if (card instanceof StarterCard) {
                StarterCard starterCard = (StarterCard) card;
                for (ResourceType resource : (ResourceType[]) starterCard.getRecto()) {
                    addResource(resource, 1);
                }
            } else if (card instanceof ResourceCard) {
                ResourceCard resourceCard = (ResourceCard) card;
                for (CornerType corner : resourceCard.recto()) {
                    addResource(corner, 1);
                }
                score += resourceCard.score().points();
            } else if (card instanceof GoldCard) {
                GoldCard goldCard = (GoldCard) card;
                for (CornerType corner : goldCard.getRecto()) {
                    addResource(corner, 1);
                }
                score += goldCard.score().points();
                // Soustraire les ressources de cost
                for (CornerType cost : goldCard.cost()) {
                    removeResource(cost, 1);
                }
            }
        }
        // TODO Ajouter le score de la carte à l'inventaire
//        score += card.getScore().points();
        System.out.println(this);
    }

    // Ajout de ressource (Plant, Animal, Insect, Fungi)
    private void addResource(ResourceType resource, int count) {
        switch (resource) {
            case Animal -> addNbAnimal(count);
            case Plant -> addNbPlant(count);
            case Fungi -> addNbFungi(count);
            case Insect -> addNbInsect(count);
        }
    }

    // Ajout d'artifact (Inkwell, Manuscript, Quill)
    private void addResource(ArtifactType artifact, int count) {
        switch (artifact) {
            case Quill -> addNbQuill(count);
            case Manuscript -> addNbManuscript(count);
            case Inkwell -> addNbInkwell(count);
        }
    }

    // Gestion de tout type de corner
    private void addResource(CornerType corner, int count) {
        if (corner instanceof ResourceType) {
            addResource((ResourceType) corner, count);
        } else if (corner instanceof ArtifactType) {
            addResource((ArtifactType) corner, count);
        }
    }

    // Suppression de tout type de corner
    private void removeResource(CornerType corner, int count) {
        if (corner instanceof ResourceType) {
            removeResource((ResourceType) corner, count);
        } else if (corner instanceof ArtifactType) {
            removeResource((ArtifactType) corner, count);
        }
    }

    // Suppression de ressource (Plant, Animal, Insect, Fungi)
    private void removeResource(ResourceType resource, int count) {
        switch (resource) {
            case Animal -> removeNbAnimal(count);
            case Plant -> removeNbPlant(count);
            case Fungi -> removeNbFungi(count);
            case Insect -> removeNbInsect(count);
        }
    }

    // Suppression d'artifact (Inkwell, Manuscript, Quill)
    private void removeResource(ArtifactType artifact, int count) {
        switch (artifact) {
            case Quill -> removeNbQuill(count);
            case Manuscript -> removeNbManuscript(count);
            case Inkwell -> removeNbInkwell(count);
        }
    }

    // Score
    private void calculateScore(Scoring scoring) {
        /* TODO Calculer score 
         * D -> direct : score += count
         * C -> combien de corner recouvert ? : score += nbCorner * scoring.points()
         * Q / I / M -> nbArtefactZoneDeJeu + ArtefactSurCard 
        */
        switch (scoring.scoringType()){
            case DIRECT:
                score += scoring.points();
            case BY_ARTIFACT:
                switch (scoring.artifactType()) {
                    case Inkwell:
                    case Manuscript:
                    case Quill:
                        addNbQuill(1);
                        score += scoring.points() /*  * nbArtefact(zone+card)  */ ;
                }
            case BY_CORNER:
            case NONE:
            default:
                
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Inventary = {animal :");
        builder.append(animal);
        builder.append(", plant : ");
        builder.append(plant);
        builder.append(", fungi : ");
        builder.append(fungi);
        builder.append(", insect : ");
        builder.append(insect);
        builder.append(", quill : ");
        builder.append(quill);
        builder.append(", manuscript : ");
        builder.append(manuscript);
        builder.append(", inkwell : ");
        builder.append(inkwell);
        builder.append(" et ");
        builder.append(score);
        builder.append(" points}");
        return builder.toString();
    }
}
