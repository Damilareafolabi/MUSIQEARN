# MUSIZEARN — Global Music Streaming, Gaming, Fan Rewards & Celebrity Platform

> **LISTEN. CONNECT. PLAY. SUPPORT. EARN.**  
> **Founder & Lead Developer:** Afolabi Oluwadamilare Simeon  
> **Studio:** SimeonJr Studios (Lagos, Nigeria & Worldwide)  
> **Target Market:** Worldwide (Starting with Nigeria & West Africa, expanding globally)  
> **Platform:** Android (Google Play Store)  
> **Backend Architecture:** Firebase Live Cloud Sync + Managed Supabase PostgreSQL with RLS (`/supabase-schema.sql`)

---

## 🎧 The 8 Core Pillars of MUSIZEARN

1. **MUSIZEARN Music (Streaming & Discovery)**
   - Complete music experience with genre category pills (Afrobeats, Amapiano, Highlife, Hip Hop, Gospel, R&B).
   - Popular songs carousel, continuous floating mini-player bar, and full-screen DJ jog-wheel circular vinyl player.
   - Built-in dynamic procedural audio engine synthesized on Android `AudioTrack` ensuring zero unlicensed master streaming during development.

2. **Play & Earn (Competitive Gaming Arena)**
   - **Guess the Song**: Listen to melody previews and choose from four titles against the clock.
   - **Name the Artist**: Identify hit vocalists and producers across eras.
   - **1v1 Music Battle**: Live real-time turn duel with round-by-round point tallies.
   - **Challenge a Friend**: Instant custom code generation with direct WhatsApp/SMS invite dispatch.
   - **Daily Streaks**: Rotating trivia challenges with XP and streak boosts.

3. **Giveaways & Fan Rewards**
   - Verified sponsor campaigns providing real Mobile Airtime (MTN, Airtel, Glo, 9mobile), Data Bundles, and cash tournament pools.
   - Strict qualification criteria: minimum XP score thresholds, anti-cheat latency filters, and claim queue auditing.

4. **Celebrity Fan Clubs & Exclusive Drops**
   - Verified profiles for global stars (Burna Boy, Tems, Asake, Rema).
   - Exclusive announcements, VIP tour giveaways, fan loyalty challenges, likes, and comments.

5. **Donate for Me — Artist Support (Crowdfunding)**
   - Emerging creators launch transparent crowdfunding goals for studio recording, equipment, and music video shoots.
   - Real-time progress bars, backer counts, transparent fee disclosures (92% direct to artist; 8% processing & audit fees), and donor perks.

6. **Connect & Chat (Global Social Network)**
   - Community debate channels, direct messaging, user following, blocking, and reporting tools.

7. **Book a Celebrity (Talent Marketplace)**
   - Event organizers browse verified stars and submit structured event proposals (festival headline, private gala, club tour).
   - Direct connection to verified talent agencies (Spaceship Collective, Mavin, The Cap, Pianochella).
   - Transparent enquiry tracking pipeline: `Submitted` -> `Manager In Review` -> `Quotation Issued` -> `Confirmed` / `Cancelled`.

8. **Artist Studio & Admin Moderation Portal**
   - Artists submit tracks with strict copyright ownership declarations.
   - Administrators review copyright disputes, moderate reported posts, approve/reject song submissions, and monitor monetization streams.

---

## 💼 Business & Monetization Model

MUSIZEARN is architected for sustainable, transparent revenue:
- **Advertising**: Google AdMob banner & interstitial test IDs configured compliant with Google Play Families & Ads policies.
- **Celebrity Booking Commission**: 5% – 10% platform facilitation fee per confirmed booking contract.
- **Crowdfunding Platform Fee**: Transparent 8% processing and auditing fee on successfully disbursed campaigns.
- **Sponsored Giveaways & Tournaments**: Corporate campaign setup fees funded by brands and telecom sponsors.
- **Artist Studio Pro Tools**: Optional analytics, priority moderation, and spotlight badges.

---

## 🗄️ Backend Architecture (Supabase & Firebase)

### 1. Supabase PostgreSQL Schema
Complete database schema with Row-Level Security (RLS) is located at `/supabase-schema.sql`. It defines:
- `profiles` with role-based access (`user`, `artist`, `celebrity_manager`, `admin`).
- `tracks` for licensed audio and copyright metadata.
- `game_sessions` with server-side score verification.
- `reward_campaigns` & `reward_claims`.
- `celebrity_fan_clubs` & `fan_club_posts`.
- `crowdfunding_campaigns` & `crowdfunding_donations`.
- `celebrity_bookings` with negotiation status.
- `chat_messages` & `user_reports`.

### 2. Firebase Cloud Integration
- Provisioned on Google Cloud with zero-trust Firestore security rules (`/firestore.rules`).
- Google Sign-In implemented using Android's modern Jetpack `CredentialManager` (`GetSignInWithGoogleOption`).
- Zero hardcoded client secrets.

---

## 📱 How to Open & Run in Android Studio

1. **Extract / Open**:
   - Open Android Studio (Ladybug / Hedgehog or newer).
   - Select **Open** and choose the `MUSIZEARN` directory.
2. **Sync Gradle**:
   - Gradle will sync using Kotlin 2.0+ and Jetpack Compose BOM.
3. **Run**:
   - Connect an Android device or launch an emulator.
   - Click the green **Run (▶)** button.
4. **Generate Release Bundle**:
   - Go to **Build > Generate Signed Bundle / APK > Android App Bundle (.aab)**.
   - Upload `app/release/app-release.aab` to Google Play Console.
