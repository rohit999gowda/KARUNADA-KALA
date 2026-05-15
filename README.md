# KARUNADA-KALA

## Cultural Discovery & Preservation Platform

**National Pride: Preserving Karnataka's Unique Art Forms**  
**Version:** 1.0  
**Status:** Development  
**Last Updated:** May 2026

---

## 1. Executive Summary

Karunada-Kala is a mobile-first cultural discovery and preservation platform designed to bridge the gap between modern audiences and traditional Karnataka art forms. The app serves as a comprehensive directory connecting learners, tourists, and enthusiasts with authentic artisans, performance venues, and hands-on workshops across Karnataka.

By combining cultural education, artisan marketplace access, and event discovery, Karunada-Kala aims to preserve endangered art forms while creating a sustainable creative economy for rural artisans.

## 2. Problem Statement

### The Challenge

Karnataka's rich tapestry of traditional art forms, including Yakshagana, Kinnala toys, Bidriware, Ilkal weaving, and Dollu Kunitha, faces an existential threat in the modern era. These arts struggle due to:

- Limited geographic reach: Most performances and workshops exist in isolated villages with minimal visibility
- Information asymmetry: Potential learners cannot easily find authentic Guru-Shishya training centers
- Market access: Artisans lack direct channels to sell products to consumers beyond local communities
- Declining interest: Younger generations lack awareness of these art forms and their cultural significance
- Fragmented event calendars: No centralized way to discover upcoming performances or festivals

### Impact of Inaction

Without intervention, these art forms risk permanent extinction, resulting in loss of cultural sovereignty, erosion of Karnataka's unique identity, and reduced economic opportunities for rural artisans.

## 3. Vision & Strategic Goals

### Core Vision

To be Karnataka's definitive cultural discovery platform, preserving and celebrating traditional art forms while creating sustainable pathways for artisans to thrive in the modern economy.

### Strategic Goals

#### Cultural Sovereignty

Preserve and amplify Karnataka's unique artistic identity in an increasingly globalized world by creating a centralized repository and celebration of local traditions.

#### Creative Economy

Provide direct market access to rural artisans, enabling them to sell products and services directly to consumers without intermediaries, thereby increasing income and sustainability.

#### Cultural Tourism

Promote experiential tourism where visitors travel to villages to learn traditional arts hands-on, benefiting local economies while fostering genuine cultural exchange.

## 4. Primary User Personas

### Persona 1: The Culture Enthusiast

**Name:** Priya, 28, Bangalore Software Professional  
**Background:** Urban professional with growing interest in traditional arts. Wants to learn Yakshagana but does not know where to start.  
**Goals:** Find authentic training centers, understand art form history, attend live performances, purchase authentic products  
**Needs:** Clear information, location-based discovery, workshop booking, video learning resources

### Persona 2: The Artisan Entrepreneur

**Name:** Ramesh, 52, Bidriware Craftsman  
**Background:** 4th generation Bidriware artisan based in a small village. Struggles to sell products beyond local market.  
**Goals:** Expand market reach, teach workshops to interested learners, preserve family craft tradition  
**Needs:** Easy product listing, direct customer contact, workshop scheduling, visibility

### Persona 3: The Cultural Tourist

**Name:** Michael, 35, International Visitor  
**Background:** Traveling through India with interest in authentic cultural experiences beyond typical tourist attractions.  
**Goals:** Discover unique art forms, attend live performances, purchase authentic souvenirs, participate in workshops  
**Needs:** English interface, clear event calendar, location maps, background info on art forms

### Persona 4: The Performance Organizer

**Name:** Asha, 45, Festival Coordinator  
**Background:** Coordinates cultural festivals and local events in various Karnataka towns.  
**Goals:** Promote upcoming performances, connect with troupes, increase audience attendance  
**Needs:** Event listing tools, audience reach, promotion features, troupe directories

## 5. Core Features

### 5.1 Art Form Explorer

