let modalOut = document.getElementById('outModal');
let btnOut = document.getElementById('outBtn');
let spanOut = document.getElementsByClassName("closebtn")[0];

btnOut.onclick = function() {
    modalOut.style.display = "block";
}

spanOut.onclick = function() {
    modalOut.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modalOut) {
        modalOut.style.display = "none";
    }
}