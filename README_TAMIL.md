# ANBU TOUR & TRAVELS — Node.js + Supabase booking starter

இந்த bundle உங்கள் HTML website-இன் booking form-ஐ Node.js API வழியாக Supabase `public.bookings` table-க்கு அனுப்புகிறது.

## 1) Supabase SQL

1. https://supabase.com/dashboard/project/sydivopkheonsebieboi/sql/new திறக்கவும்.
2. `supabase_migration.sql` உள்ள code-ஐ paste செய்து Run அழுத்தவும்.
3. ஏற்கெனவே உருவாக்கிய `public.bookings` table இருக்க வேண்டும்.

## 2) Server அமைப்பு

Node.js 20+ உள்ள கணினியில் இந்த folder-ஐ unzip செய்யவும்.

```bash
npm install
cp .env.example .env
```

`.env` file-ஐ திறந்து `SUPABASE_SERVICE_ROLE_KEY` இடத்தில் Supabase Project Settings → API Keys பகுதியில் உள்ள **secret/service_role key**-ஐ இடவும். இந்த key-ஐ HTML, GitHub, screenshot, அல்லது chat-ல் பகிர வேண்டாம்.

```bash
npm start
```

பிறகு `http://localhost:3000` திறக்கவும். API health check: `http://localhost:3000/api/health`.

## 3) Deploy

இந்த Node.js project-ஐ Node.js ஆதரிக்கும் hosting service-ல் deploy செய்து, hosting environment variables-ல் `SUPABASE_URL` மற்றும் `SUPABASE_SERVICE_ROLE_KEY` அமைக்க வேண்டும். Static HTML மட்டும் upload செய்வதால் Node.js API இயங்காது. Hosting deployment இன்னும் செய்யப்படவில்லை.

## பாதுகாப்பு / வரம்புகள்

- Service-role key server `.env`-ல் மட்டுமே இருக்க வேண்டும்.
- இந்த starter பொதுமக்களுக்கு booking உருவாக்க மட்டும் API வழங்குகிறது; owner/admin booking dashboard, status update authentication, reviews/gallery cloud storage, WhatsApp Cloud API notifications இதில் இன்னும் இணைக்கப்படவில்லை.
- Website-இல் இருந்த demo owner PIN `1234`, localStorage reviews/photos/dashboard ஆகியவை இன்னும் demo மட்டுமே; production-ல் பயன்படுத்த வேண்டாம்.
- Supabase technical issue banner இருந்தால் SQL/API செயல்பாட்டில் தாமதம் ஏற்படலாம்.
