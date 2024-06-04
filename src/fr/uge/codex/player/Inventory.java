package fr.uge.codex.player;

import java.util.Objects;
import fr.uge.codex.deck.card.ArtifactType;
import fr.uge.codex.deck.card.Card;
import fr.uge.codex.deck.card.CornerType;
import fr.uge.codex.deck.card.GoldCard;
import fr.uge.codex.deck.card.ResourceCard;
import fr.uge.codex.deck.card.ResourceType;
import fr.uge.codex.deck.card.Scoring;
import fr.uge.codex.deck.card.StarterCard;

public class Inventory {
    private int animal;
    private int plant;
    private int fungi;
    private int insect;
    private int quill;
    private int manuscript;
    private int inkwell;
    private int score;

    public Inventory() {
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

    public boolean removeNbAnimal(int nb) {
        if (animal - nb < 0) {
            return false;
        }
        animal -= nb;
        return true;
    }

    // Plant
    public int plant() {
        return plant;
    }

    public void addNbPlant(int nb) {
        plant += nb;
    }

    public boolean removeNbPlant(int nb) {
        if (plant - nb < 0) {
            return false;
        }
        plant -= nb;
        return true;
    }

    // Fungi
    public int fungi() {
        return fungi;
    }

    public void addNbFungi(int nb) {
        fungi += nb;
    }

    public boolean removeNbFungi(int nb) {
        if (fungi - nb < 0) {
            return false;
        }
        fungi -= nb;
        return true;
    }

    // Insect
    public int insect() {
        return insect;
    }

    public void addNbInsect(int nb) {
        insect += nb;
    }

    public boolean removeNbInsect(int nb) {
        if (insect - nb < 0) {
            return false;
        }
        insect -= nb;
        return true;
    }

    // Quill
    public int quill() {
        return quill;
    }

    public void addNbQuill(int nb) {
        quill += nb;
    }

    public boolean removeNbQuill(int nb) {
        if (quill - nb < 0) {
            return false;
        }
        quill -= nb;
        return true;
    }

    // Manuscript
    public int manuscript() {
        return manuscript;
    }

    public void addNbManuscript(int nb) {
        manuscript += nb;
    }

    public boolean removeNbManuscript(int nb) {
        if (manuscript - nb < 0) {
            return false;
        }
        manuscript -= nb;
        return true;
    }

    // Inkwell
    public int inkwell() {
        return inkwell;
    }

    public void addNbInkwell(int nb) {
        inkwell += nb;
    }

    public boolean removeNbInkwell(int nb) {
        if (inkwell - nb < 0) {
            return false;
        }
        inkwell -= nb;
        return true;
    }

    // Score
    public int score() {
        return score;
    }

    public void updateInventory(Card card) {
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
                for (CornerType corner : resourceCard.getRecto()) {
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
                    if (!removeResource(cost, 1)) {
                        System.out.println("Not enough resources to cover cost");
                        // Handle the insufficient resource case, e.g., by rolling back changes or other logic
                    }
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
    private boolean removeResource(CornerType corner, int count) {
        if (corner instanceof ResourceType) {
            return removeResource((ResourceType) corner, count);
        } else if (corner instanceof ArtifactType) {
            return removeResource((ArtifactType) corner, count);
        }
        return false;
    }

    // Suppression de ressource (Plant, Animal, Insect, Fungi)
    private boolean removeResource(ResourceType resource, int count) {
        switch (resource) {
            case Animal -> {
                return removeNbAnimal(count);
            }
            case Plant -> {
                return removeNbPlant(count);
            }
            case Fungi -> {
                return removeNbFungi(count);
            }
            case Insect -> {
                return removeNbInsect(count);
            }
        }
        return false;
    }

    // Suppression d'artifact (Inkwell, Manuscript, Quill)
    private boolean removeResource(ArtifactType artifact, int count) {
        switch (artifact) {
            case Quill -> {
                return removeNbQuill(count);
            }
            case Manuscript -> {
                return removeNbManuscript(count);
            }
            case Inkwell -> {
                return removeNbInkwell(count);
            }
        }
        return false;
    }

    // Score
    private void calculateScore(Scoring scoring) {
        /* TODO Calculer score 
         * D -> direct : score += count
         * C -> combien de corner recouvert ? : score += nbCorner * scoring.points()
         * Q / I / M -> nbArtefactZoneDeJeu + ArtefactSurCard 
        */
        switch (scoring.scoringType()){
            case DIRECT -> score += scoring.points();
            case BY_ARTIFACT -> {
                switch (scoring.artifactType()) {
                    case Inkwell, Manuscript, Quill -> {
                        addNbQuill(1);
                        score += scoring.points(); /*  * nbArtefact(zone+card)  */
                    }
                }
            }
            case BY_CORNER, NONE -> {}
            default -> {}
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Inventory = {animal : ");
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
