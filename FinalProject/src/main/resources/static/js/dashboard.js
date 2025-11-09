/**
 * Dashboard JavaScript functionality for Resume Builder
 */

// Global variables
let currentModal = null;

// Initialize dashboard when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializeDashboard();
});

/**
 * Initialize dashboard functionality
 */
function initializeDashboard() {
    // Initialize delete confirmation modals
    initializeDeleteConfirmations();
    
    // Initialize duplicate confirmations
    initializeDuplicateConfirmations();
    
    // Initialize search functionality
    initializeSearch();
    
    // Initialize sorting
    initializeSorting();
    
    // Auto-hide alerts after 5 seconds
    autoHideAlerts();
}

/**
 * Initialize delete confirmation dialogs
 */
function initializeDeleteConfirmations() {
    const deleteButtons = document.querySelectorAll('.btn-delete-resume');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            const resumeId = this.dataset.resumeId;
            const resumeTitle = this.dataset.resumeTitle;
            
            showDeleteConfirmation(resumeId, resumeTitle);
        });
    });
}

/**
 * Initialize duplicate confirmation dialogs
 */
function initializeDuplicateConfirmations() {
    const duplicateButtons = document.querySelectorAll('.btn-duplicate-resume');
    
    duplicateButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            
            const resumeId = this.dataset.resumeId;
            const resumeTitle = this.dataset.resumeTitle;
            
            showDuplicateConfirmation(resumeId, resumeTitle);
        });
    });
}

/**
 * Initialize search functionality
 */
function initializeSearch() {
    const searchInput = document.getElementById('resume-search');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            filterResumes(this.value);
        });
    }
}

/**
 * Initialize sorting functionality
 */
function initializeSorting() {
    const sortSelect = document.getElementById('resume-sort');
    if (sortSelect) {
        sortSelect.addEventListener('change', function() {
            sortResumes(this.value);
        });
    }
}

/**
 * Show delete confirmation modal
 */
function showDeleteConfirmation(resumeId, resumeTitle) {
    const modalHtml = `
        <div class="modal-overlay" id="delete-modal">
            <div class="modal">
                <div class="modal-header">
                    <h3 class="modal-title">Confirm Delete</h3>
                    <button type="button" class="modal-close" onclick="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Are you sure you want to delete the resume "<strong>${escapeHtml(resumeTitle)}</strong>"?</p>
                    <p class="text-danger">This action cannot be undone.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
                    <button type="button" class="btn btn-danger" onclick="confirmDelete(${resumeId})">Delete Resume</button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    currentModal = document.getElementById('delete-modal');
}

/**
 * Show duplicate confirmation modal
 */
function showDuplicateConfirmation(resumeId, resumeTitle) {
    const modalHtml = `
        <div class="modal-overlay" id="duplicate-modal">
            <div class="modal">
                <div class="modal-header">
                    <h3 class="modal-title">Duplicate Resume</h3>
                    <button type="button" class="modal-close" onclick="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Create a copy of "<strong>${escapeHtml(resumeTitle)}</strong>"?</p>
                    <p>You'll be able to edit the copy independently.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeModal()">Cancel</button>
                    <button type="button" class="btn btn-success" onclick="confirmDuplicate(${resumeId})">Create Copy</button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    currentModal = document.getElementById('duplicate-modal');
}

/**
 * Close current modal
 */
function closeModal() {
    if (currentModal) {
        currentModal.remove();
        currentModal = null;
    }
}

/**
 * Confirm resume deletion
 */
function confirmDelete(resumeId) {
    // Create and submit delete form
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `/resume/${resumeId}/delete`;
    
    // Add CSRF token if available
    const csrfToken = document.querySelector('meta[name="_csrf"]');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]');
    
    if (csrfToken && csrfHeader) {
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_token';
        csrfInput.value = csrfToken.getAttribute('content');
        form.appendChild(csrfInput);
    }
    
    document.body.appendChild(form);
    form.submit();
}

/**
 * Confirm resume duplication
 */
function confirmDuplicate(resumeId) {
    // Create and submit duplicate form
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = `/resume/${resumeId}/duplicate`;
    
    // Add CSRF token if available
    const csrfToken = document.querySelector('meta[name="_csrf"]');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]');
    
    if (csrfToken && csrfHeader) {
        const csrfInput = document.createElement('input');
        csrfInput.type = 'hidden';
        csrfInput.name = '_token';
        csrfInput.value = csrfToken.getAttribute('content');
        form.appendChild(csrfInput);
    }
    
    document.body.appendChild(form);
    form.submit();
}

