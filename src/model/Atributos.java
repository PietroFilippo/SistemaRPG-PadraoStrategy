package model;

// Classe que representa os atributos de um personagem
public class Atributos {
    private final int forca;
    private final int destreza;
    private final int inteligencia;

    public Atributos(int forca, int destreza, int inteligencia) {
        this.forca = forca;
        this.destreza = destreza;
        this.inteligencia = inteligencia;
    }

    public int getForca() {
        return forca;
    }

    public int getDestreza() {
        return destreza;
    }

    public int getInteligencia() {
        return inteligencia;
    }
}

