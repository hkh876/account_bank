document.addEventListener("DOMContentLoaded", () => {
    highlightKeyword();
});

function highlightKeyword() {
    const searchElement = document.querySelector("#search");
    if (!searchElement) {
        return;
    }

    const keyword = searchElement.value;
    const descriptions = document.querySelectorAll(".description");

    descriptions.forEach(element => {
        const regex = new RegExp(`(${keyword})`, "gi");
        element.innerHTML = element.innerHTML.replace(regex, "<strong>$1</strong>");
    });
}

function onSearchItemClick(id) {
    location.href = "/account_bank/detail?id=" + id;
}