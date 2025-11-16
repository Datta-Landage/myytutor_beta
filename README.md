# MyyTutor - Teacher Management Platform

A comprehensive Spring Boot application for managing teachers, students, inquiries, and educational services with integrated WhatsApp Business API notifications and email communication.

## 🚀 Features

- ✅ **Teacher Registration & Management** - Complete teacher onboarding with email verification
- ✅ **Student & Inquiry Management** - Track and manage student inquiries
- ✅ **Email Notifications** - Professional email templates using Thymeleaf
- ✅ **WhatsApp Integration** - Automated WhatsApp notifications via Meta Business API
- ✅ **Subject & Class Mapping** - Flexible subject and class associations
- ✅ **Availability Scheduling** - Weekly availability calendar for teachers
- ✅ **Document Management** - Upload and manage educational documents
- ✅ **Secure Authentication** - Spring Security with JWT and password encryption
- ✅ **RESTful API** - Well-structured REST endpoints
- ✅ **MySQL Database** - Robust relational database with JPA/Hibernate

## 🛠️ Tech Stack

- **Backend:** Spring Boot 3.3.8
- **Database:** MySQL 8+
- **ORM:** Hibernate/JPA
- **Email:** Thymeleaf templates + JavaMailSender
- **Security:** Spring Security + BCrypt
- **WhatsApp:** Meta Business Cloud API v22.0
- **Build Tool:** Maven 3+
- **Java Version:** 17+

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Gmail account (for SMTP) or other email service
- WhatsApp Business Account (for WhatsApp integration)

## ⚙️ Configuration

1. **Clone the repository:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/my-tutors.git
   cd my-tutors
   ```

2. **Create `.env.dev` file** (copy from `.env.example`):
   ```bash
   cp .env.example .env.dev
   ```

3. **Update `.env.dev` with your credentials:**
   - Database connection details
   - Email (Gmail SMTP or other)
   - WhatsApp Business API credentials
   - Frontend secret key

4. **Database Setup:**
   ```sql
   CREATE DATABASE betaApp_db;
   ```
   Tables will be auto-created by Hibernate on first run.

## 🏃 Running the Application

### Development Mode:

**Windows:**
```bash
.\start-dev.bat
```

**Linux/Mac:**
```bash
./mvnw spring-boot:run -Dspring.profiles.active=dev
```

### Production Build:
```bash
.\mvnw.cmd clean package
java -jar target/myytutor-0.0.1-SNAPSHOT.jar
```

Application will run on: `http://localhost:8080`

## 📧 Email Configuration

The application uses Gmail SMTP by default. To use Gmail:

1. Enable 2FA on your Google Account
2. Generate an App Password: https://myaccount.google.com/apppasswords
3. Update `.env.dev`:
   ```env
   MAIL_USERNAME=your-email@gmail.com
   MAIL_PASSWORD=your-app-password
   ```

## 📱 WhatsApp Integration

### Setup WhatsApp Business API:

1. Create Meta Business Account: https://business.facebook.com/
2. Set up WhatsApp Business API
3. Get credentials from Meta Business Suite
4. Update `.env.dev`:
   ```env
   WHATSAPP_PHONE_NUMBER_ID=your-phone-id
   WHATSAPP_ACCESS_TOKEN=your-access-token
   WHATSAPP_BUSINESS_ACCOUNT_ID=your-business-account-id
   ```

For detailed setup, see: [WHATSAPP_SETUP_GUIDE.md](WHATSAPP_SETUP_GUIDE.md)

## 🔐 Security

- **Passwords:** BCrypt encrypted
- **API Protection:** Frontend key validation
- **CORS:** Configured for specific origins
- **SQL Injection:** Protected via JPA/Hibernate
- **Sensitive Data:** Excluded via `.gitignore`

⚠️ **Never commit `.env.dev` or any file with real credentials!**

## 📁 Project Structure

```
my-tutors/
├── src/
│   ├── main/
│   │   ├── java/com/myytutor/
│   │   │   ├── api/          # REST Controllers
│   │   │   ├── config/       # Spring Configuration
│   │   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── entity/       # JPA Entities
│   │   │   ├── repository/   # Data Access Layer
│   │   │   ├── service/      # Business Logic
│   │   │   └── util/         # Utility Classes
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-test.properties
│   │       └── templates/email/  # Email Templates
│   └── test/                 # Unit Tests
├── .env.example              # Environment template
├── .gitignore
├── pom.xml
└── README.md
```

## 🧪 Testing

Run tests:
```bash
.\mvnw.cmd test
```

Skip tests during build:
```bash
.\mvnw.cmd clean package -DskipTests
```

## 📊 API Endpoints

### Teacher Management:
- `POST /api/v1/teachers/register-email` - Register teacher email
- `POST /api/v1/teachers/verify-otp` - Verify OTP
- `POST /api/v1/teachers/register` - Complete registration
- `GET /api/v1/teachers/{id}` - Get teacher details

### Inquiry Management:
- `POST /api/v1/inquiries` - Create inquiry
- `GET /api/v1/inquiries` - List inquiries
- `GET /api/v1/inquiries/{id}` - Get inquiry details

### Document Management:
- `POST /api/v1/documents/upload` - Upload document
- `GET /api/v1/documents/{id}` - Get document

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License.

## 👥 Authors

- **Your Name** - Initial work

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- Meta for WhatsApp Business API
- All contributors who helped with this project

## 📞 Support

For issues and questions:
- Email: support@myytutor.com
- GitHub Issues: https://github.com/YOUR_USERNAME/my-tutors/issues

## 🔄 Version History

- **1.0.0** (Nov 2025) - Initial release
  - Teacher registration with email verification
  - WhatsApp Business API integration
  - Email notification system
  - Complete inquiry management

---

**Made with ❤️ by the MyyTutor Team**
