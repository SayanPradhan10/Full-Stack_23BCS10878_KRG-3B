// Resume Preview JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Initialize preview functionality
    initializeZoomControls();
    initializeDownloadButtons();
    initializePrintButton();
    initializeShareButton();
    initializeTemplateSwitch();
    initializeFullscreen();
});

// Zoom Controls
function initializeZoomControls() {
    const zoomInBtn = document.getElementById('zoom-in');
    const zoomOutBtn = document.getElementById('zoom-out');
    const zoomResetBtn = document.getElementById('zoom-reset');
    const zoomDisplay = document.getElementById('zoom-display');
    const previewContainer = document.querySelector('.resume-preview-container');
    
    let currentZoom = 1;
    
    if (zoomInBtn && zoomOutBtn && zoomResetBtn) {
        zoomInBtn.addEventListener('click', function() {
            currentZoom = Math.min(currentZoom + 0.1, 2);
            updateZoom();
        });
        
        zoomOutBtn.addEventListener('click', function() {
            currentZoom = Math.max(currentZoom - 0.1, 0.5);
            updateZoom();
        });
        
        zoomResetBtn.addEventListener('click', function() {
            currentZoom = 1;
            updateZoom();
        });
    }
    
    function updateZoom() {
        if (previewContainer && zoomDisplay) {
            previewContainer.setAttribute('data-scale', currentZoom);
            const content = previewContainer.querySelector('.resume-preview-content');
            if (content) {
                content.style.transform = `scale(${currentZoom})`;
            }
            zoomDisplay.textContent = Math.round(currentZoom * 100) + '%';
        }
    }
}

// Download Buttons
function initializeDownloadButtons() {
    const downloadResumeBtn = document.getElementById('download-resume-btn');
    const downloadPdfBtns = document.querySelectorAll('.btn-download-pdf');
    const downloadXmlBtns = document.querySelectorAll('.btn-download-xml');
    
    // Main download button
    if (downloadResumeBtn) {
        downloadResumeBtn.addEventListener('click', function() {
            const resumeId = document.body.getAttribute('data-resume-id');
            if (resumeId) {
                downloadPDF(resumeId);
            }
        });
    }
    
    // PDF download buttons
    downloadPdfBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const resumeId = this.getAttribute('data-resume-id');
            if (resumeId) {
                downloadPDF(resumeId);
            }
        });
    });
    
    // XML download buttons
    downloadXmlBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const resumeId = this.getAttribute('data-resume-id');
            if (resumeId) {
                downloadXML(resumeId);
            }
        });
    });
}

