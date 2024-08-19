window.addEventListener("DOMContentLoaded", function() {
    const authority = document.getElementsByClassName("authority");

    for (const el of authority) {
        el.addEventListener("click", function() {
            alert("aa");
        });
    }
});