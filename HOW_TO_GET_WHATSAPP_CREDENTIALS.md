# 🎯 How to Get WhatsApp Credentials - Step by Step

## 📋 What You Need to Get

```env
WHATSAPP_PHONE_NUMBER_ID=123456789012345          # From Meta Dashboard
WHATSAPP_ACCESS_TOKEN=EAA...                      # From Meta Dashboard (Permanent Token)
WHATSAPP_ADMIN_PHONE=919623947782                 # Your Phone Number
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us       # From WhatsApp Group
WHATSAPP_VERIFY_TOKEN=mySecureToken123            # You Create This
```

---

## 🚀 Step-by-Step Guide

### **STEP 1: Create Meta Business Account** ⏱️ 5 minutes

#### **1.1 Go to Meta for Developers**
```
URL: https://developers.facebook.com/
```

**Actions:**
1. Click **"Get Started"** button (top right)
2. Login with Facebook account (create one if needed)
3. Complete developer profile:
   - Full Name
   - Email
   - Phone (optional)
   - Accept terms

#### **1.2 Create Your App**
1. Click **"My Apps"** (top navigation)
2. Click **"Create App"** button
3. Select app type: **"Business"** ✅
4. Click **"Next"**

#### **1.3 Fill App Details**
```
App Display Name: MYY Tutor Platform
App Contact Email: your-email@example.com
Business Portfolio: (Select or create new)
```

5. Click **"Create App"**
6. Complete security check (if prompted)
7. You'll see your App Dashboard

**✅ Result:** You now have a Meta App ID

---

### **STEP 2: Add WhatsApp Product** ⏱️ 3 minutes

#### **2.1 In Your App Dashboard**
1. Scroll to **"Add products to your app"** section
2. Find **"WhatsApp"** card
3. Click **"Set up"** button

#### **2.2 WhatsApp Business Account**
You'll see one of these:

**Option A: No existing WhatsApp Business**
- Click **"Create new WhatsApp Business Account"**
- Fill in business details:
  ```
  Business Name: MYY Tutor Services
  Category: Education
  Business Description: Online tutoring platform
  ```
- Click **"Continue"**

**Option B: Have existing WhatsApp Business**
- Click **"Use existing WhatsApp Business Account"**
- Select your account from dropdown

**✅ Result:** WhatsApp product added to your app

---

### **STEP 3: Get Phone Number ID** ⏱️ 2 minutes

#### **3.1 Navigate to API Setup**
```
Left Sidebar: WhatsApp → API Setup
```

#### **3.2 Find Test Phone Number**
You'll see a section **"Send and receive messages"** with:

```
┌─────────────────────────────────────────┐
│ From: Test number                       │
│ Phone number ID: 123456789012345 📋     │
│ WhatsApp Business Account ID: 456789... │
└─────────────────────────────────────────┘
```

**Copy the Phone number ID!**

#### **3.3 Add Your Test Number** (For testing only)
1. Scroll to **"To"** field
2. Click **"Add phone number"**
3. Select your country: **India (+91)**
4. Enter YOUR phone number: `9876543210`
5. Click **"Send code"**
6. Enter OTP received on WhatsApp
7. Click **"Verify"**

**✅ Result:** 
```env
WHATSAPP_PHONE_NUMBER_ID=123456789012345  ✅ (Copy this!)
```

---

### **STEP 4: Get Access Token** ⏱️ 5 minutes

You have 2 options:

---

#### **OPTION A: Temporary Token** (Valid 23 hours - For Testing Only)

**In API Setup Page:**
1. Find section **"Access tokens"**
2. Click **"Generate access token"** button
3. Token appears (starts with `EAA...`)
4. Click **📋 Copy** icon

```env
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxxxxx  ✅
```

**⚠️ Warning:** This token expires in 23 hours! Use for testing only.

---

#### **OPTION B: Permanent Token** (Recommended for Production)

##### **Method 1: System User Token (Best)**

**Step 1: Create System User**
1. Go to **Meta Business Suite**: https://business.facebook.com/
2. Click **Settings** (⚙️ gear icon, bottom left)
3. Navigate to **Users → System users**
4. Click **"Add"** button
5. Fill details:
   ```
   System user name: MYY Tutor API Bot
   System user role: Admin
   ```
6. Click **"Create system user"**

**Step 2: Assign Assets**
1. Click on your new system user name
2. Click **"Add Assets"** button
3. Select **"Apps"** tab
4. Find your app: `MYY Tutor Platform`
5. Toggle it ON
6. Check **"Manage app"** permission
7. Click **"Save Changes"**

**Step 3: Add WhatsApp Account**
1. Still in system user page
2. Click **"Add Assets"** again
3. Select **"WhatsApp Accounts"** tab
4. Find your WhatsApp Business Account
5. Toggle it ON
6. Check **"Manage WhatsApp Business account"**
7. Click **"Save Changes"**

