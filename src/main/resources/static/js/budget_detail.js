function onDeleteClick(budgetId) {
    location.href = "/account_bank/settings/budget/delete?id=" + budgetId;
}

function onMoneyKeyUp(event) {
    const element = document.querySelector("#money");
    const value = Number(event.target.value);

    if (isNaN(value)) {
        element.value = 0;
    } else {
        element.value = value;
    }
}