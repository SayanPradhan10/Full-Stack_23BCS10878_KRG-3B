/**
 * Resume Preview and Download JavaScript functionality
 */

// Global variables
let currentResumeId = null;
let downloadModal = null;

// Initialize preview functionality when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    initializePreviewFunctionality();
});

/**
 * Initialize all preview functionality
 */
function initializePreviewFunctionality() {
    // Get resume ID from URL or data attribute
    currentResumeId = getResumeIdFromUrl() || document.body.dataset.resumeId;
    
    // Initialize download buttons
    initializeDownloadButtons();
    
    // Initialize preview controls
    initializePreviewControls();
    
    // Initialize confirmation dialogs
    initializeConfirmationDialogs();
    
    // Initialize print functionality
    initializePrintFunctionality();
    
    // Initialize sharing functionality
    initializeSharingFunctionality();
}

/**
 * Initialize download buttons
 */
function initializeDownloadButtons() {
    // PDF download buttons
    const pdfButtons = document.querySelectorAll('.btn-download-pdf');
    pdfButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const resumeId = this.dataset.resumeId || currentResumeId;
            const templateType = this.dataset.template;
            downloadPDF(resumeId, templateType);
        });
    });
    
    // XML download buttons
    const xmlButtons = document.querySelectorAll('.btn-download-xml');
    xmlButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const resumeId = this.dataset.resumeId || currentResumeId;
            downloadXML(resumeId);
        });
    });
    
    // Combined download button
    const downloadButton = document.getElementById('download-resume-btn');
    if (downloadButton) {
        downloadButton.addEventListener('click', function() {
            showDownloadModal();
        });
    }
}

/**
 * Initialize preview controls
 */
function initializePreviewControls() {
    // Zoom controls
    const zoomInBtn = document.getElementById('zoom-in');
    const zoomOutBtn = document.getElementById('zoom-out');
    const zoomResetBtn = document.getElementById('zoom-reset');
    
    if (zoomInBtn) {
        zoomInBtn.addEventListener('click', () => adjustZoom(1.1));
    }
    
    if (zoomOutBtn) {
        zoomOutBtn.addEventListener('click', () => adjustZoom(0.9));
    }
    
    if (zoomResetBtn) {
        zoomResetBtn.addEventListener('click', () => resetZoom());
    }
    
    // Full screen toggle
    const fullscreenBtn = document.getElementById('fullscreen-toggle');
    if (fullscreenBtn) {
        fullscreenBtn.addEventListener('click', toggleFullscreen);
    }
    
    // Template switcher in preview
    const templateSwitcher = document.getElementById('template-switcher');
    if (templateSwitcher) {
        templateSwitcher.addEventListener('change', function() {
            switchPreviewTemplate(this.value);
        });
    }
}

/**
 * Initialize confirmation dialogs
 */
function initializeConfirmationDialogs() {
    // Download confirmation
    const downloadConfirmButtons = document.querySelectorAll('.btn-download-confirm');
    downloadConfirmButtons.forEach(button => {
        button.addEventListener('click', function() {
            const format = this.dataset.format;
            const resumeId = this.dataset.resumeId || currentResumeId;
            showDownloadConfirmation(resumeId, format);
        });
    });
}

/**
 * Download PDF with optional template
 */
function downloadPDF(resumeId, templateType = null) {
    if (!resumeId) {
        showAlert('Resume ID not found', 'error');
        return;
    }
    
    // Show loading state
    const button = document.querySelector(`[data-resume-id="${resumeId}"].btn-download-pdf`);
    if (button) {
        showButtonLoading(button);
    }
    
    // Construct download URL
    let url = `${getContextPath()}/template/${resumeId}/download/pdf`;
    if (templateType) {
        url += `?templateType=${templateType}`;
    }
    
    // Create temporary link and trigger download
    const link = document.createElement('a');
    link.href = url;
    link.download = '';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    // Hide loading state after delay
    setTimeout(() => {
        if (button) {
            hideButtonLoading(button);
        }
        showAlert('PDF download started', 'success');
    }, 1000);
}

/**
 * Download XML
 */
