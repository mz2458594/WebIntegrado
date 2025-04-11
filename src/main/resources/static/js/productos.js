let currentCategory = "Todas";
let currentSearchTerm = "";
// Función para filtrar productos
function filterProducts() {
    const productCards = document.querySelectorAll('.product-card');
    productCards.forEach(card => {
        const category = card.querySelector('div').getAttribute('data-category');
        const productName = card.querySelector('h3').textContent.toLowerCase();
        const shouldShow = (currentCategory === "Todas" || category === currentCategory) &&
            (productName.includes(currentSearchTerm) || currentSearchTerm === "");
        card.style.display = shouldShow ? 'block' : 'none';
    });
}

// Función para manejar la selección de categoría
function handleCategoryClick(event) {
    const category = event.currentTarget.getAttribute('data-category');
    currentCategory = category;
    document.querySelectorAll('.category').forEach(cat => cat.classList.remove('active'));
    event.currentTarget.classList.add('active');
    filterProducts();
}

// Añadir event listeners a las categorías
document.querySelectorAll('.category').forEach(category => {
    category.addEventListener('click', handleCategoryClick);
});

// Función para manejar la búsqueda
function handleSearch() {
    currentSearchTerm = document.getElementById('search-input').value.toLowerCase();
    filterProducts();
}

// Añadir event listener al campo de búsqueda
document.getElementById('search-input').addEventListener('input', handleSearch);

// Función para ordenar productos
function sortProducts(sortBy) {
    const productGrid = document.querySelector('.product-grid');
    const productCards = Array.from(productGrid.children);

    productCards.sort((a, b) => {
        const aValue = a.querySelector('h3').textContent;
        const bValue = b.querySelector('h3').textContent;
        const aPrice = parseFloat(a.querySelector('p').textContent.replace('Precio: $', ''));
        const bPrice = parseFloat(b.querySelector('p').textContent.replace('Precio: $', ''));

        switch (sortBy) {
            case 'name-asc':
                return aValue.localeCompare(bValue);
            case 'name-desc':
                return bValue.localeCompare(aValue);
            case 'price-asc':
                return aPrice - bPrice;
            case 'price-desc':
                return bPrice - aPrice;
        }
    });

    productCards.forEach(card => productGrid.appendChild(card));
}

// Añadir event listener al botón de ordenar
document.getElementById('apply-sort').addEventListener('click', () => {
    const sortSelect = document.getElementById('sort-select');
    sortProducts(sortSelect.value);
});

// Añadir event listeners a los botones de compra
document.querySelectorAll('.buy-button').forEach(button => {
    button.addEventListener('click', handleBuy);
});

// Inicialización
document.querySelector('.category[data-category="Todas"]').classList.add('active');