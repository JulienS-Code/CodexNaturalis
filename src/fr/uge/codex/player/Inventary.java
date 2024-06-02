package fr.uge.codex.player;

import java.util.NoSuchElementException;

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
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
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
		if (animal - nb < 0) {
			throw new NoSuchElementException("Missing resource");
		}
		inkwell -= nb;
	}
	
	// Score
	public int score() {
		return score;
	}
	// TODO Fonction de score grâce au Scoring de chaque carte
	
}