function downloadXML(resumeId) {
    if (!resumeId) {
        showAlert('Resume ID not found', 'error');
        return;
    }
    
    // Show loading state
    const button = document.querySelector(`[data-resume-id="${resumeId}"].btn-download-xml`);
    if (button) {
        showButtonLoading(button);
    }
    
    // Construct download URL
    const url = `${getContextPath()}/template/${resumeId}/download/xml`;
    
    // Create temporary link and trigger download
    const link = document.createElement('a');
    link.href = url;
    link.download = '';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    // Hide loading state after delay
    setTimeout(() => {
        if (button) {
            hideButtonLoading(button);
        }
        showAlert('XML download started', 'success');
    }, 1000);
}

/**
 * Show download modal with options
 */
function showDownloadModal() {
    const modalHtml = `
        <div class="modal-overlay" id="download-modal">
            <div class="modal download-modal">
                <div class="modal-header">
                    <h3 class="modal-title">Download Resume</h3>
                    <button type="button" class="modal-close" onclick="closeDownloadModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="download-options">
                        <div class="download-option">
                            <div class="download-option-icon">📄</div>
                            <div class="download-option-content">
                                <h4>PDF Format</h4>
                                <p>Professional format ready for printing and sharing with employers.</p>
                                <div class="download-option-features">
                                    <span class="feature-tag">✓ Print-ready</span>
                                    <span class="feature-tag">✓ Universal format</span>
                                    <span class="feature-tag">✓ Professional</span>
                                </div>
                            </div>
                            <div class="download-option-actions">
                                <button type="button" class="btn btn-primary" onclick="confirmDownload('pdf')">
                                    Download PDF
                                </button>
                            </div>
                        </div>
                        
                        <div class="download-option">
                            <div class="download-option-icon">📋</div>
                            <div class="download-option-content">
                                <h4>XML Format</h4>
                                <p>Structured data format for importing into other systems or future editing.</p>
                                <div class="download-option-features">
                                    <span class="feature-tag">✓ Editable</span>
                                    <span class="feature-tag">✓ Structured</span>
                                    <span class="feature-tag">✓ Portable</span>
                                </div>
                            </div>
                            <div class="download-option-actions">
                                <button type="button" class="btn btn-secondary" onclick="confirmDownload('xml')">
                                    Download XML
                                </button>
                            </div>
                        </div>
                    </div>
                    
                    <div class="download-info">
                        <h5>Download Information:</h5>
                        <ul>
                            <li><strong>Current Template:</strong> <span id="current-template-name">Loading...</span></li>
                            <li><strong>Last Modified:</strong> <span id="last-modified-date">Loading...</span></li>
                            <li><strong>File Size:</strong> <span id="estimated-size">Calculating...</span></li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeDownloadModal()">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="downloadBoth()">Download Both</button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
    downloadModal = document.getElementById('download-modal');
    
    // Load resume information
    loadResumeInfo();
}

/**
 * Close download modal
 */
function closeDownloadModal() {
    if (downloadModal) {
        downloadModal.remove();
        downloadModal = null;
    }
}

/**
 * Load resume information for download modal
 */
function loadResumeInfo() {
    if (!currentResumeId) return;
    
    // Fetch resume data
    fetch(`${getContextPath()}/resume/${currentResumeId}/api/data`)
        .then(response => response.json())
        .then(data => {
            if (data) {
                // Update template name
                const templateNameSpan = document.getElementById('current-template-name');
                if (templateNameSpan) {
                    templateNameSpan.textContent = getTemplateDisplayName(data.templateType);
                }
                
                // Update last modified (this would come from resume entity)
                const lastModifiedSpan = document.getElementById('last-modified-date');
                if (lastModifiedSpan) {
                    lastModifiedSpan.textContent = 'Recently';
                }
                
                // Estimate file size
                const sizeSpan = document.getElementById('estimated-size');
                if (sizeSpan) {
                    sizeSpan.textContent = 'PDF: ~150KB, XML: ~5KB';
                }
            }
        })
        .catch(error => {
            console.error('Error loading resume info:', error);
        });
}

/**
 * Confirm download with format
 */
function confirmDownload(format) {
    closeDownloadModal();
    
    if (format === 'pdf') {
        downloadPDF(currentResumeId);
    } else if (format === 'xml') {
        downloadXML(currentResumeId);
    }
}

/**
 * Download both formats
 */
function downloadBoth() {
    closeDownloadModal();
    
    // Download PDF first, then XML after a short delay
    downloadPDF(currentResumeId);
    
    setTimeout(() => {
        downloadXML(currentResumeId);
    }, 1000);
    
    showAlert('Downloading both PDF and XML formats', 'success');
}

/**
 * Show download confirmation dialog
 */
function showDownloadConfirmation(resumeId, format) {
    const formatName = format.toUpperCase();
    const modalHtml = `
        <div class="modal-overlay" id="download-confirm-modal">
            <div class="modal">
                <div class="modal-header">
                    <h3 class="modal-title">Confirm Download</h3>
                    <button type="button" class="modal-close" onclick="closeDownloadConfirmModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <p>Download your resume in ${formatName} format?</p>
                    <div class="download-preview">
                        <div class="preview-info">
                            <strong>Format:</strong> ${formatName}<br>
                            <strong>Template:</strong> <span id="confirm-template">Loading...</span><br>
                            <strong>Estimated Size:</strong> <span id="confirm-size">${format === 'pdf' ? '~150KB' : '~5KB'}</span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeDownloadConfirmModal()">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="proceedDownload('${resumeId}', '${format}')">
                        Download ${formatName}
                    </button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
}

