# WhatsApp Message Templates - MYY Tutor

## 📋 Templates to Create

You need to create 3 message templates in Meta Business Manager. Copy the commands below and run them in PowerShell.

---

## 1️⃣ Teacher Welcome Template

**Template Name:** `teacher_welcome`  
**Purpose:** Welcome new teachers and send community link  
**Variables:** `{{1}}` = Teacher Name, `{{2}}` = Community Link

### PowerShell Command:

```powershell
$headers = @{
    "Authorization" = "Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD"
    "Content-Type" = "application/json"
}

$body = @{
    name = "teacher_welcome"
    language = "en_US"
    category = "MARKETING"
    components = @(
        @{
            type = "BODY"
            text = "🎉 Welcome to MYY Tutor, {{1}}!`n`nYour registration is complete! 🎓`n`n📱 Join Our Teacher Community:`nClick the link to join our exclusive WhatsApp group:`n{{2}}`n`n✅ What You'll Get:`n• Real-time tutoring opportunities`n• Instant inquiry notifications`n• Direct admin support`n• Network with other teachers`n`n💡 Pro Tip: Enable notifications so you never miss an opportunity!`n`nWelcome aboard! 🚀"
        }
    )
} | ConvertTo-Json -Depth 10

Invoke-RestMethod -Uri "https://graph.facebook.com/v22.0/850495054588589/message_templates" -Method Post -Headers $headers -Body $body
```

---

## 2️⃣ Student Inquiry Confirmation Template

**Template Name:** `inquiry_confirmation`  
**Purpose:** Confirm inquiry received from student/parent  
**Variables:** `{{1}}` = Inquiry ID

### PowerShell Command:

```powershell
$headers = @{
    "Authorization" = "Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD"
    "Content-Type" = "application/json"
}

$body = @{
    name = "inquiry_confirmation"
    language = "en_US"
    category = "UTILITY"
    components = @(
        @{
            type = "BODY"
            text = "✅ Thank you for your inquiry! (ID: {{1}})`n`nWe have received your tutoring request. Our team will connect with you soon.`n`n📞 You can expect a call within 24 hours.`n`nBest regards,`nMYY Tutor Team"
        }
    )
} | ConvertTo-Json -Depth 10

Invoke-RestMethod -Uri "https://graph.facebook.com/v22.0/850495054588589/message_templates" -Method Post -Headers $headers -Body $body
```

---

## 3️⃣ Admin Inquiry Alert Template

**Template Name:** `inquiry_alert`  
**Purpose:** Notify admin about new inquiry with full details  
**Variables:** `{{1}}` = Inquiry ID, `{{2}}` = Student Name, `{{3}}` = Phone, `{{4}}` = Subjects, `{{5}}` = Address

### PowerShell Command:

```powershell
$headers = @{
    "Authorization" = "Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD"
    "Content-Type" = "application/json"
}

$body = @{
    name = "inquiry_alert"
    language = "en_US"
    category = "UTILITY"
    components = @(
        @{
            type = "BODY"
            text = "🔔 NEW INQUIRY ALERT`n`n📋 Inquiry ID: #{{1}}`n👤 Student: {{2}}`n📱 Phone: {{3}}`n📚 Subjects: {{4}}`n📍 Area: {{5}}`n`nPlease review and assign a teacher.`n`nMYY Tutor Admin"
        }
    )
} | ConvertTo-Json -Depth 10

Invoke-RestMethod -Uri "https://graph.facebook.com/v22.0/850495054588589/message_templates" -Method Post -Headers $headers -Body $body
```

---

## 📝 How to Create Templates:

### Option 1: Via PowerShell (Recommended - Fast)

1. Open PowerShell
2. Copy and run each command above (one at a time)
3. Wait for response showing template created
4. Templates will be in "PENDING" status
5. Meta will review and approve within 24-48 hours

### Option 2: Via Meta Business Manager (Manual)

1. Go to: https://business.facebook.com/wa/manage/message-templates/
2. Click "Create Template"
3. Fill in:
   - Template Name (from above)
   - Category (MARKETING or UTILITY)
   - Language: English (US)
   - Body text (copy from above)
4. Add variables `{{1}}`, `{{2}}`, etc. as shown
5. Submit for review

---

## ✅ After Templates Are Approved:

Once Meta approves your templates (24-48 hours), I'll update the Java code to use these custom templates instead of `hello_world`.

### Expected Timeline:
- **Submit**: Now (5 minutes)
- **Under Review**: 24-48 hours
- **Approved**: Ready to use!
- **Code Update**: 10 minutes (I'll do it)

---

## 💰 Pricing Reminder:

- **First 1,000 conversations/month**: FREE
- **After that**: ₹0.33 - ₹0.50 per conversation (India)
- **Your expected volume**: ~200-300/month = FREE

---

## 🚀 Next Steps:

1. **Run the 3 PowerShell commands above** (copy-paste each one)
2. **Check template status**: 
   ```powershell
   $headers = @{ "Authorization" = "Bearer EAASAlPk43b0BPZBywsYQivmb9lN5n0oZBlo1UDFk8Uu5eSbpTLqNQcgJejxBJqxEjEosL5BZBk50RG8GDZAGj9TyuyIfKC5kPL1HPzM4lVHTWgqZBGIle0g9MKSNqifyDFqkmOHPPSJeZCE9oSQIEYmjvozgrnv7BbOQAP2acIH324y7DBmiSD7LgTDZAOo83CarIUzPhSauCPnYPTOySoZC15ODVetQlpJ46oBZCOAVvIJItdnsZD" }
   Invoke-RestMethod -Uri "https://graph.facebook.com/v22.0/850495054588589/message_templates?fields=name,status,language" -Method Get -Headers $headers
   ```
3. **Wait for approval** (check email from Meta)
4. **Let me know when approved** - I'll update the code to use the new templates

---

## 📞 Need Help?

If templates are rejected, Meta will tell you why. Common reasons:
- ❌ Too promotional language
- ❌ Spelling/grammar errors
- ❌ Missing required info

Just let me know and I'll help fix them!
