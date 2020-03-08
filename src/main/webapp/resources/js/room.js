let roommodal = document.getElementById('roomModal');
let roombtn = document.getElementById('roomBtn');
let roomspan = document.getElementsByClassName("roomClose")[0];

roombtn.onclick = function() {
    roommodal.style.display = "block";
}

roomspan.onclick = function() {
    roommodal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == roommodal) {
        roommodal.style.display = "none";
    }
}