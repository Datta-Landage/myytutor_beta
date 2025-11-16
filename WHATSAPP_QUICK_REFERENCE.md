# 📱 WhatsApp Business - Quick Reference Card

## 🎯 What You'll Receive as Admin

### **1. Full Inquiry Details** (Every new inquiry)
```
📨 NEW INQUIRY - FULL DETAILS

🆔 Inquiry ID: #123
━━━━━━━━━━━━━━━━━━

👤 Student Info:
• Name: Rahul Sharma
• Phone: 919876543210
• Standard: 10
• Board: CBSE

📚 Subjects:
  • Mathematics
  • Physics

📍 Location:
• Full Address

⏰ Schedule:
• Dates & Times

📝 Message:
• Additional details
```

### **2. Teacher Interest Alerts** (When teacher clicks button)
```
👨‍🏫 TEACHER INTEREST ALERT

Teacher expressed interest in:
📋 Inquiry ID: #123
📱 Teacher Phone: 919876543210

Review profile & assign if suitable.
```

---

## 👨‍🏫 What Teachers Receive

### **1. Welcome Message** (After registration)
```
🎉 Welcome to MYY Tutor Community!

Hi [Name],
Your registration is complete! 🎓

✅ New opportunities
✅ Instant notifications
✅ Admin connection

Welcome aboard! 🚀
```

### **2. Inquiry Broadcasts** (Anonymous)
```
🔔 NEW INQUIRY ALERT

📋 Inquiry ID: #123
📚 Subject: Mathematics
🏫 Class: 10
📍 Area: Andheri

[Button: I'm Interested ✋]
```

### **3. Confirmation** (After clicking button)
```
✅ Interest Recorded!

Thank you for interest in #123.
Admin will contact if selected.

Good luck! 🍀
```

---

## 🎓 What Customers Receive

### **Auto-Reply** (After submitting inquiry)
```
✅ Thank you! (ID: #123)

Request received.
We'll connect soon.

📞 Expect call within 24h.

Best regards,
MYY Tutor Team
```

---

## 🔢 Message Flow Summary

```
NEW INQUIRY
    ↓
    ├─→ Customer: "Thank you!"
    ├─→ Community: "New inquiry #123" [Button]
    └─→ Admin: Full details

TEACHER CLICKS BUTTON
    ↓
    ├─→ Admin: "Teacher 9876... interested"
    └─→ Teacher: "Interest recorded"
```

---

## 📊 Quick Stats

- **Free Messages:** 1,000/month
- **Response Time:** < 5 seconds
- **Delivery Rate:** 99%+
- **Privacy:** Community sees NO personal info

---

## 🚨 Troubleshooting

### **Not receiving messages?**
1. Check phone number format: `919876543210` (no +, no spaces)
2. Verify number added in Meta dashboard (sandbox mode)
3. Check WhatsApp is installed and active

### **Webhook not working?**
1. Verify webhook URL in Meta dashboard
2. Check verify token matches `.env.dev`
3. Test: `GET /api/v1/webhooks/whatsapp`

### **Messages delayed?**
- WhatsApp API processes within seconds
- Check Meta Business Suite status
- View logs: `tail -f logs/spring-boot-app.log | grep WhatsApp`

---

## 📞 Admin Actions

### **When you receive teacher interest:**
1. Note teacher phone: `919876543210`
2. Check teacher profile in admin dashboard
3. Review:
   - Experience
   - Subject expertise
   - Location match
   - Availability
4. Call teacher if suitable
5. Negotiate terms
6. Assign inquiry

### **Manual messages (if needed):**
```bash
# Send custom message
curl -X POST "https://graph.facebook.com/v18.0/YOUR_PHONE_ID/messages" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "messaging_product": "whatsapp",
    "to": "919876543210",
    "type": "text",
    "text": {
      "body": "Your custom message here"
    }
  }'
```

---

## 🎯 Best Practices

### **DO:**
- ✅ Respond to teacher interest within 24h
- ✅ Keep inquiry IDs for reference
- ✅ Call teachers before assigning
- ✅ Update inquiry status after assignment
- ✅ Monitor WhatsApp regularly

### **DON'T:**
- ❌ Share customer details in community
- ❌ Ignore teacher interest messages
- ❌ Send spam/marketing without approval
- ❌ Use personal number for business
- ❌ Forward messages outside platform

---

## 📈 Monitoring

### **Check message delivery:**
- Meta Business Suite → Analytics → Messages
- View: Delivered, Read, Replied

### **Check application logs:**
```bash
# Success messages
grep "WhatsApp message sent successfully" logs/spring-boot-app.log

# Errors
grep "Failed to send WhatsApp" logs/spring-boot-app.log
```

---

## 🔐 Security Notes

- **Never share:** Access token, verify token
- **Rotate tokens:** Every 90 days
- **Backup:** Save all configuration
- **Access control:** Limit who has admin number
- **Audit:** Review message logs monthly

---

## 📞 Emergency Contacts

**WhatsApp Issues:**
- Meta Business Support: [business.facebook.com/help](https://business.facebook.com/help)
- Developer Community: [developers.facebook.com/community](https://developers.facebook.com/community)

**Technical Issues:**
- Check logs: `logs/spring-boot-app.log`
- Restart service: `.\start-dev.bat`
- Developer: [Your contact info]

---

## 💡 Tips

1. **Save inquiry IDs** - Easy reference for follow-up
2. **Set WhatsApp notifications** - Don't miss teacher interest
3. **Quick response = better conversion** - Call within hours
4. **Track metrics** - Which teachers respond fastest
5. **Feedback loop** - Ask customers about teacher quality

---

**Print this card and keep it handy! 📋**
