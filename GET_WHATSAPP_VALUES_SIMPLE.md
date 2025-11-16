# 🎯 Get Your 5 WhatsApp Values - Simple Guide

## ✅ What You Need to Fill in .env.dev

```bash
WHATSAPP_PHONE_NUMBER_ID=_______________  # ← Need to fill
WHATSAPP_ACCESS_TOKEN=_________________  # ← Need to fill  
WHATSAPP_ADMIN_PHONE=919623947782        # ← Already set (your phone)
WHATSAPP_COMMUNITY_ID=_________________  # ← Need to fill
WHATSAPP_VERIFY_TOKEN=myytutor_2025...   # ← Already set (you created this)
```

---

## 📱 Step 1: Get PHONE NUMBER ID (2 minutes)

### **Where you are now: Graph API Explorer**
**Don't worry about this page for now - we'll come back to it!**

### **Go here instead:**
```
URL: https://developers.facebook.com/apps/
```

### **Steps:**
1. Click on your app: **myytutor**
2. Left sidebar: Click **WhatsApp**
3. Click **API Setup**
4. You'll see this section:

```
┌────────────────────────────────────┐
│ From: Test number                  │
│ Phone number ID: 123456789012345   │ ← COPY THIS!
│ WhatsApp Business Account ID: ...  │
└────────────────────────────────────┘
```

5. Copy the **Phone number ID** (15 digits)
6. Paste in `.env.dev`:
```bash
WHATSAPP_PHONE_NUMBER_ID=123456789012345
```

✅ **Step 1 DONE!**

---

## 🔑 Step 2: Get ACCESS TOKEN (5 minutes)

You have 2 options:

### **OPTION A: Quick Test Token (1 hour expiry) - For Testing Only**

**You're already here in Graph API Explorer!**

1. Click **"Generate Access Token"** button
2. Popup appears - add these permissions:
   - Type "whatsapp" in search
   - Check: ☑ `whatsapp_business_management`
   - Check: ☑ `whatsapp_business_messaging`
3. Click **"Generate Access Token"**
4. Login/authorize if asked
5. Token appears (starts with `EAA...`)
6. Click **"Copy Token"**
7. Paste in `.env.dev`:
```bash
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxx
```

⚠️ **This expires in 1 hour! Only use for quick testing.**

---

### **OPTION B: Permanent Token (NEVER expires) - Recommended** ⭐

1. **Go to:** https://business.facebook.com/
2. **Click:** Settings ⚙️ (bottom left)
3. **Navigate:** Users → System users
4. **Click:** "Add" button
5. **Fill in:**
   ```
   System user name: MYY Tutor API Bot
   System user role: Admin
   ```
6. **Click:** "Create system user"

7. **Add Assets:**
   - Click on the new user name
   - Click "Add Assets"
   - Tab: **Apps**
   - Find: `myytutor`
   - Toggle: **ON**
   - Permission: ☑ Manage app
   - Click "Save Changes"

8. **Add WhatsApp Asset:**
   - Click "Add Assets" again
   - Tab: **WhatsApp Accounts**
   - Find your WhatsApp Business Account
   - Toggle: **ON**
   - Permission: ☑ Manage WhatsApp Business account
   - Click "Save Changes"

