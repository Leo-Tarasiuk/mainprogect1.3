let datemodal = document.getElementById('dateModal');
let datebtn = document.getElementById('dateBtn');
let datespan = document.getElementsByClassName("closeDate")[0];

datebtn.onclick = function() {
    datemodal.style.display = "block";
}

datespan.onclick = function() {
    datemodal.style.display = "none";
}


window.onclick = function(event) {
    if (event.target == datemodal) {
        datemodal.style.display = "none";
    }
}