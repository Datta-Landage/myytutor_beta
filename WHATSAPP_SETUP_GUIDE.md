# 📱 WhatsApp Business Cloud API Integration Guide

## 🎯 Overview
This integration enables automatic WhatsApp notifications for:
1. ✅ Teacher registration → Auto-add to community
2. 📨 New inquiry → Customer confirmation
3. 📢 Inquiry broadcast → Teacher community (anonymous)
4. 👨‍🏫 Teacher interest → Interactive buttons
5. 📲 Admin notification → Full inquiry details

---

## 🚀 Setup Instructions

### **Step 1: Create Meta Business Account**

1. Go to [Meta for Developers](https://developers.facebook.com/)
2. Click **"Create App"**
3. Select **"Business"** as app type
4. Fill in app details:
   - App Name: `MYY Tutor Platform`
   - Contact Email: Your business email

### **Step 2: Add WhatsApp Product**

1. In your app dashboard, click **"Add Product"**
2. Find **"WhatsApp"** and click **"Set Up"**
3. Go to **"API Setup"** section

### **Step 3: Get Your Credentials**

#### **A. Phone Number ID**
```
Navigate to: WhatsApp > API Setup
Copy the "Phone number ID" (e.g., 123456789012345)
```

#### **B. Access Token**
```
1. Click "Generate Token" button
2. Copy the temporary token (valid 23 hours)
3. For production: Create permanent token (see below)
```

#### **C. Verify Token**
```
Create your own random string (e.g., "mySecureToken_2025")
This is used to verify webhook requests
```

---

## 🔐 Generate Permanent Access Token

### **Option 1: System User Token (Recommended)**

1. Go to **Meta Business Suite** → Settings → Users → System Users
2. Click **"Add System User"**
3. Name: `MYY Tutor API`
4. Role: Admin
5. Click **"Add Assets"**
6. Select your WhatsApp app
7. Grant **"Manage WhatsApp Business account"** permission
8. Click **"Generate New Token"**
9. Select permissions:
   - ✅ `whatsapp_business_management`
   - ✅ `whatsapp_business_messaging`
10. Copy token (starts with `EAA...`) - **This never expires!**

### **Option 2: Using Graph API Explorer**

1. Go to [Graph API Explorer](https://developers.facebook.com/tools/explorer/)
2. Select your app from dropdown
3. Click **"Get Token"** → **"Get User Access Token"**
4. Select permissions: `whatsapp_business_management`, `whatsapp_business_messaging`
5. Click **"Generate Access Token"**
6. Exchange for long-lived token using:

```bash
curl -X GET "https://graph.facebook.com/v18.0/oauth/access_token?grant_type=fb_exchange_token&client_id=YOUR_APP_ID&client_secret=YOUR_APP_SECRET&fb_exchange_token=YOUR_SHORT_LIVED_TOKEN"
```

---

## 🌐 Setup Webhook

### **1. Configure in Meta Dashboard**

```
Navigate to: WhatsApp > Configuration
Webhook URL: https://your-domain.com/api/v1/webhooks/whatsapp
Verify Token: mySecureToken_2025 (same as in .env)
```

### **2. Subscribe to Fields**

Check these boxes:
- ✅ `messages`
- ✅ `message_status`

### **3. Test Webhook**

Click **"Verify and Save"** - it should show green checkmark

---

## 📝 Environment Variables

Add these to your `.env.dev` file:

```properties
# WhatsApp Business Cloud API
WHATSAPP_PHONE_NUMBER_ID=123456789012345
WHATSAPP_ACCESS_TOKEN=EAA...YourPermanentToken
WHATSAPP_ADMIN_PHONE=919623947782
WHATSAPP_COMMUNITY_ID=120363XXXXXXXXXX@g.us
WHATSAPP_VERIFY_TOKEN=mySecureToken_2025
```

### **How to Get Community ID:**

**Method 1: Using WhatsApp Web**
1. Open WhatsApp Web
2. Go to your community/group
3. URL will show: `https://web.whatsapp.com/accept?code=...`
4. Or check group info → More options → Group info → Copy invite link
5. Community ID format: `120363XXXXXXXXXX@g.us`

**Method 2: Using API**
```bash
curl -X GET "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/whatsapp_business_groups" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

---

## 🧪 Testing

### **1. Test Customer Confirmation**

```bash
curl -X POST http://localhost:8080/api/inquiry \
  -H "Content-Type: application/json" \
  -H "X-Frontend-Key: testSecret123" \
  -d '{
    "name": "Test Student",
    "phone": "919876543210",
    "classStandard": "10",
    "board": "CBSE",
    "address": "Test Address, Mumbai",
    "message": "Looking for math tutor",
    "selectedSubjectIds": [1],
    "selectedStartDate": "2025-01-15",
    "selectedEndDate": "2025-12-15",
    "selectedStartTime": "16:00",
    "selectedEndTime": "18:00",
    "privacyAccepted": true,
    "privacyVersion": "1.0"
  }'
```

**Expected WhatsApp Messages:**
1. Customer receives: ✅ Confirmation message
2. Community receives: 📢 Anonymous inquiry with button
3. Admin receives: 📨 Full inquiry details

### **2. Test Teacher Registration**

Register a new teacher through your API - they should receive:
- Welcome message
- Community invitation

### **3. Test Interactive Button**

When a teacher clicks **"I'm Interested"** button in community:
- Admin receives notification with teacher phone
- Teacher receives confirmation message

---

## 📋 Message Templates

### **Customer Confirmation**
```
✅ Thank you for your inquiry! (ID: #123)

We have received your tutoring request. Our team will connect with you soon.

📞 You can expect a call within 24 hours.

Best regards,
MYY Tutor Team
```

### **Community Broadcast (Anonymous)**
```
🔔 NEW INQUIRY ALERT

📋 Inquiry ID: #123
📚 Subject: Mathematics
🏫 Class: 10
📍 Address: Andheri, Mumbai
⏰ Start Date: 2025-01-15

💡 Interested?
[Button: I'm Interested ✋]
```

### **Admin Full Details**
```
📨 NEW INQUIRY - FULL DETAILS

🆔 Inquiry ID: #123
━━━━━━━━━━━━━━━━━━

👤 Student Information:
• Name: Rahul Sharma
• Phone: 919876543210
• Standard: 10
• Board: CBSE

📚 Requested Subjects:
  • Mathematics
  • Physics

📍 Location:
• Address: 123 Main Street, Andheri, Mumbai

⏰ Schedule:
• Start Date: 2025-01-15
• End Date: 2025-12-15
• Start Time: 16:00
• End Time: 18:00

📝 Additional Message:
Looking for experienced math tutor

━━━━━━━━━━━━━━━━━━
⏱️ Received: 2025-11-13 10:30:45
```

### **Teacher Welcome**
```
🎉 Welcome to MYY Tutor Community!

Hi John Doe,

Your registration is complete! 🎓

You are now part of our exclusive teacher community where:
✅ You'll receive new tutoring opportunities
✅ Get instant inquiry notifications
✅ Connect with our admin team

📲 Stay active to grab the best opportunities!

Welcome aboard! 🚀
```

---

## 🔒 Security Best Practices

1. **Never commit tokens** - Use environment variables
2. **Verify webhook signatures** - Validate `X-Hub-Signature-256` header
3. **Use HTTPS** - WhatsApp requires secure URLs
4. **Rotate tokens** - Change tokens periodically
5. **Monitor usage** - Check Meta Business Suite for analytics

---

## 💰 Pricing (Free Tier)

WhatsApp Business Cloud API offers:
- ✅ **First 1,000 conversations/month: FREE**
- 📊 Business-initiated: ₹0.35 - ₹0.65 per conversation
- 👤 User-initiated: FREE for 24 hours

**What counts as conversation:**
- 24-hour window from first message
- Multiple messages = 1 conversation

**Your estimated usage:**
- 100 inquiries/month = 300 conversations (customer + community + admin)
- 50 teacher registrations/month = 50 conversations
- **Total: ~350 conversations/month** (within free tier!)

---

## 📊 Monitoring & Logs

### **Check Logs**
```bash
# View WhatsApp service logs
tail -f logs/spring-boot-app.log | grep WhatsApp

# Check successful messages
tail -f logs/spring-boot-app.log | grep "WhatsApp message sent successfully"

# Check errors
tail -f logs/spring-boot-app.log | grep "Failed to send WhatsApp"
```

### **Meta Business Suite**
- Go to **Analytics** → **Messages**
- View delivery rates, read rates, response times

---

## 🐛 Troubleshooting

### **Problem: Webhook not receiving messages**
```bash
# Test webhook endpoint locally
curl -X GET "http://localhost:8080/api/v1/webhooks/whatsapp?hub.mode=subscribe&hub.verify_token=mySecureToken_2025&hub.challenge=test123"

# Should return: test123
```

### **Problem: Messages not sending**
```bash
# Test API directly
curl -X POST "https://graph.facebook.com/v18.0/YOUR_PHONE_NUMBER_ID/messages" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "messaging_product": "whatsapp",
    "to": "919876543210",
    "type": "text",
    "text": {
      "body": "Test message from MYY Tutor"
    }
  }'
```

### **Problem: Access token expired**
- Generate new permanent token from System User
- Update `WHATSAPP_ACCESS_TOKEN` in `.env.dev`
- Restart application

### **Problem: Phone number not registered**
- Go to Meta Business Suite → WhatsApp → Phone Numbers
- Add test numbers in sandbox mode
- For production: Verify business phone number

---

## 🎓 Advanced Features (Future)

1. **Message Templates** - Pre-approved templates for marketing
2. **Media Messages** - Send images, PDFs (teacher documents)
3. **Location Sharing** - Share inquiry location with teachers
4. **Quick Replies** - Predefined teacher responses
5. **Analytics Dashboard** - Track conversion rates
6. **Chatbot Integration** - Auto-respond to common queries

---

## 📚 Resources

- [WhatsApp Business Cloud API Docs](https://developers.facebook.com/docs/whatsapp/cloud-api)
- [Getting Started Guide](https://developers.facebook.com/docs/whatsapp/cloud-api/get-started)
- [Message Templates](https://developers.facebook.com/docs/whatsapp/message-templates)
- [Webhooks Reference](https://developers.facebook.com/docs/whatsapp/webhooks)
- [Pricing Calculator](https://developers.facebook.com/docs/whatsapp/pricing)

---

## ✅ Final Checklist

Before going to production:

- [ ] Meta Business Account verified
- [ ] WhatsApp app created
- [ ] Permanent access token generated
- [ ] Webhook configured and verified
- [ ] Phone number verified (production)
- [ ] Community ID obtained
- [ ] Environment variables set
- [ ] Test messages sent successfully
- [ ] Error handling tested
- [ ] Logs monitored
- [ ] Business profile updated in WhatsApp
- [ ] Privacy policy compliant

---

## 🆘 Support

If you need help:
1. Check [Meta Developer Community](https://developers.facebook.com/community)
2. View [Status Page](https://developers.facebook.com/status/)
3. Contact Meta Business Support

---

**🎉 You're all set! Your WhatsApp integration is production-ready!**
