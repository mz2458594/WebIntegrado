//NOTIFICACIONES

const notifButton = document.getElementById("btn-notificacion");
const notifPanel = document.getElementById("panel-notifi");

notifButton.addEventListener("click", () => {
    notifPanel.classList.toggle("hidden");
});

window.addEventListener("click", (e) => {
    if (!notifButton.contains(e.target) && !notifPanel.contains(e.target)) {
        notifPanel.classList.add("hidden");
    }
});
