class AdminDashboard {
    constructor() {
        this.init();
    }

    init() {
        this.loadTheme();
        this.bindEvents();
        this.animateCards();
    }

    bindEvents() {
        // Theme toggle
        const themeToggle = document.getElementById('themeToggle');
        themeToggle.addEventListener('click', () => this.toggleTheme());

        // Sidebar toggle
        const toggleSidebar = document.getElementById('toggleSidebar');
        const dashboardContainer = document.getElementById('dashboardContainer');
        const sidebar = document.getElementById('sidebar');
        
        toggleSidebar.addEventListener('click', () => {
            if (window.innerWidth > 1024) {
                // Desktop: collapse sidebar
                dashboardContainer.classList.toggle('sidebar-collapsed');
            } else {
                // Mobile: show/hide sidebar
                sidebar.classList.toggle('open');
            }
        });

        // Navigation links
        const navLinks = document.querySelectorAll('.nav-link');
        navLinks.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                // Remove active class from all links
                navLinks.forEach(l => l.classList.remove('active'));
                // Add active class to clicked link
                link.classList.add('active');
                
                // Simulate page loading
                this.simulateLoading();
            });
        });

        // Close sidebar on mobile when clicking outside
        document.addEventListener('click', (e) => {
            if (window.innerWidth <= 1024) {
                const sidebar = document.getElementById('sidebar');
                const toggleBtn = document.getElementById('toggleSidebar');
                
                if (!sidebar.contains(e.target) && !toggleBtn.contains(e.target)) {
                    sidebar.classList.remove('open');
                }
            }
        });

        // Search functionality
        const searchInput = document.querySelector('.search-box input');
        searchInput.addEventListener('input', (e) => {
            // Simulate search
            console.log('Searching for:', e.target.value);
        });

        // Responsive handling
        window.addEventListener('resize', () => {
            const dashboardContainer = document.getElementById('dashboardContainer');
            const sidebar = document.getElementById('sidebar');
            
            if (window.innerWidth > 1024) {
                sidebar.classList.remove('open');
                dashboardContainer.classList.remove('sidebar-collapsed');
            }
        });
    }

    toggleTheme() {
        const body = document.body;
        const themeToggle = document.getElementById('themeToggle');
        const icon = themeToggle.querySelector('i');
        const text = themeToggle.querySelector('span');

        if (body.getAttribute('data-theme') === 'dark') {
            body.removeAttribute('data-theme');
            icon.className = 'fas fa-moon';
            text.textContent = 'Dark Mode';
            this.saveTheme('light');
        } else {
            body.setAttribute('data-theme', 'dark');
            icon.className = 'fas fa-sun';
            text.textContent = 'Light Mode';
            this.saveTheme('dark');
        }

        // Add a subtle animation to the theme toggle
        themeToggle.style.transform = 'scale(0.95)';
        setTimeout(() => {
            themeToggle.style.transform = 'scale(1)';
        }, 150);
    }

    saveTheme(theme) {
        this.currentTheme = theme;
        console.log('Theme saved:', theme);
    }

    loadTheme() {
        const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
        const savedTheme = this.currentTheme || (prefersDark ? 'dark' : 'light');
        
        const body = document.body;
        const themeToggle = document.getElementById('themeToggle');
        const icon = themeToggle.querySelector('i');
        const text = themeToggle.querySelector('span');

        if (savedTheme === 'dark') {
            body.setAttribute('data-theme', 'dark');
            icon.className = 'fas fa-sun';
            text.textContent = 'Light Mode';
        }
    }

    animateCards() {
        const statCards = document.querySelectorAll('.stat-card');
        statCards.forEach((card, index) => {
            card.style.animationDelay = `${index * 0.1}s`;
        });

        this.animateNumbers();
    }

    animateNumbers() {
        const numberElements = document.querySelectorAll('.stat-number');
        numberElements.forEach(element => {
            const finalNumber = element.textContent;
            const isPrice = finalNumber.includes('₹') || finalNumber.includes('$');
            const isPercentage = finalNumber.includes('%');
            
            let numericValue = parseFloat(finalNumber.replace(/[₹$,%,]/g, ''));
            let current = 0;
            const increment = numericValue / 50;
            
            const timer = setInterval(() => {
                current += increment;
                if (current >= numericValue) {
                    current = numericValue;
                    clearInterval(timer);
                }
                
                let displayValue = Math.floor(current).toLocaleString();
                if (isPrice) displayValue = '₹' + displayValue;
                if (isPercentage) displayValue = displayValue + '%';
                
                element.textContent = displayValue;
            }, 30);
        });
    }

    simulateLoading() {
        const mainContent = document.querySelector('.main-content');
        mainContent.classList.add('loading');
        
        setTimeout(() => {
            mainContent.classList.remove('loading');
        }, 800);
    }

    updateDashboardData(data) {
        console.log('Updating dashboard with data:', data);
        
        if (data.stats) {
            const statNumbers = document.querySelectorAll('.stat-number');
            data.stats.forEach((stat, index) => {
                if (statNumbers[index]) {
                    statNumbers[index].textContent = stat.value;
                }
            });
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    new AdminDashboard();

    const cards = document.querySelectorAll('.card');
    cards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.transform = 'translateY(-2px)';
        });
        
        card.addEventListener('mouseleave', () => {
            card.style.transform = 'translateY(0)';
        });
    });

    const buttons = document.querySelectorAll('button');
    buttons.forEach(button => {
        button.addEventListener('click', function() {
            const ripple = document.createElement('div');
            ripple.style.cssText = `
                position: absolute;
                border-radius: 50%;
                background: rgba(255, 255, 255, 0.6);
                transform: scale(0);
                animation: ripple 0.6s linear;
                pointer-events: none;
            `;
            
            this.style.position = 'relative';
            this.style.overflow = 'hidden';
            this.appendChild(ripple);
            
            setTimeout(() => {
                ripple.remove();
            }, 600);
        });
    });
});