// Download PDF function
function downloadPDF(resumeId) {
    showLoadingState('Generating PDF...');
    
    fetch(`/pdf/generate/${resumeId}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.blob();
        }
        throw new Error('PDF generation failed');
    })
    .then(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = `resume-${resumeId}.pdf`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        hideLoadingState();
        showMessage('PDF downloaded successfully!', 'success');
    })
    .catch(error => {
        console.error('Error downloading PDF:', error);
        hideLoadingState();
        showMessage('Failed to download PDF. Please try again.', 'error');
    });
}

// Download XML function
function downloadXML(resumeId) {
    showLoadingState('Generating XML...');
    
    fetch(`/xml/export/${resumeId}`, {
        method: 'GET',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.text();
        }
        throw new Error('XML export failed');
    })
    .then(xmlContent => {
        const blob = new Blob([xmlContent], { type: 'application/xml' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.style.display = 'none';
        a.href = url;
        a.download = `resume-${resumeId}.xml`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        hideLoadingState();
        showMessage('XML downloaded successfully!', 'success');
    })
    .catch(error => {
        console.error('Error downloading XML:', error);
        hideLoadingState();
        showMessage('Failed to download XML. Please try again.', 'error');
    });
}

// Print Button
function initializePrintButton() {
    const printBtn = document.getElementById('print-resume');
    
    if (printBtn) {
        printBtn.addEventListener('click', function() {
            window.print();
        });
    }
}

// Share Button
function initializeShareButton() {
    const shareBtn = document.getElementById('share-resume');
    
    if (shareBtn) {
        shareBtn.addEventListener('click', function() {
            const resumeId = document.body.getAttribute('data-resume-id');
            const shareUrl = `${window.location.origin}/resume/${resumeId}`;
            
            if (navigator.share) {
                navigator.share({
                    title: 'My Resume',
                    url: shareUrl
                }).catch(console.error);
            } else {
                // Fallback: copy to clipboard
                navigator.clipboard.writeText(shareUrl).then(() => {
                    showMessage('Resume link copied to clipboard!', 'success');
                }).catch(() => {
                    showMessage('Failed to copy link. Please copy manually: ' + shareUrl, 'error');
                });
            }
        });
    }
}

// Template Switcher
function initializeTemplateSwitch() {
    const templateSwitcher = document.getElementById('template-switcher');
    
    if (templateSwitcher) {
        templateSwitcher.addEventListener('change', function() {
            const resumeId = document.body.getAttribute('data-resume-id');
            const newTemplate = this.value;
            
            if (resumeId && newTemplate) {
                showLoadingState('Switching template...');
                
                fetch(`/resume/${resumeId}/template`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    body: `templateType=${newTemplate}&${getCsrfToken()}`
                })
                .then(response => {
                    if (response.ok) {
                        location.reload();
                    } else {
                        throw new Error('Template switch failed');
                    }
                })
                .catch(error => {
                    console.error('Error switching template:', error);
                    hideLoadingState();
                    showMessage('Failed to switch template. Please try again.', 'error');
                });
            }
        });
    }
}

// Fullscreen Toggle
function initializeFullscreen() {
    const fullscreenBtn = document.getElementById('fullscreen-toggle');
    
    if (fullscreenBtn) {
        fullscreenBtn.addEventListener('click', function() {
            const previewContainer = document.querySelector('.resume-preview-container');
            
            if (previewContainer) {
                if (!document.fullscreenElement) {
                    previewContainer.requestFullscreen().catch(console.error);
                    this.textContent = '⛶ Exit Fullscreen';
                } else {
                    document.exitFullscreen().catch(console.error);
                    this.textContent = '⛶ Fullscreen';
                }
            }
        });
        
        // Listen for fullscreen changes
        document.addEventListener('fullscreenchange', function() {
            const fullscreenBtn = document.getElementById('fullscreen-toggle');
            if (fullscreenBtn) {
                if (document.fullscreenElement) {
                    fullscreenBtn.textContent = '⛶ Exit Fullscreen';
                } else {
                    fullscreenBtn.textContent = '⛶ Fullscreen';
                }
            }
        });
    }
}

// Utility Functions
function showLoadingState(message) {
    // Create or update loading overlay
    let overlay = document.getElementById('loading-overlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.id = 'loading-overlay';
        overlay.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0,0,0,0.5);
            display: flex;
            align-items: center;
            justify-content: center;
            z-index: 9999;
            color: white;
            font-size: 1.2rem;
        `;
        document.body.appendChild(overlay);
    }
    overlay.textContent = message;
    overlay.style.display = 'flex';
}

function hideLoadingState() {
    const overlay = document.getElementById('loading-overlay');
    if (overlay) {
        overlay.style.display = 'none';
    }
}

function showMessage(message, type) {
    // Create message element
    const messageEl = document.createElement('div');
    messageEl.className = `alert alert-${type}`;
    messageEl.textContent = message;
    messageEl.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        z-index: 10000;
        max-width: 300px;
        animation: slideIn 0.3s ease-out;
    `;
    
    // Add animation styles
    const style = document.createElement('style');
    style.textContent = `
        @keyframes slideIn {
            from { transform: translateX(100%); opacity: 0; }
            to { transform: translateX(0); opacity: 1; }
        }
    `;
    document.head.appendChild(style);
    
    document.body.appendChild(messageEl);
    
    // Auto remove after 5 seconds
    setTimeout(() => {
        if (messageEl.parentNode) {
            messageEl.parentNode.removeChild(messageEl);
        }
    }, 5000);
}

function getCsrfToken() {
    const token = document.querySelector('meta[name="_csrf"]');
    const header = document.querySelector('meta[name="_csrf_header"]');
    
    if (token && header) {
        return `${header.getAttribute('content')}=${token.getAttribute('content')}`;
    }
    return '';
}

// Keyboard shortcuts
document.addEventListener('keydown', function(e) {
    // Ctrl/Cmd + P for print
    if ((e.ctrlKey || e.metaKey) && e.key === 'p') {
        e.preventDefault();
        window.print();
    }
    
    // Ctrl/Cmd + S for download
    if ((e.ctrlKey || e.metaKey) && e.key === 's') {
        e.preventDefault();
        const resumeId = document.body.getAttribute('data-resume-id');
        if (resumeId) {
            downloadPDF(resumeId);
        }
    }
});