/**
 * Close download confirmation modal
 */
function closeDownloadConfirmModal() {
    const modal = document.getElementById('download-confirm-modal');
    if (modal) {
        modal.remove();
    }
}

/**
 * Proceed with download after confirmation
 */
function proceedDownload(resumeId, format) {
    closeDownloadConfirmModal();
    
    if (format === 'pdf') {
        downloadPDF(resumeId);
    } else if (format === 'xml') {
        downloadXML(resumeId);
    }
}

/**
 * Adjust preview zoom
 */
function adjustZoom(factor) {
    const previewContainer = document.querySelector('.resume-preview-container');
    if (!previewContainer) return;
    
    const currentScale = parseFloat(previewContainer.dataset.scale || '1');
    const newScale = Math.max(0.5, Math.min(2, currentScale * factor));
    
    previewContainer.style.transform = `scale(${newScale})`;
    previewContainer.dataset.scale = newScale;
    
    // Update zoom display
    const zoomDisplay = document.getElementById('zoom-display');
    if (zoomDisplay) {
        zoomDisplay.textContent = `${Math.round(newScale * 100)}%`;
    }
}

/**
 * Reset preview zoom
 */
function resetZoom() {
    const previewContainer = document.querySelector('.resume-preview-container');
    if (!previewContainer) return;
    
    previewContainer.style.transform = 'scale(1)';
    previewContainer.dataset.scale = '1';
    
    // Update zoom display
    const zoomDisplay = document.getElementById('zoom-display');
    if (zoomDisplay) {
        zoomDisplay.textContent = '100%';
    }
}

/**
 * Toggle fullscreen preview
 */
function toggleFullscreen() {
    const previewContainer = document.querySelector('.resume-preview-container');
    if (!previewContainer) return;
    
    if (previewContainer.classList.contains('fullscreen')) {
        // Exit fullscreen
        previewContainer.classList.remove('fullscreen');
        document.body.classList.remove('preview-fullscreen');
    } else {
        // Enter fullscreen
        previewContainer.classList.add('fullscreen');
        document.body.classList.add('preview-fullscreen');
    }
}

/**
 * Switch preview template
 */
function switchPreviewTemplate(templateType) {
    if (!currentResumeId) return;
    
    const previewContainer = document.querySelector('.resume-preview-content');
    if (!previewContainer) return;
    
    // Show loading state
    previewContainer.innerHTML = `
        <div class="preview-loading">
            <div class="loading"></div>
            <p>Switching to ${getTemplateDisplayName(templateType)} template...</p>
        </div>
    `;
    
    // Simulate template switch (in real implementation, this would call the backend)
    setTimeout(() => {
        previewContainer.innerHTML = `
            <div class="template-preview-large">
                ${getTemplatePreviewContent(templateType)}
            </div>
        `;
        
        showAlert(`Switched to ${getTemplateDisplayName(templateType)} template preview`, 'success');
    }, 1500);
}

/**
 * Initialize print functionality
 */
function initializePrintFunctionality() {
    const printButton = document.getElementById('print-resume');
    if (printButton) {
        printButton.addEventListener('click', function() {
            printResume();
        });
    }
}