**Step 4: Generate Permanent Token**
1. Click **"Generate new token"** button
2. Select app: `MYY Tutor Platform`
3. Select permissions (check all):
   - ✅ `whatsapp_business_management`
   - ✅ `whatsapp_business_messaging`
   - ✅ `business_management`
4. Click **"Generate token"**
5. **IMPORTANT:** Copy token immediately and save securely!
6. Token format: `EAA...` (60-100 characters)

```env
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxxxxxxxxxxxxxxxxxx  ✅
```

**✅ This token NEVER EXPIRES!** (Perfect for production)

---

##### **Method 2: Graph API Explorer** (Alternative)

1. Go to: https://developers.facebook.com/tools/explorer/
2. Select your app from **"Meta App"** dropdown
3. Click **"Generate Access Token"** button
4. Select permissions:
   - ✅ `whatsapp_business_management`
   - ✅ `whatsapp_business_messaging`
5. Click **"Generate Access Token"**
6. Copy the token

**To make it long-lived (60 days):**
```bash
curl -X GET "https://graph.facebook.com/v18.0/oauth/access_token?grant_type=fb_exchange_token&client_id=YOUR_APP_ID&client_secret=YOUR_APP_SECRET&fb_exchange_token=YOUR_SHORT_TOKEN"
```

Replace:
- `YOUR_APP_ID`: From App Dashboard → Settings → Basic
- `YOUR_APP_SECRET`: From same page (click "Show")
- `YOUR_SHORT_TOKEN`: Token you just copied

**Response:**
```json
{
  "access_token": "EAA...",
  "token_type": "bearer",
  "expires_in": 5184000
}
```

Copy the new `access_token` value.

---

### **STEP 5: Set Admin Phone Number** ⏱️ 1 minute

This is YOUR phone number where you want to receive inquiry details.

#### **Format:** Country Code + Number (no + or spaces)

**Examples:**
```
India:    +91 9876543210  →  919876543210
USA:      +1 5551234567   →  15551234567
UK:       +44 7700900123  →  447700900123
```

**Your Number:**
```env
WHATSAPP_ADMIN_PHONE=919623947782  ✅ (Use YOUR number!)
```

---

### **STEP 6: Get Community/Group ID** ⏱️ 5 minutes

#### **Method 1: From WhatsApp Web** (Easiest)

**Step 1: Create WhatsApp Group**
1. Open WhatsApp on phone
2. Tap **"New Group"**
3. Add at least 1 contact
4. Name: `MYY Tutor Community`
5. Create group

**Step 2: Get Group ID**

**Option A: Using WhatsApp Web**
1. Open WhatsApp Web: https://web.whatsapp.com/
2. Scan QR code with phone
3. Click on your community/group
4. URL will look like:
   ```
   https://web.whatsapp.com/send?phone=120363XXXXXXXXXX
   ```
5. Copy the number part

**Option B: Using Invite Link**
1. Open group in WhatsApp
2. Tap group name (top)
3. Tap **"Invite via link"**
4. Tap **"Copy link"**
5. Link format:
   ```
   https://chat.whatsapp.com/ABCdefGHijKLmno123
   ```

**Then get actual ID using API:**
```bash
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/whatsapp_business_groups" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**Response shows:**
```json
{
  "data": [{
    "id": "120363XXXXXXXXXX@g.us",
    "name": "MYY Tutor Community"
  }]
}
```

**Format:** `[numbers]@g.us`

```env
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us  ✅
```

---

#### **Method 2: Using Meta API** (Advanced)

```bash
curl -X POST \
  "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/whatsapp_business_groups" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "MYY Tutor Community",
    "description": "Exclusive community for MYY Tutor teachers"
  }'
```

**Response:**
```json
{
  "id": "120363XXXXXXXXXX@g.us"
}
```

Copy the `id` value.

---

### **STEP 7: Create Verify Token** ⏱️ 1 minute

This is a **random string YOU create** for security.

**Requirements:**
- At least 20 characters
- Mix of letters, numbers, symbols
- Keep it secret (don't share)

**Generate Random Token:**

**Option A: Using Online Generator**
1. Go to: https://www.uuidgenerator.net/
2. Copy any generated UUID
3. Add your own text: `myytutor_2025_[UUID]`

**Option B: Using Terminal**
```bash
# Windows PowerShell
-join ((48..57) + (65..90) + (97..122) | Get-Random -Count 32 | % {[char]$_})

# Linux/Mac
openssl rand -base64 32

# Or just make up a strong password
```

**Example:**
```env
WHATSAPP_VERIFY_TOKEN=myytutor_2025_s3cur3T0k3n_XyZ123!  ✅
```

**✅ You can use ANY strong password here!**

---

## 📝 Complete .env.dev File

Now add all values to your `.env.dev` file:

```bash
# ========================================
# WHATSAPP BUSINESS CLOUD API
# ========================================

