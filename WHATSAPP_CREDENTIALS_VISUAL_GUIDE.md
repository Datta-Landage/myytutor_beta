# 🎯 WhatsApp Credentials - Visual Guide

```
┌─────────────────────────────────────────────────────────────────────┐
│                    GET YOUR 5 WHATSAPP CREDENTIALS                   │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  1️⃣  PHONE NUMBER ID (From Meta Dashboard)                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  📍 Where: https://developers.facebook.com/                         │
│  🛣️  Path: My Apps → Your App → WhatsApp → API Setup               │
│                                                                      │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  From: Test number                                   │          │
│  │  Phone number ID: 123456789012345  📋 [Copy]         │          │
│  │  WhatsApp Business Account ID: 456789...             │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  ✅ Copy the 15-digit "Phone number ID"                             │
│  📝 Result: WHATSAPP_PHONE_NUMBER_ID=123456789012345                │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  2️⃣  ACCESS TOKEN (Permanent Token - BEST METHOD)                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  📍 Where: https://business.facebook.com/                           │
│  🛣️  Path: Settings → Users → System users → Add                    │
│                                                                      │
│  Step 1: Create System User                                         │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  System user name: MYY Tutor API Bot                 │          │
│  │  System user role: [Admin ▼]                         │          │
│  │                                                       │          │
│  │  [Create system user]                                │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  Step 2: Add Assets                                                 │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  Apps             WhatsApp Accounts                  │          │
│  │  ☑ MYY Tutor Platform                                │          │
│  │  ☑ Manage app                                        │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  Step 3: Generate Token                                             │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  [Generate new token]                                │          │
│  │                                                       │          │
│  │  Select App: MYY Tutor Platform                      │          │
│  │  Permissions:                                        │          │
│  │  ☑ whatsapp_business_management                      │          │
│  │  ☑ whatsapp_business_messaging                       │          │
│  │                                                       │          │
│  │  Token: EAACEdEose0cBAxxx...  📋 [Copy Now!]        │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  ⚠️  IMPORTANT: Copy immediately! You can't see it again!           │
│  ✅ This token NEVER expires!                                       │
│  📝 Result: WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxx...              │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  3️⃣  ADMIN PHONE (Your Phone Number)                                │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  This is YOUR phone number where admin receives inquiry details     │
│                                                                      │
│  Format: [Country Code][Phone Number] (no + or spaces)              │
│                                                                      │
│  Examples:                                                           │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  India:     +91 9623947782  →  919623947782          │          │
│  │  USA:       +1 5551234567   →  15551234567           │          │
│  │  UK:        +44 7700900123  →  447700900123          │          │
│  │  UAE:       +971 501234567  →  971501234567          │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  📝 Result: WHATSAPP_ADMIN_PHONE=919623947782                       │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  4️⃣  COMMUNITY ID (WhatsApp Group ID)                               │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  METHOD 1: Create Group First (Easiest)                             │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  1. Open WhatsApp                                    │          │
│  │  2. Tap "New Group"                                  │          │
│  │  3. Add 1+ contacts                                  │          │
│  │  4. Name: "MYY Tutor Community"                      │          │
│  │  5. Create                                           │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  METHOD 2: Get Group ID Using API                                   │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  curl "https://graph.facebook.com/v18.0/            │          │
│  │        YOUR_PHONE_NUMBER_ID/                         │          │
│  │        whatsapp_business_groups" \                   │          │
│  │    -H "Authorization: Bearer YOUR_TOKEN"             │          │
│  │                                                       │          │
│  │  Response:                                           │          │
│  │  {                                                   │          │
│  │    "data": [{                                        │          │
│  │      "id": "120363XXXXXXXXXX@g.us",                  │          │
│  │      "name": "MYY Tutor Community"                   │          │
│  │    }]                                                │          │
│  │  }                                                   │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  ✅ Copy the "id" value                                             │
│  📝 Result: WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us             │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  5️⃣  VERIFY TOKEN (You Create This!)                                │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  This is a random string YOU make up for security                   │
│                                                                      │
│  Requirements:                                                       │
│  • At least 20 characters                                           │
│  • Mix letters, numbers, symbols                                    │
│  • Make it strong!                                                  │
│                                                                      │
│  Generate Random Token:                                             │
│  ┌──────────────────────────────────────────────────────┐          │
│  │  Windows PowerShell:                                 │          │
│  │  -join ((48..57) + (65..90) + (97..122) |           │          │
│  │         Get-Random -Count 32 | % {[char]$_})         │          │
│  │                                                       │          │
│  │  OR just make up a strong password:                  │          │
│  │  myytutor_2025_SecureToken_XyZ123!                   │          │
│  └──────────────────────────────────────────────────────┘          │
│                                                                      │
│  ✅ Create any strong random string                                 │
│  📝 Result: WHATSAPP_VERIFY_TOKEN=myytutor_2025_XyZ123!             │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│  ✅ YOUR COMPLETE .env.dev FILE                                     │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  # WhatsApp Business Cloud API Configuration                        │
│  WHATSAPP_PHONE_NUMBER_ID=123456789012345                           │
│  WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxx                    │
│  WHATSAPP_ADMIN_PHONE=919623947782                                  │
│  WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us                        │
│  WHATSAPP_VERIFY_TOKEN=myytutor_2025_SecureToken_XyZ123!            │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🚦 Quick Start Flowchart

```
START
  ↓
