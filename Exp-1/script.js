let balance = 0;
const transactionHistory = [];

document.getElementById('transaction-form').addEventListener('submit', function(event) {
    event.preventDefault();
    
    const amountInput = document.getElementById('amount');
    const amount = parseFloat(amountInput.value);
    const action = event.submitter.dataset.action;
    const errorMessage = document.getElementById('error-message');
    
    // Form validation
    if (isNaN(amount) || amount <= 0) {
        errorMessage.textContent = 'Please enter a valid amount greater than 0.';
        return;
    }
    
    if (action === 'withdraw' && amount > balance) {
        errorMessage.textContent = 'Insufficient funds for withdrawal.';
        return;
    }
    
    // Clear error message
    errorMessage.textContent = '';
    
    // Update balance
    if (action === 'deposit') {
        balance += amount;
        transactionHistory.push(`Deposited: ₹${amount.toFixed(2)}`);
    } else if (action === 'withdraw') {
        balance -= amount;
        transactionHistory.push(`Withdrew: ₹${amount.toFixed(2)}`);
    }
    
    // Update UI
    document.getElementById('balance').textContent = `₹${balance.toFixed(2)}`;
    
    // Update transaction history
    const historyList = document.getElementById('transaction-history');
    const listItem = document.createElement('li');
    listItem.textContent = `${action.charAt(0).toUpperCase() + action.slice(1)}: ₹${amount.toFixed(2)}`;
    listItem.classList.add(action);
    historyList.prepend(listItem);
    
    // Clear input
    amountInput.value = '';
});
