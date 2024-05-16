let eventData = {};

function makeItemContainer(eventData) {
    // Remove all
    const root = document.querySelector("#itemContainer");
    root.innerHTML = "";

    if (eventData) {
        const dataList = eventData.data;
        dataList.forEach(value => {
            // Item container
            const div = document.createElement("div");
            div.classList.add("d-flex", "align-items-center", "mb-2");
            div.addEventListener("click", () => {
                location.href="/account_bank/detail?id=" + value.id;
            });

            const targetIcon = document.createElement("img");
            targetIcon.className = "target-icon";
            targetIcon.src = "/images/target/" + value.target.targetIcon;
            const categoryIcon = document.createElement("img");
            categoryIcon.className = "category-icon";
            categoryIcon.src = "/images/category/" + value.category.categoryIcon;
            const categoryNameSpan = document.createElement("span");
            categoryNameSpan.classList.add("ms-2", "category-name");
            categoryNameSpan.textContent = value.category.name;
            const descriptionDiv = document.createElement("div")
            descriptionDiv.classList.add("col-sm-6", "ms-3", "me-auto", "description-text");
            descriptionDiv.textContent = value.description;
            const moneySpan = document.createElement("span");
            moneySpan.classList.add("money-text");

            let money = value.division === "EXPENSE" ? -1 * value.money : value.money;
            moneySpan.textContent = money.toLocaleString() + "원";

            div.append(targetIcon, categoryIcon, categoryNameSpan, descriptionDiv, moneySpan);
            root.appendChild(div);
        })
    }
}

function removeAllEvents(calendar) {
    const events = calendar.getEvents();
    events.forEach(event => {
        event.remove();
    })
}

function sendRequest(dateStr, calendar) {
    fetch("/api/v1/account_bank?date=" + dateStr)
    .then(response => response.json())
    .then(response => {
        const dataList = response;

        // initialize
        removeAllEvents(calendar);
        eventData = {};
        let totalMoney = 0;

        dataList.forEach(data => {
            // money to title
            const tempDate = new Date(data["targetDate"]);
            const tempStart = dateToString(tempDate);
            const money = data["division"] === "EXPENSE" ? -1 * data["money"] : data["money"];

            if (tempStart in eventData) {
                // 값이 있다면
                eventData[tempStart].total += parseInt(money);
            } else {
                // 최초 값
                eventData[tempStart] = {
                    data: [],
                    total: 0
                };
                eventData[tempStart].total = parseInt(money);
            }

            eventData[tempStart].data.push(data);
            totalMoney += parseInt(money);
        });

        for (key in eventData) {
            calendar.addEvent({
                title: (eventData[key].total).toLocaleString(),
                start: key,
                backgroundColor: "lightskyblue",
                borderColor: "lightskyblue",
            });
        }

        // 총 지출
        const totalMoneyText = document.querySelector("#totalMoney");
        totalMoneyText.textContent = totalMoney.toLocaleString() + "원";

        const initialDate = dateToString(calendar.getDate());
        makeItemContainer(eventData[initialDate]);
    })
    .catch(error => console.error(error));
}

function makeSettingIcon() {
    const toolbar = document.querySelector(".fc-header-toolbar");

    // a tag
    const aTag = document.createElement("a");
    aTag.href = "/account_bank/settings";

    // image tag
    const imageTag = document.createElement("img");
    imageTag.className = "setting-icon";
    imageTag.src = "/images/settings/settings_icon.png";

    aTag.appendChild(imageTag);
    toolbar.appendChild(aTag);
}

document.addEventListener("DOMContentLoaded", () => {
    const calendarElement = document.querySelector("#calendar");
    const calendar = new FullCalendar.Calendar(calendarElement, {
        initialView: "dayGridMonth",
        eventClick: (info) => {
            const selectData = eventData[info.event.startStr];
            makeItemContainer(selectData);
        },
        dateClick: (info) => {
           location.href = "/account_bank/register?date=" + info.dateStr;
        },
    });

    sendRequest(dateToString(calendar.getDate()), calendar);
    calendar.render();

    // Add settings
    makeSettingIcon();

    // Click event
    const prevButton = document.querySelector(".fc-prev-button");
    prevButton.addEventListener("click", () => {
        sendRequest(dateToString(calendar.getDate()), calendar);
    });

    const nextButton = document.querySelector(".fc-next-button");
    nextButton.addEventListener("click", () => {
        sendRequest(dateToString(calendar.getDate()), calendar);
    });

    const todayButton = document.querySelector(".fc-today-button");
    todayButton.addEventListener("click", () => {
        sendRequest(dateToString(calendar.getDate()), calendar);
    });
});