/**
 * Print resume
 */
function printResume() {
    const previewContent = document.querySelector('.resume-preview-content');
    if (!previewContent) {
        showAlert('No preview content available for printing', 'error');
        return;
    }
    
    // Create print window
    const printWindow = window.open('', '_blank');
    printWindow.document.write(`
        <!DOCTYPE html>
        <html>
        <head>
            <title>Resume - Print</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 0; padding: 20px; }
                .template-preview-large { width: 100%; }
                @media print {
                    body { margin: 0; padding: 0; }
                    .template-preview-large { page-break-inside: avoid; }
                }
            </style>
        </head>
        <body>
            ${previewContent.innerHTML}
        </body>
        </html>
    `);
    
    printWindow.document.close();
    printWindow.focus();
    
    // Wait for content to load, then print
    setTimeout(() => {
        printWindow.print();
        printWindow.close();
    }, 500);
}

/**
 * Initialize sharing functionality
 */
function initializeSharingFunctionality() {
    const shareButton = document.getElementById('share-resume');
    if (shareButton) {
        shareButton.addEventListener('click', function() {
            showShareModal();
        });
    }
}

/**
 * Show share modal
 */
function showShareModal() {
    const shareUrl = window.location.href;
    const modalHtml = `
        <div class="modal-overlay" id="share-modal">
            <div class="modal">
                <div class="modal-header">
                    <h3 class="modal-title">Share Resume</h3>
                    <button type="button" class="modal-close" onclick="closeShareModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="share-options">
                        <div class="share-option">
                            <label for="share-url">Share Link:</label>
                            <div class="share-url-container">
                                <input type="text" id="share-url" value="${shareUrl}" readonly class="form-control">
                                <button type="button" class="btn btn-secondary btn-sm" onclick="copyShareUrl()">Copy</button>
                            </div>
                        </div>
                        
                        <div class="share-social">
                            <h5>Share on Social Media:</h5>
                            <div class="social-buttons">
                                <button type="button" class="btn btn-primary btn-sm" onclick="shareOnLinkedIn()">
                                    LinkedIn
                                </button>
                                <button type="button" class="btn btn-secondary btn-sm" onclick="shareByEmail()">
                                    Email
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeShareModal()">Close</button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHtml);
}

/**
 * Close share modal
 */
function closeShareModal() {
    const modal = document.getElementById('share-modal');
    if (modal) {
        modal.remove();
    }
}

/**
 * Copy share URL to clipboard
 */
function copyShareUrl() {
    const urlInput = document.getElementById('share-url');
    if (urlInput) {
        urlInput.select();
        document.execCommand('copy');
        showAlert('Link copied to clipboard', 'success');
    }
}

/**
 * Share on LinkedIn
 */
function shareOnLinkedIn() {
    const url = encodeURIComponent(window.location.href);
    const text = encodeURIComponent('Check out my professional resume');
    const linkedInUrl = `https://www.linkedin.com/sharing/share-offsite/?url=${url}&text=${text}`;
    
    window.open(linkedInUrl, '_blank', 'width=600,height=400');
}

/**
 * Share by email
 */
function shareByEmail() {
    const subject = encodeURIComponent('My Professional Resume');
    const body = encodeURIComponent(`Hi,\n\nI'd like to share my professional resume with you. You can view it at:\n\n${window.location.href}\n\nBest regards`);
    const emailUrl = `mailto:?subject=${subject}&body=${body}`;
    
    window.location.href = emailUrl;
}

/**
 * Get resume ID from URL
 */
function getResumeIdFromUrl() {
    const pathParts = window.location.pathname.split('/');
    const resumeIndex = pathParts.indexOf('resume');
    
    if (resumeIndex !== -1 && pathParts[resumeIndex + 1]) {
        return pathParts[resumeIndex + 1];
    }
    
    return null;
}

/**
 * Get context path
 */
function getContextPath() {
    return window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) || '';
}

/**
 * Get template display name
 */
function getTemplateDisplayName(templateType) {
    const names = {
        'CLASSIC': 'Classic',
        'MODERN': 'Modern',
        'CREATIVE': 'Creative'
    };
    return names[templateType] || templateType;
}

/**
 * Get template preview content (reuse from template-selection.js)
 */