9. **Generate Token:**
   - Click **"Generate new token"**
   - Select app: `myytutor`
   - Select permissions:
     - ☑ `whatsapp_business_management`
     - ☑ `whatsapp_business_messaging`
     - ☑ `business_management`
   - Click "Generate token"
   - **COPY IMMEDIATELY!** (You can't see it again)

10. Paste in `.env.dev`:
```bash
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxx
```

✅ **Step 2 DONE!** (This token NEVER expires!)

---

## 📞 Step 3: ADMIN PHONE (Already Done!) ✅

```bash
WHATSAPP_ADMIN_PHONE=919623947782  # Already set!
```

This is your phone number where you receive inquiry details.

**Format:** Country code + number (no + or spaces)
- Example: +91 9623947782 → `919623947782`

✅ **Step 3 DONE!**

---

## 👥 Step 4: Get COMMUNITY/GROUP ID (3 minutes)

### **Method 1: Create Group & Use API**

**Step 4.1: Create WhatsApp Group**
1. Open WhatsApp on your phone
2. Tap "New Group"
3. Add at least 1 contact (you can remove later)
4. Name: `MYY Tutor Community`
5. Create group

**Step 4.2: Get Group ID Using API**

Open PowerShell and run this command:

```powershell
# Replace these with YOUR values from Steps 1 & 2:
$PHONE_ID = "YOUR_PHONE_NUMBER_ID"
$TOKEN = "YOUR_ACCESS_TOKEN"

# Run this:
$url = "https://graph.facebook.com/v18.0/$PHONE_ID/whatsapp_business_groups"
$headers = @{"Authorization" = "Bearer $TOKEN"}
Invoke-RestMethod -Uri $url -Headers $headers
```

**You'll see:**
```json
{
  "data": [{
    "id": "120363XXXXXXXXXX@g.us",
    "name": "MYY Tutor Community"
  }]
}
```

Copy the `id` value and paste in `.env.dev`:
```bash
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us
```

✅ **Step 4 DONE!**

---

## 🔐 Step 5: VERIFY TOKEN (Already Done!) ✅

```bash
WHATSAPP_VERIFY_TOKEN=myytutor_2025_SecureVerifyToken_XyZ123  # Already set!
```

This is a random string YOU create for security. Already set in your `.env.dev`!

You'll use this same value later when configuring the webhook in Meta dashboard.

✅ **Step 5 DONE!**

---

## 🎉 Final Checklist

### **Your .env.dev should now have:**

```bash
WHATSAPP_PHONE_NUMBER_ID=123456789012345              # ← Step 1
WHATSAPP_ACCESS_TOKEN=EAACEdEose0cBAxxxxxxxxxxx       # ← Step 2
WHATSAPP_ADMIN_PHONE=919623947782                     # ← Already set
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us           # ← Step 4
WHATSAPP_VERIFY_TOKEN=myytutor_2025_SecureVerifyToken # ← Already set
```

### **Check each one:**
- [ ] Phone Number ID - 15 digits
- [ ] Access Token - Starts with `EAA...` (60-100 chars)
- [ ] Admin Phone - Format: `919623947782`
- [ ] Community ID - Ends with `@g.us`
- [ ] Verify Token - 20+ characters

---

## 🧪 Test Your Setup

After filling all values, test it works:

```powershell
# Replace with your actual values:
$PHONE_ID = "YOUR_PHONE_NUMBER_ID"
$TOKEN = "YOUR_ACCESS_TOKEN"

# Test 1: Verify token works
$url = "https://graph.facebook.com/v18.0/$PHONE_ID"
$headers = @{"Authorization" = "Bearer $TOKEN"}
Invoke-RestMethod -Uri $url -Headers $headers
```

**Should return your phone details!** ✅

```powershell
# Test 2: Send test message to yourself
$url = "https://graph.facebook.com/v18.0/$PHONE_ID/messages"
$headers = @{
    "Authorization" = "Bearer $TOKEN"
    "Content-Type" = "application/json"
}
$body = @{
    messaging_product = "whatsapp"
    to = "919623947782"  # Your number
    type = "text"
    text = @{
        body = "Test from MYY Tutor! 🎓"
    }
} | ConvertTo-Json

Invoke-RestMethod -Uri $url -Method Post -Headers $headers -Body $body
```

**Check your WhatsApp - you should receive the message!** 📱

---

## 🚀 What's Next?

After all values are in `.env.dev`:

1. **Restart your application:**
   ```powershell
   .\start-dev.bat
   ```

2. **Test inquiry submission** - WhatsApp messages should be sent automatically!

3. **Configure webhook** (for receiving teacher button clicks):
   - Go to: https://developers.facebook.com/apps/
   - Your app → WhatsApp → Configuration
   - Webhook URL: `https://your-domain.com/api/v1/webhooks/whatsapp`
   - Verify Token: `myytutor_2025_SecureVerifyToken_XyZ123` (same as in .env.dev)

---

## 🆘 Need Help?

**Can't find something?**
- Phone Number ID: WhatsApp → API Setup
- Access Token: Use System User method (permanent)
- Community ID: Create group first, then use API

**Detailed guides:**
- `HOW_TO_GET_WHATSAPP_CREDENTIALS.md` - Complete instructions
- `WHATSAPP_CREDENTIALS_VISUAL_GUIDE.md` - Visual diagrams

---

**🎯 Quick Summary:**

| Value | Where to Get | Time |
|-------|-------------|------|
| Phone Number ID | WhatsApp → API Setup | 2 min |
| Access Token | System User → Generate Token | 5 min |
| Admin Phone | Your number (already set) | ✅ Done |
| Community ID | API call after creating group | 3 min |
| Verify Token | Create random string (already set) | ✅ Done |

**Total time: ~10 minutes** ⏱️
