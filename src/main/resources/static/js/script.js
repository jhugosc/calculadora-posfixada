const API_BASE_URL = '/api/calculadora';

let expression = [];
let currentInput = '';
let history = [];

const display = document.getElementById('display');
const stackContainer = document.getElementById('stackContainer');
const stackEmpty = document.getElementById('stackEmpty');
const historyCard = document.getElementById('historyCard');
const historyContainer = document.getElementById('historyContainer');
const expressionDisplay = document.getElementById('expressionDisplay');

function updateDisplay() {
    display.textContent = currentInput || '0';
}

function updateExpression() {
    if (expression.length === 0) {
        expressionDisplay.textContent = 'Digite números e operadores';
    } else {
        expressionDisplay.textContent = expression.join(' ');
    }
}

function updateStack() {
    stackContainer.innerHTML = '';

    if (expression.length === 0) {
        stackContainer.appendChild(stackEmpty);
    } else {
        expression.forEach((value, index) => {
            const item = document.createElement('div');
            item.className = 'stack-item';
            if (index === expression.length - 1) {
                item.classList.add('top');
            }
            item.textContent = value.toString();
            stackContainer.appendChild(item);
        });
    }
}

function addToHistory(operation) {
    history.push(operation);
    if (history.length > 10) {
        history.shift();
    }
    updateHistory();
}

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

function inputNumber(num) {
    currentInput += num;
    updateDisplay();
}

function inputDecimal() {
    if (!currentInput.includes('.')) {
        currentInput += '.';
        updateDisplay();
    }
}

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

function addOperator(operator) {
    if (currentInput) {
        enterNumber();
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


function clearAll() {
    expression = [];
    currentInput = '';
    updateDisplay();
    updateStack();
    updateExpression();
}

function backspace() {
    if (currentInput.length > 0) {
        currentInput = currentInput.slice(0, -1);
        updateDisplay();
    }
}

function drop() {
    if (expression.length > 0) {
        const dropped = expression.pop();
        addToHistory(`Drop: ${dropped}`);
        updateStack();
        updateExpression();
    }
}

const messageBox = document.getElementById('messageBox');

function showMessage(message, type = 'success') {
    messageBox.textContent = message;
    messageBox.className = `message-box ${type}`;
    messageBox.style.display = 'block';

    setTimeout(() => {
        messageBox.style.display = 'none';
    }, 5000);
}

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


async function calcular() {
    if (currentInput) {
        enterNumber();
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
            body: JSON.stringify({ expressao: expression.join(' ') })
        });

        const data = await response.json();

        if (!response.ok) {
            const errorMsg = data.mensagem || 'Erro na requisição';
            showMessage(errorMsg, 'error');
            return;
        }

        const resultado = data.resultado;

        addToHistory(`Expressão: ${expression.join(' ')} = ${resultado}`);

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

updateDisplay();
updateStack();
updateExpression();