function getTemplatePreviewContent(templateType) {
    // This function is defined in template-selection.js
    // Include the same implementation here or import it
    const previews = {
        'CLASSIC': `
            <div class="template-preview-classic">
                <div class="preview-header">
                    <h2>John Doe</h2>
                    <p>john.doe@email.com | (555) 123-4567</p>
                </div>
                <div class="preview-section">
                    <h3>Education</h3>
                    <div class="preview-item">
                        <strong>Bachelor of Science in Computer Science</strong><br>
                        University of Technology, 2020-2024
                    </div>
                </div>
                <div class="preview-section">
                    <h3>Experience</h3>
                    <div class="preview-item">
                        <strong>Software Developer Intern</strong><br>
                        Tech Company Inc., Summer 2023
                    </div>
                </div>
                <div class="preview-section">
                    <h3>Skills</h3>
                    <div class="preview-item">
                        Java, Python, JavaScript, React
                    </div>
                </div>
            </div>
        `,
        'MODERN': `
            <div class="template-preview-modern">
                <div class="preview-sidebar">
                    <div class="preview-profile">
                        <div class="preview-avatar"></div>
                        <h2>John Doe</h2>
                        <p>Software Developer</p>
                    </div>
                    <div class="preview-contact">
                        <h4>Contact</h4>
                        <p>john.doe@email.com</p>
                        <p>(555) 123-4567</p>
                    </div>
                    <div class="preview-skills">
                        <h4>Skills</h4>
                        <div class="skill-bar">Java</div>
                        <div class="skill-bar">Python</div>
                        <div class="skill-bar">React</div>
                    </div>
                </div>
                <div class="preview-main">
                    <div class="preview-section">
                        <h3>Education</h3>
                        <div class="preview-item">
                            <strong>Bachelor of Science</strong><br>
                            University of Technology
                        </div>
                    </div>
                    <div class="preview-section">
                        <h3>Experience</h3>
                        <div class="preview-item">
                            <strong>Software Developer Intern</strong><br>
                            Tech Company Inc.
                        </div>
                    </div>
                </div>
            </div>
        `,
        'CREATIVE': `
            <div class="template-preview-creative">
                <div class="preview-header-creative">
                    <div class="preview-name-creative">
                        <h1>John Doe</h1>
                        <h2>Creative Developer</h2>
                    </div>
                    <div class="preview-contact-creative">
                        <p>📧 john.doe@email.com</p>
                        <p>📱 (555) 123-4567</p>
                    </div>
                </div>
                <div class="preview-content-creative">
                    <div class="preview-column">
                        <div class="preview-section-creative">
                            <h3>🎓 Education</h3>
                            <div class="preview-item-creative">
                                <strong>Computer Science Degree</strong><br>
                                University of Technology
                            </div>
                        </div>
                        <div class="preview-section-creative">
                            <h3>🛠️ Skills</h3>
                            <div class="preview-tags">
                                <span class="tag">Java</span>
                                <span class="tag">Python</span>
                                <span class="tag">React</span>
                            </div>
                        </div>
                    </div>
                    <div class="preview-column">
                        <div class="preview-section-creative">
                            <h3>💼 Experience</h3>
                            <div class="preview-item-creative">
                                <strong>Software Developer Intern</strong><br>
                                Tech Company Inc.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        `
    };
    
    return previews[templateType] || '<p>Template preview not available</p>';
}

/**
 * Show loading state for buttons
 */
function showButtonLoading(button) {
    const originalText = button.textContent;
    button.dataset.originalText = originalText;
    button.innerHTML = '<span class="loading"></span> Downloading...';
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

/**
 * Escape HTML to prevent XSS
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Close modals when clicking outside
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal-overlay')) {
        if (downloadModal) closeDownloadModal();
        
        const confirmModal = document.getElementById('download-confirm-modal');
        if (confirmModal) closeDownloadConfirmModal();
        
        const shareModal = document.getElementById('share-modal');
        if (shareModal) closeShareModal();
    }
});

// Close modals with Escape key
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        if (downloadModal) closeDownloadModal();
        
        const confirmModal = document.getElementById('download-confirm-modal');
        if (confirmModal) closeDownloadConfirmModal();
        
        const shareModal = document.getElementById('share-modal');
        if (shareModal) closeShareModal();
    }
});