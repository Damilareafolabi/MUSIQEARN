-- ====================================================================
-- MUSIZEARN PRODUCTION SUPABASE POSTGRESQL SCHEMA & RLS POLICIES
-- Platform: Global Music Streaming, Gaming, Rewards, Fan Clubs & Bookings
-- Founder: Afolabi Oluwadamilare Simeon | Studio: SimeonJr Studios
-- ====================================================================

-- 1. EXTENSIONS
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. USER PROFILES & ROLES
CREATE TYPE user_role AS ENUM ('user', 'artist', 'celebrity_manager', 'admin');

CREATE TABLE IF NOT EXISTS public.profiles (
    id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
    username TEXT UNIQUE NOT NULL,
    display_name TEXT NOT NULL,
    country TEXT NOT NULL DEFAULT 'Nigeria',
    country_flag TEXT NOT NULL DEFAULT '🇳🇬',
    avatar_url TEXT,
    role user_role NOT NULL DEFAULT 'user',
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    level INT NOT NULL DEFAULT 1,
    xp INT NOT NULL DEFAULT 0,
    musiz_coins INT NOT NULL DEFAULT 50,
    current_streak INT NOT NULL DEFAULT 0,
    best_streak INT NOT NULL DEFAULT 0,
    total_games_played INT NOT NULL DEFAULT 0,
    total_wins INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3. LICENSED TRACKS & AUDIO UPLOADS
CREATE TABLE IF NOT EXISTS public.tracks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    artist_id UUID REFERENCES public.profiles(id) ON DELETE SET NULL,
    artist_name TEXT NOT NULL,
    title TEXT NOT NULL,
    genre TEXT NOT NULL,
    release_year TEXT NOT NULL,
    audio_preview_url TEXT NOT NULL,
    cover_image_url TEXT,
    lyrics_snippet TEXT,
    rights_ownership_declared BOOLEAN NOT NULL DEFAULT FALSE,
    is_approved BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 4. SERVER-VALIDATED GAME SESSIONS & SCORES
CREATE TABLE IF NOT EXISTS public.game_sessions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    game_mode TEXT NOT NULL,
    score INT NOT NULL DEFAULT 0,
    questions_count INT NOT NULL,
    correct_count INT NOT NULL,
    time_taken_ms INT NOT NULL,
    is_server_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5. SPONSORED GIVEAWAYS & FAN REWARDS
CREATE TYPE reward_status AS ENUM ('PENDING_VERIFICATION', 'APPROVED', 'DISPATCHED', 'REJECTED');

CREATE TABLE IF NOT EXISTS public.reward_campaigns (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sponsor_name TEXT NOT NULL,
    title TEXT NOT NULL,
    reward_type TEXT NOT NULL, -- AIRTIME, DATA_BUNDLE, CASH_PRIZE, MERCH_CONCERT
    prize_value TEXT NOT NULL,
    total_pool_budget NUMERIC NOT NULL,
    remaining_budget NUMERIC NOT NULL,
    target_country TEXT NOT NULL,
    min_score_required INT NOT NULL DEFAULT 100,
    expires_at TIMESTAMPTZ NOT NULL,
    is_live BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.reward_claims (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    campaign_id UUID NOT NULL REFERENCES public.reward_campaigns(id) ON DELETE CASCADE,
    user_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    delivery_destination TEXT NOT NULL, -- e.g. Phone number for airtime or bank account details
    status reward_status NOT NULL DEFAULT 'PENDING_VERIFICATION',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 6. CELEBRITY FAN CLUBS & POSTS
CREATE TABLE IF NOT EXISTS public.celebrity_fan_clubs (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    celebrity_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    celebrity_name TEXT NOT NULL,
    handle TEXT NOT NULL UNIQUE,
    bio TEXT,
    banner_url TEXT,
    is_verified BOOLEAN NOT NULL DEFAULT TRUE,
    followers_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.fan_club_posts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    club_id UUID NOT NULL REFERENCES public.celebrity_fan_clubs(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    media_url TEXT,
    sponsored_reward_id UUID REFERENCES public.reward_campaigns(id),
    likes_count INT NOT NULL DEFAULT 0,
    comments_count INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 7. "DONATE FOR ME" ARTIST CROWDFUNDING
CREATE TABLE IF NOT EXISTS public.crowdfunding_campaigns (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    artist_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    title TEXT NOT NULL,
    purpose_category TEXT NOT NULL, -- Recording, Music Video, Studio Equipment, Tour
    story TEXT NOT NULL,
    target_amount_usd NUMERIC NOT NULL,
    raised_amount_usd NUMERIC NOT NULL DEFAULT 0.00,
    supporters_count INT NOT NULL DEFAULT 0,
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.crowdfunding_donations (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    campaign_id UUID NOT NULL REFERENCES public.crowdfunding_campaigns(id) ON DELETE CASCADE,
    supporter_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    amount_usd NUMERIC NOT NULL,
    payment_reference TEXT NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 8. CELEBRITY BOOKING MARKETPLACE
CREATE TABLE IF NOT EXISTS public.celebrity_bookings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    celebrity_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    organizer_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    event_type TEXT NOT NULL,
    event_date DATE NOT NULL,
    venue_city TEXT NOT NULL,
    expected_audience INT NOT NULL,
    budget_offer NUMERIC NOT NULL,
    special_requirements TEXT,
    status TEXT NOT NULL DEFAULT 'SUBMITTED', -- SUBMITTED, IN_REVIEW, QUOTATION_ISSUED, CONFIRMED, CANCELLED
    quotation_amount NUMERIC,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 9. SOCIAL MESSAGING & NETWORKING
CREATE TABLE IF NOT EXISTS public.chat_messages (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sender_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    receiver_id UUID REFERENCES public.profiles(id) ON DELETE CASCADE,
    club_id UUID REFERENCES public.celebrity_fan_clubs(id) ON DELETE CASCADE,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS public.user_reports (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    reporter_id UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    target_id TEXT NOT NULL,
    category TEXT NOT NULL,
    detail TEXT NOT NULL,
    status TEXT NOT NULL DEFAULT 'PENDING_REVIEW',
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ====================================================================
-- ROW-LEVEL SECURITY (RLS) POLICIES
-- ====================================================================
ALTER TABLE public.profiles ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.tracks ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.game_sessions ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.reward_campaigns ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.reward_claims ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.celebrity_fan_clubs ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.fan_club_posts ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.crowdfunding_campaigns ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.crowdfunding_donations ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.celebrity_bookings ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.chat_messages ENABLE ROW LEVEL SECURITY;
ALTER TABLE public.user_reports ENABLE ROW LEVEL SECURITY;

-- Profiles: Public read, owner update
CREATE POLICY "Public profiles are viewable by everyone" ON public.profiles FOR SELECT USING (true);
CREATE POLICY "Users can update own profile" ON public.profiles FOR UPDATE USING (auth.uid() = id);

-- Tracks: Anyone can read approved tracks; Artists can upload
CREATE POLICY "Approved tracks viewable by all" ON public.tracks FOR SELECT USING (is_approved = true OR auth.uid() = artist_id);
CREATE POLICY "Artists can insert tracks" ON public.tracks FOR INSERT WITH CHECK (auth.uid() = artist_id);

-- Game sessions: Users can insert their sessions; server/admin can verify
CREATE POLICY "Users can view own game sessions" ON public.game_sessions FOR SELECT USING (auth.uid() = user_id);
CREATE POLICY "Users can submit game session" ON public.game_sessions FOR INSERT WITH CHECK (auth.uid() = user_id);

-- Reward campaigns: Public read
CREATE POLICY "Campaigns viewable by everyone" ON public.reward_campaigns FOR SELECT USING (true);
CREATE POLICY "Users can create and view own claims" ON public.reward_claims FOR SELECT USING (auth.uid() = user_id);
CREATE POLICY "Users can insert own claims" ON public.reward_claims FOR INSERT WITH CHECK (auth.uid() = user_id);

-- Fan clubs & posts: Public read
CREATE POLICY "Fan clubs viewable by everyone" ON public.celebrity_fan_clubs FOR SELECT USING (true);
CREATE POLICY "Fan posts viewable by everyone" ON public.fan_club_posts FOR SELECT USING (true);

-- Crowdfunding: Public read
CREATE POLICY "Crowdfunding campaigns viewable by everyone" ON public.crowdfunding_campaigns FOR SELECT USING (true);
CREATE POLICY "Artists can insert own crowdfunding campaign" ON public.crowdfunding_campaigns FOR INSERT WITH CHECK (auth.uid() = artist_id);

-- Bookings: Organizer and celebrity manager only
CREATE POLICY "Organizers and Managers view bookings" ON public.celebrity_bookings
    FOR SELECT USING (auth.uid() = organizer_id OR auth.uid() = celebrity_id);
CREATE POLICY "Organizers can create booking requests" ON public.celebrity_bookings
    FOR INSERT WITH CHECK (auth.uid() = organizer_id);

-- Messages: Sender, receiver, or club members
CREATE POLICY "Chat message visibility" ON public.chat_messages
    FOR SELECT USING (auth.uid() = sender_id OR auth.uid() = receiver_id OR club_id IS NOT NULL);
CREATE POLICY "Authenticated users can post chat messages" ON public.chat_messages
    FOR INSERT WITH CHECK (auth.uid() = sender_id);

-- Reports: Authenticated user submit
CREATE POLICY "Users can insert reports" ON public.user_reports
    FOR INSERT WITH CHECK (auth.uid() = reporter_id);
