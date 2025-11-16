# 🚀 WhatsApp Setup - 5-Minute Cheat Sheet

## 📋 What You Need

```env
WHATSAPP_PHONE_NUMBER_ID=123456789012345
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxx
WHATSAPP_ADMIN_PHONE=919623947782
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us
WHATSAPP_VERIFY_TOKEN=myytutor_2025_XyZ123!
```

---

## ⚡ Quick Steps

### 1️⃣ **Phone Number ID** (2 min)
```
URL: https://developers.facebook.com/
Path: My Apps → Create App → Add WhatsApp → API Setup
Copy: "Phone number ID" (15 digits)
```

### 2️⃣ **Access Token** (5 min)
```
URL: https://business.facebook.com/
Path: Settings → Users → System users → Add
Name: "MYY Tutor API Bot"
Click: "Generate new token"
Permissions: ☑ whatsapp_business_management
             ☑ whatsapp_business_messaging
Copy: Token (starts with EAA...)
⚠️ NEVER EXPIRES - Save it now!
```

### 3️⃣ **Admin Phone** (1 min)
```
Your phone: +91 9623947782
Format: 919623947782 (no + or spaces)
```

### 4️⃣ **Community ID** (3 min)
```bash
# Create WhatsApp group first, then:
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_ID/whatsapp_business_groups" \
  -H "Authorization: Bearer YOUR_TOKEN"

# Copy "id" from response
```

### 5️⃣ **Verify Token** (1 min)
```
Create any strong password (20+ chars)
Example: myytutor_2025_SecureToken_XyZ123!
```

---

## ✅ Test Commands

### Test 1: Verify Token Works
```bash
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_ID" \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Test 2: Send Message
```bash
curl -X POST "https://graph.facebook.com/v18.0/YOUR_PHONE_ID/messages" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "messaging_product": "whatsapp",
    "to": "919876543210",
    "type": "text",
    "text": {"body": "Test! 🎓"}
  }'
```

---

## 🎯 Add to .env.dev

```bash
# Copy these lines to .env.dev
WHATSAPP_PHONE_NUMBER_ID=YOUR_15_DIGIT_ID
WHATSAPP_ACCESS_TOKEN=YOUR_EAA_TOKEN
WHATSAPP_ADMIN_PHONE=91XXXXXXXXXX
WHATSAPP_COMMUNITY_ID=YOUR_GROUP_ID@g.us
WHATSAPP_VERIFY_TOKEN=YOUR_RANDOM_STRING
```

---

## 🚦 Ready to Go!

```bash
# Restart application
.\start-dev.bat

# Test inquiry submission
# Check WhatsApp messages arrive
# ✅ Done!
```

---

**📖 Detailed guides available:**
- `HOW_TO_GET_WHATSAPP_CREDENTIALS.md` - Complete step-by-step (20 min)
- `WHATSAPP_CREDENTIALS_VISUAL_GUIDE.md` - Visual diagrams
- `WHATSAPP_SETUP_GUIDE.md` - Full production setup

**Total Time: ~15 minutes** ⏱️
