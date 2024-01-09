/*
    date -> yyyy년 mm월 dd일(요일)
*/
function dateToDateStr(date) {
    const days = ["일","월","화","수","목","금","토"];
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);

    return year + "년 " + month + "월 " + day + "일" + "(" + days[date.getDay()] + ")";
}

/*
    date -> yyyy-mm-dd
*/
function dateToString(date) {
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

function setScreenSize() {
    let vh = window.innerHeight * 0.01;
    document.documentElement.style.setProperty("--vh", `${vh}px`);
}

setScreenSize();
window.addEventListener("resize", setScreenSize);