A searchable, categorized catalog of Karnataka's traditional art forms with rich multimedia content.

**Features:**

- Detailed art form cards with history, origins, and cultural significance
- Embedded YouTube videos and performance clips
- Photo galleries of traditional costumes, instruments, and artifacts
- Related artists and troupes links
- Search and filter by art form type, region, and heritage status

### 5.2 Artisan Map

An interactive map displaying artisans, workshops, and performance venues across Karnataka with precise location data.

**Features:**

- Google Maps integration with custom markers differentiated by type
- Distinct icons for Workshops, Performance Venues, and Product Makers
- Tap marker to view artisan profile with photo, bio, specialization, and contact info
- "Tap to Call" button for direct phone contact
- Distance calculation and directions via Google Maps
- Filter by art form, distance radius, and availability
- Ratings and reviews from workshop participants

### 5.3 Workshop & Learning Pathways

Browse and register for hands-on learning experiences in traditional arts.

**Features:**

- Workshop listing with dates, times, durations, and pricing
- Guru profiles and background information
- Registration form with participant information collection
- Confirmation emails and SMS notifications
- Payment integration for fee-based workshops
- Difficulty levels: Beginner, Intermediate, Advanced

### 5.4 Event Feed & Performance Calendar

A real-time feed of upcoming Yakshagana, Dollu Kunitha, and other performance events across Karnataka.

**Features:**

- Community-contributed event listings
- Calendar view with month/week/day navigation
- Performance type filtering (Yakshagana, Dollu Kunitha, Koodiyattam, etc.)
- Location-based "Events Near Me"
- Event details: date, time, venue, admission price, performer info
- "Interested" / "Going" quick-tap features
- Share event via social media or messaging apps

### 5.5 Artisan Marketplace

Direct-to-consumer sales platform for handmade products.

**Features:**

- Product listings with photos, descriptions, pricing, and inventory
- Artisan shop profiles with ratings and reviews
- Simple checkout process (future phase: integrated payments)
- Contact artisan directly for custom orders
- Product categories: Bidriware, Weavings, Toys, Instruments, Jewelry

## 6. Design & Branding Guidelines

### Color Palette

The app employs the colors of the Karnataka flag, tastefully integrated into the UI:

- Karnataka Red (`#C60C30`): Primary headers, buttons, brand elements
- Karnataka Blue (`#003DA5`): Secondary headers, accents
- Neutral White (`#FFFFFF`): Backgrounds, content areas

### Typography

- Primary Font: Roboto (Android) / San Francisco (iOS) for clean, modern appearance
- Headings: Bold, 24-32pt
- Body Text: Regular, 14-16pt
- Accent Text: Italics for quotes and cultural terms

### Map Icons

Distinct icons differentiate location types:

- Workshop: Orange pin with mortar and pestle icon
- Performance Venue: Red pin with theatre mask icon
- Artisan Shop: Blue pin with shopping bag icon

## 7. Technical Implementation

### Platform & Architecture

- Native mobile apps: iOS (Swift) and Android (Kotlin)
- Responsive web version for desktop access
- Minimum iOS 13, Android 9.0

### Key APIs & Libraries

#### Maps & Location

- Google Maps API (iOS Maps SDK, Android Maps SDK)
- Location services for "Find Near Me" functionality
- Geofencing for event notifications

#### Media & Video

- ExoPlayer (Android) for high-performance video playback
- AVFoundation (iOS) for video handling
- YouTube iFrame Embed for performance clips
- Image caching and optimization libraries (Glide, Picasso)

#### Backend & Database

- Firebase (Realtime Database or Firestore)
- User authentication: Firebase Auth
- Cloud Storage for images and videos
- REST API endpoints for event and artisan data

#### Communications

- Push notifications (Firebase Cloud Messaging)
- SMS integration for workshop confirmations
- In-app messaging system

## 8. Success Metrics

### User Engagement