# From Step 3: API Setup → Phone number ID
WHATSAPP_PHONE_NUMBER_ID=123456789012345

# From Step 4: System User → Generate Token
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxxxxxxxxxxxxxxxxxx

# From Step 5: Your phone number (country code + number)
WHATSAPP_ADMIN_PHONE=919623947782

# From Step 6: WhatsApp Group ID
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us

# From Step 7: Random string you created
WHATSAPP_VERIFY_TOKEN=myytutor_2025_s3cur3T0k3n_XyZ123
```

---

## ✅ Verification Checklist

Before starting your app, verify:

- [ ] **Phone Number ID** - Should be 15 digits
- [ ] **Access Token** - Starts with `EAA...` (60-100 chars)
- [ ] **Admin Phone** - Format: `91XXXXXXXXXX` (no + or spaces)
- [ ] **Community ID** - Ends with `@g.us`
- [ ] **Verify Token** - At least 20 characters, strong password

---

## 🧪 Test Your Configuration

### **1. Test Access Token**
```bash
curl "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

**Should return:**
```json
{
  "verified_name": "MYY Tutor Platform",
  "code_verification_status": "VERIFIED",
  "display_phone_number": "+91 98765 43210",
  "quality_rating": "GREEN",
  "id": "123456789012345"
}
```

### **2. Test Sending Message**
```bash
curl -X POST \
  "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/messages" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "messaging_product": "whatsapp",
    "to": "919876543210",
    "type": "text",
    "text": {
      "body": "Test message from MYY Tutor! 🎓"
    }
  }'
```

**Should return:**
```json
{
  "messaging_product": "whatsapp",
  "contacts": [{
    "input": "919876543210",
    "wa_id": "919876543210"
  }],
  "messages": [{
    "id": "wamid.HBgLNTU..."
  }]
}
```

### **3. Check Your WhatsApp**
You should receive: "Test message from MYY Tutor! 🎓"

---

## 🔒 Security Best Practices

### **DO:**
- ✅ Store tokens in `.env.dev` (not in code)
- ✅ Add `.env.dev` to `.gitignore`
- ✅ Use System User tokens (never expire)
- ✅ Rotate tokens every 90 days
- ✅ Keep backup of all credentials

### **DON'T:**
- ❌ Commit tokens to Git
- ❌ Share tokens publicly
- ❌ Use temporary tokens in production
- ❌ Hardcode credentials in code
- ❌ Share verify token

---

## 📊 Quick Reference

| Credential | Where to Get | Format | Example |
|------------|-------------|--------|---------|
| **Phone Number ID** | Meta Dashboard → WhatsApp → API Setup | 15 digits | `123456789012345` |
| **Access Token** | Meta Business Suite → System Users | Starts with `EAA...` | `EAACEdEose0cBAxxx...` |
| **Admin Phone** | Your phone number | Country code + number | `919876543210` |
| **Community ID** | WhatsApp group invite link | Numbers + `@g.us` | `120363XXXXX@g.us` |
| **Verify Token** | Create your own | 20+ chars strong password | `myytutor_2025_XyZ!` |

---

## 🆘 Troubleshooting

### **Problem: Can't find Phone Number ID**
**Solution:** Go to WhatsApp → API Setup → Look for "Phone number ID" field (15 digits)

### **Problem: Access token expired**
**Solution:** Create permanent token using System User (Method 1 in Step 4)

### **Problem: Can't get Group ID**
**Solution:** 
1. Create group in WhatsApp first
2. Use API call to list all groups
3. Or use WhatsApp Web URL method

### **Problem: Test message not received**
**Solution:**
1. Verify phone number in Meta dashboard (API Setup → Add phone number)
2. Check number format: `919876543210` (no + or spaces)
3. Make sure WhatsApp is installed on that number

### **Problem: Webhook verification fails**
**Solution:** 
1. Make sure `WHATSAPP_VERIFY_TOKEN` in `.env.dev` matches exactly what you enter in Meta dashboard
2. Case-sensitive!

---

## 🎯 Next Steps After Getting Credentials

1. ✅ Add all 5 values to `.env.dev`
2. ✅ Restart your Spring Boot application
3. ✅ Test inquiry submission
4. ✅ Check WhatsApp messages arrive
5. ✅ Configure webhook in Meta dashboard
6. ✅ Test teacher registration
7. ✅ Go live! 🚀

---

## 📞 Need Help?

**Meta Documentation:**
- Getting Started: https://developers.facebook.com/docs/whatsapp/cloud-api/get-started
- System Users: https://www.facebook.com/business/help/503306463479099

**Support:**
- Meta Developer Community: https://developers.facebook.com/community
- WhatsApp Business API Support: https://business.facebook.com/help

---

**🎉 You now have everything you need to configure WhatsApp Business API!**

**Just follow each step above and copy the values into your `.env.dev` file.** 

**Total time: ~20 minutes** ⏱️
