# WhatsApp Message Templates Creation Guide

## 📋 Overview
To send WhatsApp messages to real numbers, you need to create and get approval for message templates in Meta Business Suite.

---

## 🎯 Templates Needed for MYY Tutor

### **1. Teacher Welcome Template**
**Name:** `teacher_welcome`  
**Category:** UTILITY  
**Language:** English (US)

**Template Body:**
```
Welcome to MYY Tutor, {{1}}!

Your teacher registration has been completed successfully.

Join our WhatsApp community to receive:
- Real-time tutoring opportunities
- Inquiry notifications
- Direct support

Community Link: {{2}}

Thank you for joining MYY Tutor.
```

**Parameters:**
- `{{1}}` = Teacher Name (e.g., "Aditya Markad")
- `{{2}}` = Community Link (e.g., "https://chat.whatsapp.com/...")

---

### **2. Inquiry Confirmation Template**
**Name:** `inquiry_confirmation`  
**Category:** UTILITY  
**Language:** English (US)

**Template Body:**
```
Thank you for your inquiry with MYY Tutor!

Your inquiry ID: {{1}}

We have received your tutoring request and our team will contact you within 24 hours.

For urgent assistance, please call us.

Best regards,
MYY Tutor Team
```

**Parameters:**
- `{{1}}` = Inquiry ID (e.g., "#12345")

---

### **3. Inquiry Alert Template (For Admin)**
**Name:** `inquiry_alert`  
**Category:** UTILITY  
**Language:** English (US)

**Template Body:**
```
New Inquiry Received

Inquiry ID: {{1}}
Subject: {{2}}
Class: {{3}}
Location: {{4}}

Please review and assign to a suitable teacher.

View full details in admin dashboard.
```

**Parameters:**
- `{{1}}` = Inquiry ID
- `{{2}}` = Subject Name
- `{{3}}` = Class Standard
- `{{4}}` = Location/Area

---

## 📝 How to Create Templates

### **Method 1: Meta Business Suite (Recommended)**

1. **Go to Meta Business Suite:**
   - Visit: https://business.facebook.com/
   - Log in with your Facebook account

2. **Navigate to WhatsApp Manager:**
   - Click on "WhatsApp Accounts" in left menu
   - Select your WhatsApp Business Account

3. **Create Message Template:**
   - Click "Message templates" tab
   - Click "Create template" button
   - Fill in the details:
     - **Template name:** (e.g., `teacher_welcome`)
     - **Category:** Select "UTILITY"
     - **Language:** English (US)
     - **Header:** (Optional) Leave empty
     - **Body:** Paste the template body from above
     - **Footer:** (Optional) "MYY Tutor - Your Learning Partner"
     - **Buttons:** (Optional) Add action buttons if needed

4. **Add Variables:**
   - Use `{{1}}`, `{{2}}`, etc. for dynamic content
   - Click "Add sample" to provide example values
   - Example: `{{1}}` = "John Doe", `{{2}}` = "https://example.com"

5. **Submit for Review:**
   - Click "Submit"
   - Approval typically takes 24-48 hours
   - You'll receive email notification when approved

---

### **Method 2: API (Alternative)**

If you prefer API creation, here are the curl commands:

#### **Template 1: teacher_welcome**
```bash
curl -X POST "https://graph.facebook.com/v22.0/850495054588589/message_templates" \
  -H "Authorization: Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "teacher_welcome",
    "language": "en_US",
    "category": "UTILITY",
    "components": [
      {
        "type": "BODY",
        "text": "Welcome to MYY Tutor, {{1}}!\n\nYour teacher registration has been completed successfully.\n\nJoin our WhatsApp community to receive:\n- Real-time tutoring opportunities\n- Inquiry notifications\n- Direct support\n\nCommunity Link: {{2}}\n\nThank you for joining MYY Tutor.",
        "example": {
          "body_text": [
            ["Aditya Markad", "https://chat.whatsapp.com/HiU7szPwe5o4N8xWsaIIBt"]
          ]
        }
      }
    ]
  }'
```