- Monthly Active Users (MAU): target 50,000 in Year 1
- Average session duration: over 8 minutes
- Workshop registration conversion rate: over 15%
- Event attendance rate: over 20% of interested users

### Cultural Impact

- Number of artisans onboarded: 200+ by Year 1
- Events listed on platform: 500+ annually
- Workshop participants trained: 5,000+ annually
- Art forms documented: all 15+ major forms with multimedia

### Economic Impact

- Artisan revenue through platform: target INR 50 lakhs in Year 1
- Average income increase per artisan: 25% by Year 2
- Repeat customer rate for artisan products: over 40%

## 9. Development Roadmap

### Phase 1: MVP (Months 1-3)

- Art Form Explorer with 10 major forms
- Basic Artisan Map with 50 verified artisans
- Workshop listing and registration (non-payment)
- Event feed for 5 districts
- Android app launch

### Phase 2: Expansion (Months 4-6)

- iOS app launch
- Artisan Marketplace MVP (product listings)
- Expand artisans to 150 across all districts
- User reviews and ratings system
- Push notifications for nearby events

### Phase 3: Monetization & Scale (Months 7-12)

- Integrated payment processing (workshops and products)
- Premium artisan profiles (featured listings)
- Web dashboard for artisans to manage orders
- Tourism partnerships (hotels, tour operators)
- 1,000+ registered artisans

## 10. Constraints & Assumptions

### Assumptions

- Target users have smartphones with internet access
- Artisans will actively maintain their profiles and availability
- Government/NGO support for initial artisan onboarding
- Community willingness to contribute event information

### Constraints

- Data accuracy: Community-sourced event data may need moderation
- Connectivity: Rural areas have limited internet infrastructure
- Language: Initial launch in Kannada and English
- Funding: Limited resources for infrastructure and marketing
- Payment infrastructure: May need partnerships with local payment gateways

## 11. Development Success Criteria

### Map Implementation

- Map displays with Google Maps API fully integrated
- Distinct icon types visible for Workshops vs Performances
- Tap on marker reveals artisan profile with all relevant details
- "Tap to Call" button implemented and functional
- Location search and filters working smoothly

### Artisan Profile

- Profile displays photo, name, specialization, and location
- "Tap to Call" feature enabled (direct call from app)
- Workshop schedule visible with easy registration
- Customer reviews and ratings displayed
- Product listings (if applicable) accessible

### User Interface

- Karnataka flag colors (`#C60C30`, `#003DA5`) tastefully integrated
- Header bar uses red, secondary elements use blue
- Color contrast meets accessibility standards
- Clean, modern design without overwhelming use of colors
- Consistent branding across all screens

### Performance & Quality

- App loads in under 3 seconds on 4G
- Maps respond smoothly to gestures (pinch, scroll, zoom)
- No crashes or memory leaks during extended use
- Images optimized for mobile (minimal data usage)
- Offline caching for critical data

## 12. Data Privacy & Security

### Data Collection

The app collects the following user data:

- Basic profile info (name, email, phone for authentication)
- Location data (for map-based features, with explicit user consent)
- Workshop registration data (contact info, preferences)
- Event interest tracking (for recommendations)

### Privacy Commitments

- GDPR and data protection compliance
- User data encryption in transit (HTTPS/TLS)
- Clear privacy policy explaining data usage
- Users can delete accounts and associated data on request
- No third-party advertising or data selling

## Appendix: Featured Art Forms

- **Yakshagana:** Ancient classical narrative dance-drama from coastal Karnataka with elaborate costumes and makeup
- **Dollu Kunitha:** Rhythmic drum dance performed during festivals, popular in Deccan regions
- **Bidriware:** Intricate metalware craft using brass, copper, and zinc with traditional inlay techniques
- **Ilkal Weaving:** Traditional hand-woven silk and cotton sarees with unique border designs from Belagavi district
- **Kinnala Toys:** Handcrafted clay and wooden toys, important folk art from rural Karnataka
And few more if there are found
---

End of document.
