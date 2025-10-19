package strategy;

import model.Personagem;

// Classe abstrata base para implementação de armas, implementa comportamentos comuns
public abstract class ArmaBase implements Arma {
    protected final String nome;
    protected final int danoBase;
    protected final int custoMana;
    protected final String descricaoEfeito;

    public ArmaBase(String nome, int danoBase, int custoMana, String descricaoEfeito) {
        this.nome = nome;
        this.danoBase = danoBase;
        this.custoMana = custoMana;
        this.descricaoEfeito = descricaoEfeito;
    }

    @Override
    public int getDanoBase() {
        return danoBase;
    }

    @Override
    public int getCustoMana() {
        return custoMana;
    }

    @Override
    public String getNome() {
        return nome;
    }

    // Calcula o dano crítico (chance de 15% de dar dano dobrado)
    protected int calcularDanoCritico(int danoBase) {
        if (Math.random() < 0.15) {
            System.out.println("  - Crítico");
            return danoBase * 2;
        }
        return danoBase;
    }

    // Verifica se o atacante tem mana suficiente
    protected boolean verificarMana(Personagem atacante) {
        if (!atacante.consumirMana(custoMana)) {
            return false;
        }
        return true;
    }
}

