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
