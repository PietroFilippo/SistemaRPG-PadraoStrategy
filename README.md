# Sistema de Combate RPG com Padrão Strategy

Sistema de combate para RPG medieval implementado em Java, demonstrando o uso do Padrão de Projeto Strategy para gerenciar diferentes tipos de armas e seus efeitos especiais.

Este projeto implementa um sistema de batalha por turnos onde personagens de diferentes classes podem equipar e trocar armas durante o combate. Cada arma possui comportamentos únicos que são aplicados através do padrão Strategy.

## Padrão Strategy

O padrão Strategy é aplicado da seguinte forma:

- **Strategy (Interface)**: `Arma` - Define o contrato para diferentes estratégias de ataque
- **Concrete Strategies**: `EspadaLonga`, `ArcoElfico`, `CajadoArcano`, etc. - Implementações específicas
- **Context**: `Personagem` - Utiliza uma estratégia de arma que pode ser trocada em runtime
- **Benefício**: Permite trocar o comportamento de ataque sem modificar a classe do personagem

## Armas Disponíveis

| Arma | Dano | Mana | Efeito Especial | Requisito |
|------|------|------|-----------------|-----------|
| **Espada Longa** | 15 | 0 | Corte Profundo - 30% de sangramento (5 dano/3 turnos) | Força ≥ 10 |
| **Arco Élfico** | 12 | 15 | Chuva de Flechas - Atinge todos os inimigos | Destreza ≥ 8 |
| **Cajado Arcano** | 8 | 25 | Bola de Fogo - Causa queimadura (10 dano/2 turnos) | Inteligência ≥ 12 |
| **Machado de Guerra** | 18 | 5 | Golpe Esmagador - 25% de atordoamento (perde 1 turno) | Força ≥ 15 |
| **Adaga Sombria** | 10 | 10 | Ataque Furtivo - Dano triplo se alvo desprevenido | Destreza ≥ 12 |
| **Lança do Dragão** | 20 | 20 | Perfuração Dracônica - Ignora 50% da resistência | Força ≥ 12 E Destreza ≥ 12 |

## Classes de Personagens

### Guerreiro
- **Atributos**: Força 15, Destreza 8, Inteligência 5
- **Vida**: 120 | **Mana**: 50
- **Passiva**: Pele Dura - Reduz 20% do dano recebido
- **Armas**: Espada Longa, Machado de Guerra

### Arqueiro
- **Atributos**: Força 8, Destreza 15, Inteligência 7
- **Vida**: 90 | **Mana**: 80
- **Passiva**: Esquiva - 25% de chance de evitar ataques
- **Armas**: Arco Élfico, Adaga Sombria

### Mago
- **Atributos**: Força 5, Destreza 7, Inteligência 18
- **Vida**: 70 | **Mana**: 150
- **Passiva**: Regeneração de Mana - Recupera 10 de mana por turno
- **Armas**: Cajado Arcano, Adaga Sombria

### Paladino (Híbrido)
- **Atributos**: Força 12, Destreza 8, Inteligência 12
- **Vida**: 100 | **Mana**: 100
- **Passiva**: Benção Divina - Regenera 5 de vida e 5 de mana por turno
- **Passiva Extra**: 10% de chance de bloquear completamente um ataque
- **Armas**: Espada Longa, Machado de Guerra, Cajado Arcano, Lança do Dragão
- **Habilidade Especial**: Golpe Sagrado - Combina dano físico e mágico

## Efeitos de Status

- **Sangramento**: 5 de dano por 3 turnos
- **Queimadura**: 10 de dano por 2 turnos
- **Atordoamento**: Personagem perde 1 turno
- **Envenenamento**: Dano crescente por 4 turnos (3, 6, 9, 12)

## Mecânicas de Combate

### Sistema de Crítico
- 15% de chance de causar dano dobrado em qualquer ataque

### Sistema de Turnos
1. Aplicar habilidade passiva do personagem
2. Processar efeitos de status ativos
3. Executar ataque (se não estiver atordoado)
4. Processar efeitos do alvo
5. Verificar condição de vitória

### Troca de Armas
- Personagens podem trocar armas durante a batalha
- Sistema valida requisitos de atributos antes de equipar