/**
 * Filter resumes based on search term
 */
function filterResumes(searchTerm) {
    const resumeCards = document.querySelectorAll('.resume-card');
    const searchLower = searchTerm.toLowerCase();
    
    resumeCards.forEach(card => {
        const title = card.querySelector('.resume-title').textContent.toLowerCase();
        const template = card.querySelector('.template-badge').textContent.toLowerCase();
        
        if (title.includes(searchLower) || template.includes(searchLower)) {
            card.style.display = 'block';
        } else {
            card.style.display = 'none';
        }
    });
    
    // Update empty state visibility
    updateEmptyState();
}

/**
 * Sort resumes based on selected criteria
 */
function sortResumes(sortBy) {
    const resumeGrid = document.querySelector('.resume-grid');
    const resumeCards = Array.from(document.querySelectorAll('.resume-card'));
    
    resumeCards.sort((a, b) => {
        switch (sortBy) {
            case 'title':
                const titleA = a.querySelector('.resume-title').textContent;
                const titleB = b.querySelector('.resume-title').textContent;
                return titleA.localeCompare(titleB);
                
            case 'date':
                const dateA = new Date(a.dataset.createdDate);
                const dateB = new Date(b.dataset.createdDate);
                return dateB - dateA; // Newest first
                
            case 'template':
                const templateA = a.querySelector('.template-badge').textContent;
                const templateB = b.querySelector('.template-badge').textContent;
                return templateA.localeCompare(templateB);
                
            default:
                return 0;
        }
    });
    
    // Re-append sorted cards
    resumeCards.forEach(card => {
        resumeGrid.appendChild(card);
    });
}

/**
 * Update empty state visibility based on visible cards
 */
function updateEmptyState() {
    const visibleCards = document.querySelectorAll('.resume-card[style*="block"], .resume-card:not([style*="none"])');
    const emptyState = document.querySelector('.empty-state');
    const resumeGrid = document.querySelector('.resume-grid');
    
    if (visibleCards.length === 0 && emptyState) {
        emptyState.style.display = 'block';
        if (resumeGrid) resumeGrid.style.display = 'none';
    } else if (emptyState) {
        emptyState.style.display = 'none';
        if (resumeGrid) resumeGrid.style.display = 'grid';
    }
}

/**
 * Auto-hide alert messages after 5 seconds
 */
function autoHideAlerts() {
    const alerts = document.querySelectorAll('.alert');
    
    alerts.forEach(alert => {
        setTimeout(() => {
            alert.style.opacity = '0';
            setTimeout(() => {
                alert.remove();
            }, 300);
        }, 5000);
    });
}

/**
 * Escape HTML to prevent XSS
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

/**
 * Show loading state for buttons
 */
function showButtonLoading(button) {
    const originalText = button.textContent;
    button.dataset.originalText = originalText;
    button.innerHTML = '<span class="loading"></span> Loading...';
    button.disabled = true;
}

/**
 * Hide loading state for buttons
 */
function hideButtonLoading(button) {
    const originalText = button.dataset.originalText;
    if (originalText) {
        button.textContent = originalText;
        button.disabled = false;
    }
}

/**
 * Format date for display
 */
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

/**
 * Show success message
 */
function showSuccessMessage(message) {
    showAlert(message, 'success');
}

/**
 * Show error message
 */
function showErrorMessage(message) {
    showAlert(message, 'error');
}

/**
 * Show alert message
 */
function showAlert(message, type) {
    const alertHtml = `
        <div class="alert alert-${type}">
            ${escapeHtml(message)}
        </div>
    `;
    
    const mainContent = document.querySelector('.main-content');
    if (mainContent) {
        mainContent.insertAdjacentHTML('afterbegin', alertHtml);
        
        // Auto-hide after 5 seconds
        setTimeout(() => {
            const alert = mainContent.querySelector('.alert');
            if (alert) {
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 300);
            }
        }, 5000);
    }
}

// Close modal when clicking outside
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal-overlay')) {
        closeModal();
    }
});

// Close modal with Escape key
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape' && currentModal) {
        closeModal();
    }
});