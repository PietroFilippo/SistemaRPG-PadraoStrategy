package model;

import strategy.Arma;
import java.util.ArrayList;
import java.util.List;

// Classe abstrata que representa um personagem
public abstract class Personagem {
    protected final String nome;
    protected int vidaAtual;
    protected final int vidaMaxima;
    protected int manaAtual;
    protected final int manaMaxima;
    protected final Atributos atributos;
    protected Arma armaEquipada;
    protected final List<StatusEffect> efeitosAtivos;
    protected boolean atordoado;
    protected boolean desprevenido;

    public Personagem(String nome, int vidaMaxima, int manaMaxima, Atributos atributos) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.manaMaxima = manaMaxima;
        this.manaAtual = manaMaxima;
        this.atributos = atributos;
        this.efeitosAtivos = new ArrayList<>();
        this.atordoado = false;
        this.desprevenido = true; // Inicia desprevenido no começo da batalha
    }

    // Aplica habilidade passiva da classe
    public abstract String aplicarHabilidadePassiva();

    // Retorna os tipos de armas que o personagem pode usar
    public abstract List<String> getArmasPermitidas();

    // Equipa uma arma
    public boolean equiparArma(Arma arma) {
        if (arma.podeUsar(this)) {
            this.armaEquipada = arma;
            return true;
        }
        return false;
    }

    // Recebe dano
    public void receberDano(int dano) {
        int danoFinal = calcularDanoRecebido(dano);
        this.vidaAtual = Math.max(0, this.vidaAtual - danoFinal);
    }

    // Calcula o dano recebido após aplicar modificadores
    public abstract int calcularDanoRecebido(int dano);

    // Consome mana
    public boolean consumirMana(int quantidade) {
        if (this.manaAtual >= quantidade) {
            this.manaAtual -= quantidade;
            return true;
        }
        return false;
    }

    // Regenera mana
    public void regenerarMana(int quantidade) {
        this.manaAtual = Math.min(this.manaMaxima, this.manaAtual + quantidade);
    }

    // Adiciona um efeito de status
    public void adicionarEfeito(StatusEffect efeito) {
        this.efeitosAtivos.add(efeito);
    }

    // Processa efeitos ativos no início do turno
    public String processarEfeitos() {
        StringBuilder resultado = new StringBuilder();
        List<StatusEffect> efeitosParaRemover = new ArrayList<>();

        for (StatusEffect efeito : efeitosAtivos) {
            resultado.append(efeito.aplicarEfeito(this)).append("\n");
            efeito.decrementarTurno();
            
            if (!efeito.isAtivo()) {
                efeitosParaRemover.add(efeito);
                resultado.append(String.format("  - Efeito '%s' terminou!\n", efeito.getNome()));
            }
        }

        efeitosAtivos.removeAll(efeitosParaRemover);
        return resultado.toString();
    }

    // Verifica se está atordoado
    public boolean isAtordoado() {
        return atordoado;
    }

    public void setAtordoado(boolean atordoado) {
        this.atordoado = atordoado;
    }

    public boolean isDesprevenido() {
        return desprevenido;
    }

    public void setDesprevenido(boolean desprevenido) {
        this.desprevenido = desprevenido;
    }

    public boolean isVivo() {
        return vidaAtual > 0;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public int getVidaAtual() {
        return vidaAtual;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getManaAtual() {
        return manaAtual;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public Atributos getAtributos() {
        return atributos;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public List<StatusEffect> getEfeitosAtivos() {
        return new ArrayList<>(efeitosAtivos);
    }

    public String getStatus() {
        return String.format("%s | Vida: %d/%d | Mana: %d/%d | Arma: %s%s",
                nome,
                vidaAtual,
                vidaMaxima,
                manaAtual,
                manaMaxima,
                armaEquipada != null ? armaEquipada.getNome() : "Desarmado",
                efeitosAtivos.isEmpty() ? "" : " | Efeitos: " + efeitosAtivos.size());
    }
}

