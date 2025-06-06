
# 📘 Tutorial - Calculadora Pós-Fixada

Este projeto é uma calculadora que interpreta e avalia expressões matemáticas no formato **pós-fixado** (notação polonesa reversa), desenvolvida em Java com Spring Boot.

---

## ✅ Como funciona a notação pós-fixada?

Na notação pós-fixada:
- Os operandos vêm **antes** do operador.
- Exemplo: `3 4 +` resulta em `7`

---

## ▶️ Como usar a aplicação

A aplicação expõe uma interface web simples (ou pode ser acessada via API) onde você insere a expressão pós-fixada.

### Exemplos válidos:
- `2 3 +` → 5
- `5 1 2 + 4 * + 3 -` → 14
- `10 2 /` → 5
- `10 0 /` → erro: divisão por zero

---

## ⚠️ Validações e Tratamento de Erros

A aplicação trata as seguintes situações:

| Situação                              | Mensagem de Erro                                      |
|--------------------------------------|--------------------------------------------------------|
| Divisão por zero                     | `Divisão por zero.`                                   |
| Módulo por zero                      | `Módulo por zero.`                                    |
| Operadores inválidos                 | `Operador inválido: X`                                |
| Faltam operandos para o operador     | `Faltam operandos para o operador 'X'.`               |
| Expressão com itens de sobra         | `Expressão malformada: operandos ou operadores em excesso.` |
| Expressão muito curta (ex: apenas 1) | `Expressão malformada: deve conter ao menos dois operandos e um operador.` |

---

## 📁 Estrutura do Projeto

- `CalculadoraService.java` → lógica principal de cálculo
- `Pilha.java` e `FilaDinamica.java` → estruturas auxiliares
- `controller/` → recebe requisições HTTP
- `dto/` → define requisições e respostas

---

## 🚀 Como executar

```bash
./mvnw spring-boot:run
```

Depois, acesse: [http://localhost:8080](http://localhost:8080)

---

## 🧮 Funções da Calculadora

A calculadora possui botões com funcionalidades específicas para facilitar a montagem da expressão pós-fixada:

| Botão               | Função                                                                 |
|---------------------|------------------------------------------------------------------------|
| `Enter`             | Adiciona o número digitado à expressão.                                |
| `+` `-` `*` `/` `%` | Adicionam o operador correspondente à expressão.                       |
| `+/-`               | Inverte o sinal do número que está sendo digitado.                     |
| `Apagar`            | Remove o último caractere digitado (backspace).                        |
| `Limpar`            | Limpa a expressão completa.                                            |
| `Drop`              | Remove o último item adicionado à expressão.                           |
| `Calcular`          | Envia a expressão para o serviço e exibe o resultado da avaliação.     |

---

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- HTML + CSS + JS (frontend simples)

---

Acesse para ver um vídeo de funcionamento: [https://youtu.be/bBbMomiyZkc](https://youtu.be/bBbMomiyZkc)