┌─────────────────────────┐
│ 1. Go to developers.    │
│    facebook.com         │
│ 2. Create App           │
│ 3. Add WhatsApp product │
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ API Setup Page          │
│ → Copy Phone Number ID  │ ────→ WHATSAPP_PHONE_NUMBER_ID
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ business.facebook.com   │
│ → System Users          │
│ → Generate Token        │ ────→ WHATSAPP_ACCESS_TOKEN
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Your Phone Number       │
│ Format: 919876543210    │ ────→ WHATSAPP_ADMIN_PHONE
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Create WhatsApp Group   │
│ → Use API to get ID     │ ────→ WHATSAPP_COMMUNITY_ID
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Create Random String    │
│ (Strong password)       │ ────→ WHATSAPP_VERIFY_TOKEN
└───────────┬─────────────┘
            ↓
┌─────────────────────────┐
│ Add all to .env.dev     │
│ Restart application     │
│ Test! 🎉                │
└─────────────────────────┘
```

---

## 🎬 Step-by-Step Video Timeline

**Total Time: ~20 minutes**

```
00:00 - 05:00  Create Meta Developer Account
               → developers.facebook.com
               → Create App (Business type)
               → Add WhatsApp product

05:00 - 07:00  Get Phone Number ID
               → WhatsApp → API Setup
               → Copy Phone Number ID
               → Add test number

07:00 - 12:00  Get Permanent Access Token
               → business.facebook.com
               → Settings → System Users
               → Create user → Assign assets
               → Generate token → COPY NOW!

12:00 - 13:00  Set Admin Phone
               → Your phone number
               → Format: 919876543210

13:00 - 17:00  Get Community ID
               → Create WhatsApp group
               → Run API call
               → Copy group ID

17:00 - 18:00  Create Verify Token
               → Generate random string
               → Or use PowerShell command

18:00 - 20:00  Configure & Test
               → Add to .env.dev
               → Restart app
               → Submit test inquiry
               → Verify messages arrive
```

---

## 📸 Screenshot Locations

### **Meta Dashboard (developers.facebook.com)**
```
My Apps → [Your App Name]
  ├─ WhatsApp
  │   ├─ API Setup  ← Phone Number ID is here
  │   ├─ Webhooks   ← Configure later
  │   └─ Settings
  └─ Settings
      └─ Basic     ← App ID & App Secret
```

### **Meta Business Suite (business.facebook.com)**
```
Settings ⚙️
  └─ Users
      └─ System users  ← Create here for permanent token
          ├─ Add
          ├─ Assign assets
          └─ Generate token  ← Access Token is here
```

---

## ✅ Verification Tests

### **Test 1: Check Phone Number ID**
```bash
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**✅ Should return phone number details**

### **Test 2: Send Test Message**
```bash
curl -X POST \
  "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/messages" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "messaging_product": "whatsapp",
    "to": "YOUR_ADMIN_PHONE",
    "type": "text",
    "text": {"body": "Test from MYY Tutor! 🎓"}
  }'
```

**✅ Should receive message on WhatsApp**

### **Test 3: List Groups**
```bash
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/whatsapp_business_groups" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**✅ Should list your community**

---

## 🆘 Common Issues

| Problem | Solution |
|---------|----------|
| Can't find Phone Number ID | Go to WhatsApp → API Setup (left sidebar) |
| Token expired after 24h | Use System User token (permanent) |
| Message not received | Add test number in API Setup page |
| Group ID not found | Create group first, then use API |
| Webhook fails | Match verify token exactly (case-sensitive) |

---

## 📋 Checklist Before Testing

- [ ] Phone Number ID is 15 digits
- [ ] Access Token starts with `EAA...`
- [ ] Admin Phone format: `919876543210`
- [ ] Community ID ends with `@g.us`
- [ ] Verify Token is 20+ characters
- [ ] All values added to `.env.dev`
- [ ] Application restarted
- [ ] Test number added in Meta dashboard

---

**🎉 Follow this guide and you'll have all credentials in 20 minutes!**

**Read the detailed guide:** `HOW_TO_GET_WHATSAPP_CREDENTIALS.md`
