// Product Data
const products = [
  { id: 1, name: "Wireless Bluetooth Headphones", description: "High-quality wireless headphones with noise cancellation.", price: 2499, category: "Electronics", image: "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&h=500&fit=crop" },
  { id: 2, name: "Cotton Blend T-Shirt", description: "Comfortable and stylish cotton blend t-shirt.", price: 899, category: "Clothing", image: "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=500&h=500&fit=crop" },
  { id: 3, name: "The Psychology of Programming", description: "Guide to understanding the mindset of effective programmers.", price: 1299, category: "Books", image: "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500&h=500&fit=crop" },
  { id: 4, name: "Smart Home Security Camera", description: "Advanced security camera with AI detection.", price: 4999, category: "Electronics", image: "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=500&h=500&fit=crop" },
  { id: 5, name: "Premium Leather Jacket", description: "Genuine leather jacket with modern design.", price: 8999, category: "Clothing", image: "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&h=500&fit=crop" },
  { id: 6, name: "Ceramic Coffee Mug Set", description: "Set of  elegant ceramic mugs.", price: 1499, category: "Home", image: "https://nestasia.in/cdn/shop/products/MatteFinishCup_9.png?v=1688625001&width=600" },
  { id: 7, name: "JavaScript: The Definitive Guide", description: "Comprehensive reference book for JavaScript.", price: 1799, category: "Books", image: "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=500&h=500&fit=crop" },
  { id: 8, name: "Yoga Exercise Mat", description: "Non-slip yoga mat made from eco-friendly materials.", price: 1999, category: "Sports", image: "https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=500&h=500&fit=crop" },
  { id: 9, name: "Smartphone with 128GB Storage", description: "Latest smartphone with advanced camera.", price: 25999, category: "Electronics", image: "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500&h=500&fit=crop" },
  { id: 10, name: "Designer Denim Jeans", description: "Premium quality denim jeans with stylish design.", price: 3499, category: "Clothing", image: "https://images.unsplash.com/photo-1542272604-787c3835535d?w=500&h=500&fit=crop" },
  { id: 11, name: "Scented Candle Collection", description: "Set of  luxury scented candles.", price: 2299, category: "Home", image: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-MKBZjEJNzcvFUzDQhO9IhYRMOMS4wQ2Fnw&s" },
  { id: 12, name: "Professional Tennis Racket", description: "High-performance tennis racket.", price: 6999, category: "Sports", image: "https://images.unsplash.com/photo-1551698618-1dfe5d97d256?w=500&h=500&fit=crop" }
];

// DOM Elements
const categoryFilter = document.getElementById('category-filter');
const productsGrid = document.getElementById('products-grid');
const productCount = document.getElementById('product-count');
const noProductsMessage = document.getElementById('no-products');

// Initialize
function init() {
  renderProducts(products);
  updateProductCount(products.length);
  categoryFilter.addEventListener('change', () => {
    const selected = categoryFilter.value;
    const filtered = selected === 'All' ? products : products.filter(p => p.category === selected);
    renderProducts(filtered);
    updateProductCount(filtered.length);
  });
}

// Render products
function renderProducts(list) {
  if (list.length === 0) {
    productsGrid.innerHTML = '';
    noProductsMessage.classList.remove('hidden');
    productsGrid.classList.add('hidden');
    return;
  }
  noProductsMessage.classList.add('hidden');
  productsGrid.classList.remove('hidden');
  productsGrid.innerHTML = list.map(p => `
    <div class="product-card">
      <img src="${p.image}" alt="${p.name}" class="product-image" loading="lazy"/>
      <div class="product-content">
        <h3 class="product-name">${p.name}</h3>
        <p class="product-description">${p.description}</p>
        <div class="product-footer">
          <span class="product-price">₹${p.price}</span>
          <span class="product-category">${p.category}</span>
        </div>
      </div>
    </div>
  `).join('');
}

// Update product count
function updateProductCount(count) {
  productCount.textContent = `Showing ${count} product${count !== 1 ? 's' : ''}`;
}

// Start
document.addEventListener('DOMContentLoaded', init);


