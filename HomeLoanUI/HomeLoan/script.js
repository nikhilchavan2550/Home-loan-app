// HomeSpire JavaScript - All Interactive Functionality

document.addEventListener('DOMContentLoaded', function () {
    // Initialize all functionality
    initDynamicHeader();
    initSmoothScrolling();
    initCalculators();
    initTestimonialSlider();
    initFormValidation();
    initScrollAnimations();
});

// ===== DYNAMIC HEADER =====
function initDynamicHeader() {
    const header = document.getElementById('header');
    const logo = document.getElementById('logo');

    if (!header) return;

    function updateHeader() {
        const scrollPosition = window.scrollY;
        const isHomePage = window.location.pathname.endsWith('index.html') || window.location.pathname.endsWith('/');

        if (isHomePage && scrollPosition < 100) {
            // Transparent header for homepage hero section
            header.classList.remove('bg-white', 'shadow-sm');
            header.classList.add('bg-transparent');
            if (logo) logo.classList.add('text-white');

            // Update navigation links to white
            const navLinks = header.querySelectorAll('a:not([href*="login.html"]):not([href*="register.html"]):not([href*="admin_login.html"])');
            navLinks.forEach(link => {
                link.classList.remove('text-primary', 'hover:text-gray-600');
                link.classList.add('text-white', 'hover:text-gray-200');
            });

            // Update login/register/admin buttons
            const loginBtn = header.querySelector('a[href*="login.html"]');
            const registerBtn = header.querySelector('a[href*="register.html"]');
            const adminBtn = header.querySelector('a[href*="admin_login.html"]');

            if (loginBtn) {
                loginBtn.classList.remove('border-primary', 'text-primary', 'hover:bg-primary', 'hover:text-white');
                loginBtn.classList.add('border-white', 'text-white', 'hover:bg-white', 'hover:text-primary');
                // Ensure border-2 is maintained
                if (!loginBtn.classList.contains('border-2')) {
                    loginBtn.classList.add('border-2');
                }
            }

            if (registerBtn) {
                registerBtn.classList.remove('bg-primary', 'text-white', 'hover:bg-gray-800');
                registerBtn.classList.add('bg-white', 'text-primary', 'hover:bg-gray-100', 'border-white');
                // Ensure border-2 is maintained
                if (!registerBtn.classList.contains('border-2')) {
                    registerBtn.classList.add('border-2');
                }
            }

            if (adminBtn) {
                adminBtn.classList.remove('border-primary', 'text-primary', 'hover:bg-primary', 'hover:text-white');
                adminBtn.classList.add('border-white', 'text-white', 'hover:bg-white', 'hover:text-primary');
                // Ensure border-2 is maintained
                if (!adminBtn.classList.contains('border-2')) {
                    adminBtn.classList.add('border-2');
                }
            }
        } else {
            // Solid header for scrolled state or other pages
            header.classList.remove('bg-transparent');
            header.classList.add('bg-white', 'shadow-sm');
            if (logo) logo.classList.remove('text-white');

            // Update navigation links to black
            const navLinks = header.querySelectorAll('a:not([href*="login.html"]):not([href*="register.html"]):not([href*="admin_login.html"])');
            navLinks.forEach(link => {
                link.classList.remove('text-white', 'hover:text-gray-200');
                link.classList.add('text-primary', 'hover:text-gray-600');
            });

            // Update login/register/admin buttons
            const loginBtn = header.querySelector('a[href*="login.html"]');
            const registerBtn = header.querySelector('a[href*="register.html"]');
            const adminBtn = header.querySelector('a[href*="admin_login.html"]');

            if (loginBtn) {
                loginBtn.classList.remove('border-white', 'text-white', 'hover:bg-white', 'hover:text-primary');
                loginBtn.classList.add('border-primary', 'text-primary', 'hover:bg-primary', 'hover:text-white');
                // Ensure border-2 is maintained
                if (!loginBtn.classList.contains('border-2')) {
                    loginBtn.classList.add('border-2');
                }
            }

            if (registerBtn) {
                registerBtn.classList.remove('bg-primary', 'text-white', 'hover:bg-gray-800', 'border-white');
                registerBtn.classList.add('bg-white', 'text-primary', 'hover:bg-gray-100', 'border-primary');
                // Ensure border-2 is maintained
                if (!registerBtn.classList.contains('border-2')) {
                    registerBtn.classList.add('border-2');
                }
            }

            if (adminBtn) {
                adminBtn.classList.remove('border-white', 'text-white', 'hover:bg-white', 'hover:text-primary');
                adminBtn.classList.add('border-primary', 'text-primary', 'hover:bg-primary', 'hover:text-white');
                // Ensure border-2 is maintained
                if (!adminBtn.classList.contains('border-2')) {
                    adminBtn.classList.add('border-2');
                }
            }
        }
    }

    // Update header on scroll
    window.addEventListener('scroll', updateHeader);

    // Initial call
    updateHeader();
}

