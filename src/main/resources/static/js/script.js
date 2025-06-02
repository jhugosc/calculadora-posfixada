// URL base da API utilizada para o cálculo
const API_BASE_URL = '/api/calculadora';

// Variáveis de estado
let expression = [];   // Guarda a expressão matemática atual (números e operadores)
let currentInput = ''; // Guarda o número que está sendo digitado
let history = [];      // Armazena o histórico das operações realizadas

// Referências aos elementos da interface (DOM)
const display = document.getElementById('display');
const stackContainer = document.getElementById('stackContainer');
const stackEmpty = document.getElementById('stackEmpty');
const historyCard = document.getElementById('historyCard');
const historyContainer = document.getElementById('historyContainer');
const expressionDisplay = document.getElementById('expressionDisplay');

// Atualiza o display principal com o número atual ou 0
function updateDisplay() {
    display.textContent = currentInput || '0';
}

// Atualiza a visualização da expressão construída até o momento
function updateExpression() {
    if (expression.length === 0) {
        expressionDisplay.textContent = 'Digite números e operadores';
    } else {
        expressionDisplay.textContent = expression.join(' ');
    }
}

// Atualiza a pilha visual (representação da stack)
function updateStack() {
    stackContainer.innerHTML = '';

    if (expression.length === 0) {
        stackContainer.appendChild(stackEmpty);
    } else {
        expression.forEach((value, index) => {
            const item = document.createElement('div');
            item.className = 'stack-item';
            if (index === expression.length - 1) {
                item.classList.add('top'); // Destaca o item do topo da pilha
            }
            item.textContent = value.toString();
            stackContainer.appendChild(item);
        });
    }
}

// Adiciona uma entrada ao histórico, limitando a 10 itens
function addToHistory(operation) {
    history.push(operation);
    if (history.length > 10) {
        history.shift(); // Remove o item mais antigo
    }
    updateHistory();
}

// Atualiza a exibição do histórico na interface
function updateHistory() {
    if (history.length > 0) {
        historyCard.style.display = 'block';
        historyContainer.innerHTML = '';
        history.forEach(entry => {
            const item = document.createElement('div');
            item.className = 'history-item';
            item.textContent = entry;
            historyContainer.appendChild(item);
        });
    }
}

// Insere um número no input atual
function inputNumber(num) {
    currentInput += num;
    updateDisplay();
}

// Insere um ponto decimal, se ainda não houver
function inputDecimal() {
    if (!currentInput.includes('.')) {
        currentInput += '.';
        updateDisplay();
    }
}

// Converte o número digitado e adiciona à expressão
function enterNumber() {
    if (currentInput) {
        const number = parseFloat(currentInput);
        if (!isNaN(number)) {
            expression.push(number);
            addToHistory(`Adicionado: ${number}`);
            currentInput = '';
            updateDisplay();
            updateStack();
            updateExpression();
        }
    }
}

// Adiciona um operador à expressão
function addOperator(operator) {
    if (currentInput) {
        enterNumber(); // Garante que o número atual seja adicionado antes do operador
    }

    if (expression.length === 0) {
        showMessage('Não é possível iniciar a expressão com um operador!', 'error');
        return;
    }

    expression.push(operator);
    addToHistory(`Operador: ${operator}`);
    updateStack();
    updateExpression();
}

// Limpa toda a calculadora (input, expressão, display)
function clearAll() {
    expression = [];
    currentInput = '';
    updateDisplay();
    updateStack();
    updateExpression();
}

// Remove o último caractere digitado no input atual
function backspace() {
    if (currentInput.length > 0) {
        currentInput = currentInput.slice(0, -1);
        updateDisplay();
    }
}

// Remove o último item da pilha (último número ou operador)
function drop() {
    if (expression.length > 0) {
        const dropped = expression.pop();
        addToHistory(`Drop: ${dropped}`);
        updateStack();
        updateExpression();
    }
}

// Mostra uma mensagem temporária na interface
const messageBox = document.getElementById('messageBox');

function showMessage(message, type = 'success') {
    messageBox.textContent = message;
    messageBox.className = `message-box ${type}`;
    messageBox.style.display = 'block';

    setTimeout(() => {
        messageBox.style.display = 'none';
    }, 5000);
}

// Inverte o sinal do número atual (+ para - e vice-versa)
function toggleSign() {
    if (currentInput) {
        if (currentInput.startsWith('-')) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = '-' + currentInput;
        }
        updateDisplay();
    }
}

// Função principal que envia a expressão para a API e processa o resultado
async function calcular() {
    if (currentInput) {
        enterNumber(); // Garante que o número atual seja incluído antes do cálculo
    }
    if (expression.length === 0) {
        showMessage('Expressão vazia!', 'error');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/calcular`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ expressao: expression.join(' ') }) // Envia a expressão como string
        });

        const data = await response.json();

        if (!response.ok) {
            const errorMsg = data.mensagem || 'Erro na requisição';
            showMessage(errorMsg, 'error');
            return;
        }

        const resultado = data.resultado;

        addToHistory(`Expressão: ${expression.join(' ')} = ${resultado}`);

        // Define o resultado como o novo estado da expressão
        expression = [resultado];
        currentInput = '';
        updateDisplay();
        updateStack();
        updateExpression();

        showMessage('Cálculo realizado com sucesso!', 'success');

    } catch (error) {
        showMessage('Erro ao calcular: ' + error.message, 'error');
    }
}

// Inicializa os elementos da interface na carga inicial
updateDisplay();
updateStack();
updateExpression();