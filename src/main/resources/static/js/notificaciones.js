//NOTIFICACIONES

const notifButton = document.getElementById("btn-notificacion");
const notifPanel = document.getElementById("panel-notifi");
const notifCount = document.getElementById("contar-notificacion");
const notifList = document.getElementById("lista-notifi");


/*OBJETO PARA LLENAR LAS NOTIFICACIONES, ESTANDO COMENTADAS SALDRA QUE HAY 0*/
// SI SE DESCOMENTAN SE MOSTRARAN
const notifications = [
    // {
    //     title: "Stock Bajo",
    //     description: "Solo queda una laptop, recuerda hacer un pedido",
    //     time: "hace 18 días",
    // },
    // {
    //     title: "Stock bajo",
    //     description: "Quedan 2 celulares",
    //     time: "hace 2 minutos",
    // },
    // {
    //     title: "Stock Bajo",
    //     description: "Solo queda una laptop, recuerda hacer un pedido",
    //     time: "hace 18 días",
    // },
    // {
    //     title: "Stock bajo",
    //     description: "Quedan 2 celulares",
    //     time: "hace 2 minutos",
    // },
    // {
    //     title: "Stock Bajo",
    //     description: "Solo queda una laptop, recuerda hacer un pedido",
    //     time: "hace 18 días",
    // },
    // {
    //     title: "Stock bajo",
    //     description: "Quedan 2 celulares",
    //     time: "hace 2 minutos",
    // }
];



function renderNotifications() {
    notifList.innerHTML = "";
    notifications.forEach(notif => {
        const div = document.createElement("div");
        div.classList.add("notification-item");
        div.innerHTML = `
        <strong>${notif.title}</strong>
        <div>${notif.description}</div>
        <small>${notif.time}</small>
    `;
        notifList.appendChild(div);
    });

    notifCount.textContent = notifications.length;
    notifCount.style.display = notifications.length ? "inline-block" : "none";
}

function markAllAsRead() {
    notifications.length = 0;
    renderNotifications();
    notifPanel.classList.add("hidden");
}

notifButton.addEventListener("click", () => {
    notifPanel.classList.toggle("hidden");
});

window.addEventListener("click", (e) => {
    if (!notifButton.contains(e.target) && !notifPanel.contains(e.target)) {
        notifPanel.classList.add("hidden");
    }
});

renderNotifications();


if(notifications.length === 0){
    notifList.style.display = "flex";
    notifList.style.justifyContent = "center";
    notifList.style.alignItems = "center";
    notifList.innerHTML = `
        <p class="nohay-notifi">No hay notificaciones</p>
    `;

}