// ===== SMOOTH SCROLLING =====
function initSmoothScrolling() {
    const links = document.querySelectorAll('a[href^="#"]');

    links.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                const headerHeight = document.getElementById('header')?.offsetHeight || 0;
                const targetPosition = targetElement.offsetTop - headerHeight;

                window.scrollTo({
                    top: targetPosition,
                    behavior: 'smooth'
                });
            }
        });
    });
}

// ===== CALCULATORS =====
function initCalculators() {
    // Tab switching for calculators
    const tabButtons = document.querySelectorAll('.tab-btn');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', function () {
            const targetTab = this.getAttribute('data-tab');

            // Update active tab button
            tabButtons.forEach(btn => {
                btn.classList.remove('active', 'border-primary');
                btn.classList.add('text-gray-500');
            });
            this.classList.add('active', 'border-primary');
            this.classList.remove('text-gray-500');

            // Show target tab content
            tabContents.forEach(content => {
                content.classList.add('hidden');
            });
            document.getElementById(`${targetTab}-calculator`).classList.remove('hidden');
        });
    });

    // EMI Calculator
    const calculateEmiBtn = document.getElementById('calculate-emi');
    if (calculateEmiBtn) {
        calculateEmiBtn.addEventListener('click', calculateEMI);
    }

    // Eligibility Calculator
    const calculateEligibilityBtn = document.getElementById('calculate-eligibility');
    if (calculateEligibilityBtn) {
        calculateEligibilityBtn.addEventListener('click', calculateEligibility);
    }
}

function calculateEMI() {
    const loanAmount = parseFloat(document.getElementById('loan-amount').value) || 0;
    const interestRate = parseFloat(document.getElementById('interest-rate').value) || 0;
    const loanTenure = parseFloat(document.getElementById('loan-tenure').value) || 0;

    if (loanAmount <= 0 || interestRate <= 0 || loanTenure <= 0) {
        alert('Please enter valid values for all fields.');
        return;
    }

    // Convert annual interest rate to monthly
    const monthlyRate = interestRate / 12 / 100;
    const totalMonths = loanTenure * 12;

    // EMI calculation formula: EMI = P × r × (1 + r)^n / ((1 + r)^n - 1)
    const emi = loanAmount * monthlyRate * Math.pow(1 + monthlyRate, totalMonths) /
        (Math.pow(1 + monthlyRate, totalMonths) - 1);

    const totalAmount = emi * totalMonths;
    const totalInterest = totalAmount - loanAmount;

    // Update results
    document.getElementById('monthly-emi').textContent = `₹${Math.round(emi).toLocaleString()}`;
    document.getElementById('total-interest').textContent = `₹${Math.round(totalInterest).toLocaleString()}`;
    document.getElementById('total-amount').textContent = `₹${Math.round(totalAmount).toLocaleString()}`;
}

function calculateEligibility() {
    const monthlyIncome = parseFloat(document.getElementById('monthly-income').value) || 0;
    // const monthlyExpenses = parseFloat(document.getElementById('monthly-expenses').value) || 0;
    const existingEmis = parseFloat(document.getElementById('existing-emis').value) || 0;

    if (monthlyIncome <= 0) {
        alert('Please enter a valid monthly income.');
        return;
    }

    // Calculate disposable income
    const disposableIncome = monthlyIncome - existingEmis;

    if (disposableIncome <= 0) {
        alert('Your monthly expenses exceed your income. Please review your financial situation.');
        return;
    }

    // EMI capacity (typically 40-50% of disposable income)
    const emiCapacity = disposableIncome * 0.45;

    // Maximum loan amount calculation (assuming 8.5% interest rate for 20 years)
    const monthlyRate = 8.5 / 12 / 100;
    const totalMonths = 20 * 12;
    const maxLoanAmount = emiCapacity * (Math.pow(1 + monthlyRate, totalMonths) - 1) /
        (monthlyRate * Math.pow(1 + monthlyRate, totalMonths));

    // Eligibility score (0-100%)
    const eligibilityScore = Math.min(100, (disposableIncome / monthlyIncome) * 100);

    // Update results
    document.getElementById('max-loan-amount').textContent = `₹${Math.round(maxLoanAmount).toLocaleString()}`;
    document.getElementById('emi-capacity').textContent = `₹${Math.round(emiCapacity).toLocaleString()}`;
    document.getElementById('eligibility-score').textContent = `${Math.round(eligibilityScore)}%`;
}

