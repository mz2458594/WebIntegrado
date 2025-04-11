let currentIndex = 0;

function slideTo(index) {
    const slidesContainer = document.querySelector('.slides');
    slidesContainer.style.transform = `translateX(-${index * 50}%)`;
}

function startSlider() {
    const totalSlides = document.querySelectorAll('.slide').length;
    setInterval(() => {
        currentIndex = (currentIndex + 1) % totalSlides;
        slideTo(currentIndex);
    }, 4000);
}

document.addEventListener("DOMContentLoaded", startSlider);
