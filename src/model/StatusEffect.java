package model;

// Classe abstrata que representa um efeito de status
public abstract class StatusEffect {
    protected final String nome;
    protected int turnosRestantes;
    protected final int dano;

    public StatusEffect(String nome, int turnos, int dano) {
        this.nome = nome;
        this.turnosRestantes = turnos;
        this.dano = dano;
    }

    // Aplica o efeito em um personagem
    public abstract String aplicarEfeito(Personagem personagem);

    // Reduz o número de turnos restantes
    public void decrementarTurno() {
        if (turnosRestantes > 0) {
            turnosRestantes--;
        }
    }

    // Verifica se o efeito ainda está ativo
    public boolean isAtivo() {
        return turnosRestantes > 0;
    }

    public String getNome() {
        return nome;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public int getDano() {
        return dano;
    }
}

