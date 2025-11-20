// Initialize the map
let map;
let diveSites = [];

// Initialize map when the page loads
document.addEventListener('DOMContentLoaded', function() {
    initMap();
    loadDiveSites();
});

function initMap() {
    // Create map centered on a default location (Mediterranean Sea)
    map = L.map('map').setView([36.0, 15.0], 4);

    // Add OpenStreetMap tiles
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
        maxZoom: 19
    }).addTo(map);
}

function loadDiveSites() {
    // Fetch dive sites from the REST API
    fetch('/api/v1/sites')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch dive sites');
            }
            return response.json();
        })
        .then(sites => {
            diveSites = sites;
            displayDiveSites(sites);
        })
        .catch(error => {
            console.error('Error loading dive sites:', error);
            // Show a user-friendly message
            alert('Unable to load dive sites. Please make sure the backend server is running.');
        });
}

function displayDiveSites(sites) {
    if (sites.length === 0) {
        console.log('No dive sites to display');
        return;
    }

    // Create markers for each dive site
    sites.forEach(site => {
        const marker = L.marker([site.latitude, site.longitude])
            .addTo(map);

        // Create popup content
        const popupContent = `
            <div class="popup-content">
                <h3>${escapeHtml(site.siteName)}</h3>
                <p><strong>Coordinates:</strong> ${site.latitude.toFixed(4)}, ${site.longitude.toFixed(4)}</p>
                <button onclick="showSiteDetails(${site.latitude}, ${site.longitude})">View Details</button>
            </div>
        `;

        marker.bindPopup(popupContent);
        
        // Store site data with the marker
        marker.siteData = site;
        
        // Add click event to show details
        marker.on('click', function() {
            // Popup will show automatically, button will trigger details
        });
    });

    // Fit map to show all markers if there are multiple sites
    if (sites.length > 1) {
        const bounds = L.latLngBounds(sites.map(site => [site.latitude, site.longitude]));
        map.fitBounds(bounds, { padding: [50, 50] });
    } else if (sites.length === 1) {
        map.setView([sites[0].latitude, sites[0].longitude], 12);
    }
}

function showSiteDetails(lat, lng) {
    // Find the site by coordinates
    const site = diveSites.find(s => s.latitude === lat && s.longitude === lng);
    
    if (!site) {
        console.error('Site not found');
        return;
    }

    // Populate the details panel
    document.getElementById('site-name').textContent = site.siteName;
    document.getElementById('site-description').textContent = site.description;
    document.getElementById('site-lat').textContent = site.latitude.toFixed(6);
    document.getElementById('site-lng').textContent = site.longitude.toFixed(6);

    // Show the details panel
    document.getElementById('site-details').classList.remove('hidden');
    
    // Create overlay
    let overlay = document.querySelector('.overlay');
    if (!overlay) {
        overlay = document.createElement('div');
        overlay.className = 'overlay';
        overlay.onclick = closeSiteDetails;
        document.body.appendChild(overlay);
    }
    overlay.classList.add('active');
}

function closeSiteDetails() {
    document.getElementById('site-details').classList.add('hidden');
    const overlay = document.querySelector('.overlay');
    if (overlay) {
        overlay.classList.remove('active');
    }
}

// Utility function to escape HTML and prevent XSS
function escapeHtml(text) {
    const map = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    };
    return text.replace(/[&<>"']/g, m => map[m]);
}

// Close details panel when pressing Escape key
document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeSiteDetails();
    }
});