#### **Template 2: inquiry_confirmation**
```bash
curl -X POST "https://graph.facebook.com/v22.0/850495054588589/message_templates" \
  -H "Authorization: Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "inquiry_confirmation",
    "language": "en_US",
    "category": "UTILITY",
    "components": [
      {
        "type": "BODY",
        "text": "Thank you for your inquiry with MYY Tutor!\n\nYour inquiry ID: {{1}}\n\nWe have received your tutoring request and our team will contact you within 24 hours.\n\nFor urgent assistance, please call us.\n\nBest regards,\nMYY Tutor Team",
        "example": {
          "body_text": [
            ["#12345"]
          ]
        }
      }
    ]
  }'
```

#### **Template 3: inquiry_alert**
```bash
curl -X POST "https://graph.facebook.com/v22.0/850495054588589/message_templates" \
  -H "Authorization: Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "inquiry_alert",
    "language": "en_US",
    "category": "UTILITY",
    "components": [
      {
        "type": "BODY",
        "text": "New Inquiry Received\n\nInquiry ID: {{1}}\nSubject: {{2}}\nClass: {{3}}\nLocation: {{4}}\n\nPlease review and assign to a suitable teacher.\n\nView full details in admin dashboard.",
        "example": {
          "body_text": [
            ["#12345", "Mathematics", "10th Standard", "Pune, Maharashtra"]
          ]
        }
      }
    ]
  }'
```

---

## ✅ Template Approval Tips

### **Do's:**
- ✅ Keep text clear and professional
- ✅ Use UTILITY category for transactional messages
- ✅ Include clear opt-out language if needed
- ✅ Provide accurate sample values
- ✅ Avoid promotional language in UTILITY templates
- ✅ Use proper grammar and spelling

### **Don'ts:**
- ❌ Don't use excessive emojis (1-2 max)
- ❌ Don't include marketing language in UTILITY
- ❌ Don't use all caps text
- ❌ Don't include external links (except {{variables}})
- ❌ Don't violate WhatsApp's commerce policies
- ❌ Don't include time-sensitive offers

---

## 🔄 After Template Approval

Once your templates are approved (usually 24-48 hours):

1. **Check Template Status:**
```bash
curl "https://graph.facebook.com/v22.0/850495054588589/message_templates?fields=name,status,language" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN"
```

2. **Update Your Code:**
   - Replace `hello_world` with your approved template names
   - Add parameters for dynamic content
   - Test with real phone numbers

3. **Monitor Usage:**
   - Check message delivery status
   - Review failed messages in Meta Business Suite
   - Adjust templates based on user feedback

---

## 💰 Pricing After Templates

**Free Tier:** 1,000 conversations/month  
**Paid Tier (India):**
- Utility messages: ~₹0.33-0.50 per conversation
- Service messages: ~₹0.16-0.25 per conversation

**For 100-500 messages/month:** Expect ₹0-500/month cost

---

## 🎯 Next Steps

1. **Create Templates** in Meta Business Suite (easier than API)
2. **Wait for Approval** (24-48 hours)
3. **Update Application Code** to use new template names
4. **Test** with real phone numbers
5. **Monitor** delivery and user feedback

---

## 📞 Support

- **Meta Business Help:** https://business.facebook.com/help
- **WhatsApp Business API Docs:** https://developers.facebook.com/docs/whatsapp
- **Template Guidelines:** https://developers.facebook.com/docs/whatsapp/message-templates/guidelines

---

## ⚠️ Important Notes

- Templates can take up to 48 hours for approval
- Rejected templates can be resubmitted with modifications
- Each template can have max 4096 characters
- Max 10 variables per template
- Variables must be used in order: {{1}}, {{2}}, {{3}}, etc.
- Cannot skip variable numbers (can't have {{1}} and {{3}} without {{2}})

---

**Good luck! 🚀**
