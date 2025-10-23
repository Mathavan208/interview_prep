// DOM Elements
document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips
    initializeTooltips();
    
    // Initialize form validation
    initializeFormValidation();
    
    // Initialize confirm dialogs
    initializeConfirmDialogs();
    
    // Initialize AJAX for pagination
    initializeAjaxPagination();
});

// Initialize tooltips
function initializeTooltips() {
    const tooltipElements = document.querySelectorAll('[data-tooltip]');
    
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', function() {
            const tooltipText = this.getAttribute('data-tooltip');
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = tooltipText;
            document.body.appendChild(tooltip);
            
            const rect = this.getBoundingClientRect();
            tooltip.style.left = rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2) + 'px';
            tooltip.style.top = rect.top - tooltip.offsetHeight - 10 + 'px';
            
            this.tooltip = tooltip;
        });
        
        element.addEventListener('mouseleave', function() {
            if (this.tooltip) {
                document.body.removeChild(this.tooltip);
                this.tooltip = null;
            }
        });
    });
}

// Initialize form validation
function initializeFormValidation() {
    const forms = document.querySelectorAll('form[data-validate]');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            let isValid = true;
            
            // Required fields validation
            const requiredFields = form.querySelectorAll('[required]');
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    showError(field, 'This field is required');
                    isValid = false;
                } else {
                    clearError(field);
                }
            });
            
            // Email validation
            const emailFields = form.querySelectorAll('[type="email"]');
            emailFields.forEach(field => {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (field.value && !emailRegex.test(field.value)) {
                    showError(field, 'Please enter a valid email address');
                    isValid = false;
                }
            });
            
            // Number validation
            const numberFields = form.querySelectorAll('[type="number"]');
            numberFields.forEach(field => {
                if (field.value && isNaN(field.value)) {
                    showError(field, 'Please enter a valid number');
                    isValid = false;
                }
            });
            
            if (!isValid) {
                event.preventDefault();
            }
        });
    });
}

// Show error message
function showError(field, message) {
    clearError(field);
    
    field.classList.add('error');
    
    const errorElement = document.createElement('div');
    errorElement.className = 'error';
    errorElement.textContent = message;
    
    field.parentNode.appendChild(errorElement);
    field.errorElement = errorElement;
}

// Clear error message
function clearError(field) {
    field.classList.remove('error');
    
    if (field.errorElement) {
        field.parentNode.removeChild(field.errorElement);
        field.errorElement = null;
    }
}

// Initialize confirm dialogs
function initializeConfirmDialogs() {
    const deleteButtons = document.querySelectorAll('.btn-delete');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            const message = this.getAttribute('data-confirm') || 'Are you sure you want to delete this item?';
            
            if (!confirm(message)) {
                event.preventDefault();
            }
        });
    });
}

// Initialize AJAX for pagination
function initializeAjaxPagination() {
    const paginationLinks = document.querySelectorAll('.pagination a[data-page]');
    
    paginationLinks.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            
            const page = this.getAttribute('data-page');
            const url = new URL(window.location);
            url.searchParams.set('page', page);
            
            // Show loading spinner
            showLoadingSpinner();
            
            // Fetch the new page content
            fetch(url.toString())
                .then(response => response.text())
                .then(html => {
                    // Parse the HTML response
                    const parser = new DOMParser();
                    const doc = parser.parseFromString(html, 'text/html');
                    
                    // Update the table content
                    const tableContainer = document.querySelector('.table-container');
                    if (tableContainer && doc.querySelector('.table-container')) {
                        tableContainer.innerHTML = doc.querySelector('.table-container').innerHTML;
                    }
                    
                    // Update the pagination
                    const pagination = document.querySelector('.pagination');
                    if (pagination && doc.querySelector('.pagination')) {
                        pagination.innerHTML = doc.querySelector('.pagination').innerHTML;
                    }
                    
                    // Update the URL without reloading the page
                    window.history.pushState({}, '', url.toString());
                    
                    // Re-initialize event handlers
                    initializeConfirmDialogs();
                    initializeAjaxPagination();
                    
                    // Hide loading spinner
                    hideLoadingSpinner();
                })
                .catch(error => {
                    console.error('Error fetching page:', error);
                    hideLoadingSpinner();
                });
        });
    });
}

// Show loading spinner
function showLoadingSpinner() {
    const spinner = document.createElement('div');
    spinner.className = 'spinner';
    spinner.id = 'loading-spinner';
    
    const tableContainer = document.querySelector('.table-container');
    if (tableContainer) {
        tableContainer.appendChild(spinner);
    }
}

// Hide loading spinner
function hideLoadingSpinner() {
    const spinner = document.getElementById('loading-spinner');
    if (spinner) {
        spinner.remove();
    }
}

// Filter form submission
function initializeFilters() {
    const filterForm = document.getElementById('filter-form');
    
    if (filterForm) {
        filterForm.addEventListener('change', function() {
            this.submit();
        });
    }
}

// Initialize filters when DOM is loaded
document.addEventListener('DOMContentLoaded', initializeFilters);