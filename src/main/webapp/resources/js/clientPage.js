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
