let d = document;
let wrap = d.querySelector('.wrap');
let items = d.querySelector('.items');
let itemCount = d.querySelectorAll('.item').length;
let scroller = d.querySelector('.scroller');
let pos = 0;
let transform = Modernizr.prefixed('transform');

function setTransform() {
    items.style[transform] = 'translate3d(' + (-pos * items.offsetWidth) + 'px,0,0)';
}

function prev() {
    pos = Math.max(pos - 1, 0);
    setTransform();
}

function next() {
    pos = Math.min(pos + 1, itemCount - 1);
    setTransform();
}

window.addEventListener('resize', setTransform);

let modal = document.getElementById('myModal');
let btn = document.getElementById('myBtn');
let span = document.getElementsByClassName("close")[0];

btn.onclick = function() {
    modal.style.display = "block";
}

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

let upmodal = document.getElementById('upModal');
let upbtn = document.getElementById('upBtn');
let upspan = document.getElementsByClassName("closebtn")[0];

upbtn.onclick = function() {
    upmodal.style.display = "block";
}

upspan.onclick = function() {
    upmodal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == upmodal) {
        upmodal.style.display = "none";
    }
}