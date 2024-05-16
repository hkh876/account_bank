function onDeleteClick(shoppingId) {
    if (confirm("삭제 하시겠습니까?")) {
        const data = { shopping_id: shoppingId }
        sendRequest(
            "/api/v1/account_bank/grocery_shopping/delete",
            "DELETE",
            data
        ).then(response => {
            if (response.ok) {
                location.reload();
            } else {
                throw new Error(`${response.status} 오류`);
            }
        }).catch(error => alert(error.message));
    }
}