// ===== TESTIMONIAL SLIDER =====
function initTestimonialSlider() {
    const slides = document.querySelectorAll('.testimonial-slide');
    const dots = document.querySelectorAll('.testimonial-dot');

    if (slides.length === 0) return;

    let currentSlide = 0;
    let slideInterval;

    function showSlide(index) {
        // Hide all slides
        slides.forEach(slide => slide.classList.add('hidden'));
        dots.forEach(dot => {
            dot.classList.remove('bg-primary');
            dot.classList.add('bg-gray-300');
        });

        // Show current slide
        slides[index].classList.remove('hidden');
        dots[index].classList.remove('bg-gray-300');
        dots[index].classList.add('bg-primary');

        currentSlide = index;
    }

    function nextSlide() {
        const nextIndex = (currentSlide + 1) % slides.length;
        showSlide(nextIndex);
    }

    // Auto-play functionality
    function startAutoPlay() {
        slideInterval = setInterval(nextSlide, 5000); // Change slide every 5 seconds
    }

    function stopAutoPlay() {
        clearInterval(slideInterval);
    }

    // Dot navigation
    dots.forEach((dot, index) => {
        dot.addEventListener('click', function () {
            stopAutoPlay();
            showSlide(index);
            startAutoPlay();
        });
    });

    // Pause auto-play on hover
    const sliderContainer = document.getElementById('testimonial-slider');
    if (sliderContainer) {
        sliderContainer.addEventListener('mouseenter', stopAutoPlay);
        sliderContainer.addEventListener('mouseleave', startAutoPlay);
    }

    // Start auto-play
    startAutoPlay();
}

