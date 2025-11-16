import re

# Read the file
with open('src/main/java/com/myytutor/service/WhatsAppService.java', 'r', encoding='utf-8') as f:
    content = f.read()

# Define the pattern to find the old code in addTeacherToCommunity method
pattern = r'(String teacherPhone = formatPhoneNumber\(phone\);)[\s\S]*?(sendMessage\(request\);)'

# Define the replacement with template code
replacement = r'''\1
            
            log.debug("Formatted phone: {}", teacherPhone);
            log.debug("Using hello_world template (approved template)");

            // Use approved template - hello_world for testing
            // TODO: Create custom teacher_welcome template in Meta Business Suite
            WhatsAppMessageRequest request = WhatsAppMessageRequest.builder()
                    .messagingProduct("whatsapp")
                    .to(teacherPhone)
                    .type("template")
                    .template(WhatsAppMessageRequest.TemplateMessage.builder()
                            .name("hello_world")
                            .language(WhatsAppMessageRequest.TemplateMessage.Language.builder()
                                    .code("en_US")
                                    .build())
                            .build())
                    .build();

            \2'''

# Perform the replacement (only first occurrence in addTeacherToCommunity)
new_content = re.sub(pattern, replacement, content, count=1)

# Write back
with open('src/main/java/com/myytutor/service/WhatsAppService.java', 'w', encoding='utf-8') as f:
    f.write(new_content)

print("✅ Successfully updated addTeacherToCommunity to use hello_world template")
