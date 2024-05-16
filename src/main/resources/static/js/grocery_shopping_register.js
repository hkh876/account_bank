function addRow() {
    const table = document.querySelector("#shoppingTable");
    const tableRowCount = table.rows.length;
    const newRow = table.insertRow(tableRowCount - 1);
    newRow.className = "dataRow";

    // Cell 4개
    const nameCell = newRow.insertCell(0);
    const marketCell = newRow.insertCell(1);
    const martCell = newRow.insertCell(2);
    const coupangCell = newRow.insertCell(3);

    // 내용 채우기
    nameCell.className = "position-relative";
    nameCell.innerHTML = `
        <img class="delete-icon" style="display: none;" src="/images/settings/delete_icon.png"
            alt="delete icon" onclick="onDeleteIconClick(this)">
        <input type="text" class="form-control nameInput" placeholder="항목">
    `;
    marketCell.innerHTML = `
        <input type="number" class="form-control text-end marketInput" min="0" value="0"
            onkeyup="onMoneyKeyUp(this)">
    `;
    martCell.innerHTML = `
        <input type="number" class="form-control text-end martInput" min="0" value="0"
            onkeyup="onMoneyKeyUp(this)">
    `;
    coupangCell.innerHTML = `
        <input type="number" class="form-control text-end coupangInput" min="0" value="0"
           onkeyup="onMoneyKeyUp(this)">
    `
}

function deleteRow(rowIndex) {
    const table = document.querySelector("#shoppingTable");
    table.deleteRow(rowIndex);
}

function onDeleteClick() {
    const deleteIcons = document.querySelectorAll(".delete-icon");
    deleteIcons.forEach((icon) => {
        icon.style.display = (icon.style.display === "none") ? "block" : "none";
    });
}

function onDeleteIconClick(icon) {
    const parentRow = icon.parentElement.parentElement;
    deleteRow(parentRow.rowIndex);
}

function onSaveClick() {
    const rows = document.querySelectorAll("#shoppingTable > tbody > tr.dataRow");
    const data = [];

    for (const item of rows) {
        const name = item.querySelector(".nameInput").value;
        const marketPrice = item.querySelector(".marketInput").value;
        const martPrice = item.querySelector(".martInput").value;
        const coupangPrice = item.querySelector(".coupangInput").value;

        if (name.trim() === "") {
            continue;
        }

        data.push({
            name: name,
            market_price: marketPrice,
            mart_price: martPrice,
            coupang_price: coupangPrice
        });
    }

    if (data.length === 0) {
        alert("저장할 내용이 없습니다.");
        return;
    }

    // 서버 전송
    sendRequest(
        "/api/v1/account_bank/grocery_shopping/item_register",
        "POST",
        data,
    ).then(response => {
        if (response.ok) {
            location.replace("/account_bank/settings/grocery_shopping");
        } else {
            throw new Error(`${response.status} 오류`);
        }
    }).catch(error => alert(error.message));
}