// ===== FORM VALIDATION =====
function initFormValidation() {
    const registerForm = document.getElementById('registerForm');

    if (registerForm) {
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');
        const passwordError = document.getElementById('passwordError');
        const panInput = document.getElementById('pan');

        function validatePasswords() {
            if (password.value && confirmPassword.value) {
                if (password.value !== confirmPassword.value) {
                    passwordError.classList.remove('hidden');
                    confirmPassword.classList.add('border-red-500');
                    return false;
                } else {
                    passwordError.classList.add('hidden');
                    confirmPassword.classList.remove('border-red-500');
                    return true;
                }
            }
            return true;
        }

        // PAN validation
        function validatePAN(pan) {
            const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
            return panRegex.test(pan.toUpperCase());
        }

        // Password validation
        function validatePassword(password) {
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
            return passwordRegex.test(password);
        }

        // Real-time password validation
        confirmPassword.addEventListener('input', validatePasswords);
        password.addEventListener('input', validatePasswords);

        // PAN input formatting
        panInput.addEventListener('input', function (e) {
            let value = e.target.value.toUpperCase();
            // Remove any non-alphanumeric characters
            value = value.replace(/[^A-Z0-9]/g, '');
            e.target.value = value;
        });

        // Form submission
        registerForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            if (!validatePasswords()) {
                alert('Please ensure passwords match.');
                return;
            }

            // Additional validation
            const firstName = document.getElementById('firstName').value;
            const lastName = document.getElementById('lastName').value;
            const email = document.getElementById('email').value;
            const pan = document.getElementById('pan').value;

            if (!validatePassword(password.value)) {
                alert('Password must be at least 8 characters with uppercase, lowercase, digit, and special character.');
                return;
            }

            if (!isValidEmail(email)) {
                alert('Please enter a valid email address.');
                return;
            }

            if (!validatePAN(pan)) {
                alert('Please enter a valid PAN number (e.g., ABCDE1234F).');
                return;
            }

            const customerData = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password.value,
                pan: pan.toUpperCase()
            };

            try {
                const response = await fetch('http://localhost:8085/applicant/customer/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(customerData)
                });

                const result = await response.json();

                // Hide old messages
                successMsg.classList.add("hidden");
                errorMsg.classList.add("hidden");

                if (response.ok) {
                    successMsg.textContent = 'Registration successful! Welcome to Orlo Home Loans.';
                    successMsg.classList.remove("hidden");

                    registerForm.reset();

                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 1500);
                } else {
                    errorMsg.textContent = result.message || 'Registration failed.';
                    errorMsg.classList.remove("hidden");
                }
            } catch (error) {
                console.error('Error:', error);
                successMsg.classList.add("hidden");
                errorMsg.textContent = 'Registration failed. Please try again.';
                errorMsg.classList.remove("hidden");
            }

        });
    }

    // Login form handling
    const loginForm = document.querySelector('form');
    if (loginForm && !loginForm.id) { // This is the login form
        loginForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            const errorMsgDiv = document.getElementById('errorMsg');
            if (!email || !password) {
                showLoginError('Please fill in all fields.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8085/applicant/customer/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password
                    })
                });

                const result = await response.json();

                if (response.ok && result.message.includes('successful')) {
                    sessionStorage.setItem('adminData', JSON.stringify({
                        adminId: result.adminId,
                        email: result.email,
                        firstName: result.firstName,
                        lastName: result.lastName
                    }));
                    window.location.href = './Admin/admin-dashboard/dashboard.html';
                } else {
                    showLoginError('Admin login failed: ' + result.message);
                }
            } catch (error) {
                console.error('Error:', error);
                showLoginError('Admin login failed. Please try again.');
            }

        });
    }
    function showLoginError(message) {
        const errorMsgDiv = document.getElementById('errorMsg');
        errorMsgDiv.textContent = message;
        errorMsgDiv.classList.remove('hidden');
    }


    // Forgot password functionality
    const forgotPasswordLink = document.getElementById('forgotPasswordLink');
    if (forgotPasswordLink) {
        forgotPasswordLink.addEventListener('click', function (e) {
            e.preventDefault();

            const email = prompt('Enter your email address:');
            if (!email) return;

            const pan = prompt('Enter your PAN number:');
            if (!pan) return;

            const newPassword = prompt('Enter new password (min 8 chars with uppercase, lowercase, digit, special char):');
            if (!newPassword) return;

            // Validate new password
            const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
            if (!passwordRegex.test(newPassword)) {
                alert('Password must be at least 8 characters with uppercase, lowercase, digit, and special character.');
                return;
            }

            // Send reset request
            fetch('http://localhost:8085/applicant/customer/forgot-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    email: email,
                    pan: pan.toUpperCase(),
                    newPassword: newPassword
                })
            })
                .then(response => response.text())
                .then(result => {
                    alert(result);
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Password reset failed. Please try again.');
                });
        });
    }

    // Admin login form handling
    const adminForm = document.getElementById('adminLoginForm');
    if (adminForm) { // This is the admin login form
        adminForm.addEventListener('submit', async function (e) {
            e.preventDefault();

            const email = document.getElementById('adminEmail').value;
            const password = document.getElementById('adminPassword').value;

            if (!email || !password) {
                alert('Please fill in all fields.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8083/admin/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        email: email,
                        password: password
                    })
                });

                const result = await response.json();

                if (response.ok && result.message.includes('successful')) {
                    // alert('Admin login successful! Welcome to HomeSpire Admin Dashboard.');
                    // Store admin data in session storage
                    sessionStorage.setItem('adminData', JSON.stringify({
                        adminId: result.adminId,
                        email: result.email,
                        firstName: result.firstName,
                        lastName: result.lastName
                    }));
                    // Redirect to admin dashboardHomeLoan\Admin\admin-dashboard\dashboard.html
                    window.location.href = './Admin/admin-dashboard/dashboard.html';
                } else {
                    showLoginError(result.message);
                }
            } catch (error) {
                console.error('Error:', error);
                showLoginError('Admin login failed. Please try again.');
            }
        });
    }
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function isValidPAN(pan) {
    const panRegex = /^[A-Z]{5}[0-9]{4}[A-Z]{1}$/;
    return panRegex.test(pan.toUpperCase());
}

// ===== SCROLL ANIMATIONS =====
function initScrollAnimations() {
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver(function (entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('is-visible');
            }
        });
    }, observerOptions);

    // Observe all sections that should animate
    const sections = document.querySelectorAll('section');
    sections.forEach(section => {
        section.classList.add('fade-in-section');
        observer.observe(section);
    });
}

// ===== UTILITY FUNCTIONS =====

// Format currency
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        minimumFractionDigits: 0
    }).format(amount);
}

// Debounce function for performance
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Throttle function for scroll events
function throttle(func, limit) {
    let inThrottle;
    return function () {
        const args = arguments;
        const context = this;
        if (!inThrottle) {
            func.apply(context, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// Optimize scroll events
const optimizedScrollHandler = throttle(function () {
    // Any scroll-based functionality can be added here
}, 16); // ~60fps

window.addEventListener('scroll', optimizedScrollHandler);

// ===== ERROR HANDLING =====
window.addEventListener('error', function (e) {
    console.error('JavaScript Error:', e.error);
    // You can add error reporting here
});

// ===== PERFORMANCE OPTIMIZATION =====
// Lazy load images
function lazyLoadImages() {
    const images = document.querySelectorAll('img[data-src]');
    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.classList.remove('lazy');
                imageObserver.unobserve(img);
            }
        });
    });

    images.forEach(img => imageObserver.observe(img));
}

// Initialize lazy loading if needed
if ('IntersectionObserver' in window) {
    lazyLoadImages();
} 