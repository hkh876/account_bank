function dateToDateStr(date) {
    const days = ["일","월","화","수","목","금","토"];
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);

    return year + "년 " + month + "월 " + day + "일" + "(" + days[date.getDay()] + ")";
}

document.addEventListener("DOMContentLoaded", () => {
    const rollDate = new Rolldate({
        el: "#displayDate",
        format: "YYYY-MM-DD",
        lang: { title: '날짜선택', cancel: '취소', confirm: '선택', year: '년', month: '월', day: '일'},
        confirm: (date) => {
            const tempDate = new Date(date);
            const value = dateToDateStr(tempDate);

            setTimeout(() => {
                // display date
                const dateStrInput = document.querySelector("#displayDate");
                dateStrInput.value = value;

                // real date
                const dateStr = document.querySelector("#dateStr");
                dateStr.value = date;
            }, 1);
        }
    });
});

function onDeleteClick(accountId) {
    location.href = "/account_bank/delete?id=" + accountId;
}

function onMoneyKeyUp(event) {
    const element = document.querySelector("#money");
    const value = Number(event.target.value);

    console.log(value);
    if (isNaN(value)) {
        element.value = 0;
    } else {
        element.value = value;
    }
}