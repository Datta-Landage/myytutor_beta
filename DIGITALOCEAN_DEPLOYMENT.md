# Digital Ocean App Platform Deployment Guide

## 🚀 Quick Deploy to Digital Ocean

### Prerequisites
1. DigitalOcean account
2. GitHub repository connected: `Datta-Landage/myytutor_beta`
3. DigitalOcean Managed MySQL Database (recommended)

---

## 📋 Step-by-Step Deployment

### Option 1: Deploy Using App Spec File (Recommended)

1. **Go to DigitalOcean App Platform**
   - Visit: https://cloud.digitalocean.com/apps

2. **Create New App**
   - Click "Create App"
   - Choose "GitHub" as source
   - Select repository: `Datta-Landage/myytutor_beta`
   - Select branch: `main`

3. **Import App Spec**
   - Click "Edit App Spec"
   - Copy contents from `.do/app.yaml`
   - Paste and save

4. **Configure Environment Variables**
   Required secrets (set these in App Platform dashboard):
   
   **Database:**
   - `DB_HOST` - Your DigitalOcean database hostname
   - `DB_USER` - Database username (default: doadmin)
   - `DB_PASSWORD` - Database password
   
   **JWT:**
   - `JWT_SECRET` - Random 32+ character string
   
   **Email (Gmail):**
   - `MAIL_USERNAME` - Your Gmail address
   - `MAIL_PASSWORD` - Gmail app password (not your regular password!)
   
   **Frontend:**
   - `FRONTEND_SECRET` - Random string for API key validation
   
   **Google API:**
   - `GOOGLE_API_KEY` - Google Places API key
   
   **Twilio WhatsApp:**
   - `TWILIO_ACCOUNT_SID` - From Twilio dashboard
   - `TWILIO_AUTH_TOKEN` - From Twilio dashboard
   
   **Meta WhatsApp (if using):**
   - `WHATSAPP_PHONE_NUMBER_ID` - From Meta Business Manager
   - `WHATSAPP_ACCESS_TOKEN` - From Meta Business Manager
   - `WHATSAPP_COMMUNITY_ID` - Your WhatsApp community ID
   - `WHATSAPP_VERIFY_TOKEN` - Random string for webhook verification

5. **Deploy**
   - Click "Create Resources"
   - Wait for build and deployment (5-10 minutes first time)

---

### Option 2: Manual Configuration

If not using app.yaml:

1. **Create App**
   - Source: GitHub repo `Datta-Landage/myytutor_beta`
   - Branch: `main`

2. **Configure Build**
   - Build Command: `mvn clean package -DskipTests`
   - Run Command: `java -jar target/myytutor-0.0.1-SNAPSHOT.jar`
   - HTTP Port: `8080`

3. **Configure Resources**
   - Instance Size: Basic (512MB RAM minimum)
   - Instance Count: 1

4. **Health Check**
   - HTTP Path: `/actuator/health`
   - Initial Delay: 60 seconds
   - Period: 10 seconds

5. **Set all environment variables** (same as Option 1 above)

---

## 🗄️ Database Setup

### Option A: DigitalOcean Managed Database (Recommended)

1. **Create MySQL Database**
   - Go to Databases → Create Database
   - Choose MySQL 8
   - Select same region as your app
   - Choose plan (Basic is fine for testing)

2. **Get Connection Details**
   - Host: `your-db.db.ondigitalocean.com`
   - Port: `25060`
   - User: `doadmin`
   - Password: (shown in dashboard)
   - Database: Create one called `myytutor`

3. **Configure SSL**
   Your app is already configured for SSL: `sslMode=REQUIRED`

4. **Set Environment Variables**
   ```
   DB_HOST=your-db-cluster.db.ondigitalocean.com
   DB_PORT=25060
   DB_NAME=myytutor
   DB_USER=doadmin
   DB_PASSWORD=your-password-here
   ```

### Option B: Use Your Existing External Database

If you already have a MySQL database:

```
DB_HOST=your-external-db.com
DB_PORT=3306
DB_NAME=myytutor
DB_USER=your-user
DB_PASSWORD=your-password
```

Make sure your database allows connections from DigitalOcean IPs or is publicly accessible with SSL.

---

## 🔍 Troubleshooting Deployment

### Deployment Stuck "Running"?

**Common Issues:**

1. **Missing Environment Variables**
   - Check App Settings → Environment Variables
   - Every variable in `.env.example` must be set
   - Build will fail if any required variable is missing

2. **Database Connection Failed**
   - Verify `DB_HOST`, `DB_PORT`, `DB_USER`, `DB_PASSWORD`
   - Check database is in same region
   - Verify database allows connections
   - Check SSL mode matches database configuration

3. **Build Timeout**
   - First build takes 5-10 minutes (Maven downloads dependencies)
   - Subsequent builds are faster (cached)
   - If timeout, increase instance size temporarily

4. **Health Check Failing**
   - App must respond to `/actuator/health` within 60 seconds
   - Check application logs for startup errors
   - Increase `initial_delay_seconds` if app takes longer to start

### Check Deployment Logs

1. **Build Logs**
   - App Platform → Your App → Build Logs
   - Look for Maven errors or failed downloads

2. **Runtime Logs**
   - App Platform → Your App → Runtime Logs
   - Check for Spring Boot errors
   - Look for database connection errors
   - Verify environment variables are loaded

3. **Common Error Messages**

   ```
   Error: Could not find or load main class
   → Maven build failed or JAR not created
   → Check build logs
   ```

   ```
   CommunicationsException: Communications link failure
   → Database connection failed
   → Verify DB_HOST, DB_PORT, credentials
   ```

   ```
   IllegalArgumentException: Could not resolve placeholder 'VARIABLE_NAME'
   → Missing environment variable
   → Add it in App Settings
   ```

---

## 🔧 Post-Deployment Configuration

### 1. Set Up Custom Domain (Optional)
- App Settings → Domains
- Add your domain
- Update DNS records as shown

### 2. Configure CORS
Update `application.properties` if needed:
```properties
app.cors.allowed-origins=https://your-frontend-domain.com
```

### 3. Set Up Alerts
- App Settings → Alerts
- Configure alerts for:
  - Deployment failures
  - High CPU usage
  - High memory usage

### 4. Database Migrations
If you have schema changes:
```bash
# SSH into app or use database client
mysql -h DB_HOST -u DB_USER -p DB_NAME < your-migration.sql
```

---

## 📊 Monitoring

### Health Check Endpoint
```
https://your-app.ondigitalocean.app/actuator/health
```

Should return:
```json
{
  "status": "UP"
}
```

### Application Metrics
```
https://your-app.ondigitalocean.app/actuator/info
```

---

## 💰 Cost Optimization

### Development/Testing
- **Basic Plan**: $5-12/month
- **Database**: $15/month (Dev tier)
- **Total**: ~$20-27/month

### Production
- **Professional Plan**: $12-24/month (auto-scaling)
- **Database**: $15-60/month (depending on size)
- **Total**: ~$27-84/month

### Tips to Reduce Costs
1. Use Basic plan for testing
2. Scale up only when needed
3. Use managed database backups (included)
4. Monitor resource usage regularly

---

## 🔒 Security Checklist

- [ ] All secrets stored as encrypted environment variables
- [ ] Database uses SSL (sslMode=REQUIRED)
- [ ] JWT_SECRET is strong (32+ characters)
- [ ] Email app password (not regular Gmail password)
- [ ] CORS configured for your frontend domain only
- [ ] Health check endpoint accessible
- [ ] Logs don't contain sensitive data

---

## 🆘 Need Help?

### Check These First
1. **Deployment Logs** - App Platform → Runtime Logs
2. **Build Logs** - App Platform → Build Logs  
3. **Environment Variables** - Verify all are set
4. **Database Connection** - Test with MySQL client

### Common Quick Fixes

**App won't start?**
```bash
# Check environment variables are set
# Verify database is accessible
# Check logs for stack trace
```

**502 Bad Gateway?**
```bash
# Health check may be failing
# App not listening on port 8080
# Check runtime logs
```

**Slow deployment?**
```bash
# First deploy: 5-10 minutes (normal)
# Subsequent: 2-5 minutes
# If longer, check build logs for errors
```

---

## 📝 Deployment Commands

If using doctl CLI:

```bash
# Install doctl
brew install doctl  # macOS
# or download from https://github.com/digitalocean/doctl

# Authenticate
doctl auth init

# Create app from spec
doctl apps create --spec .do/app.yaml

# Update app
doctl apps update YOUR_APP_ID --spec .do/app.yaml

# Get app logs
doctl apps logs YOUR_APP_ID

# List apps
doctl apps list
```

---

## ✅ Verify Deployment Success

1. **Health Check**: `https://your-app.ondigitalocean.app/actuator/health`
   - Should return: `{"status":"UP"}`

2. **API Test**: `https://your-app.ondigitalocean.app/api/subjects`
   - Should return JSON response

3. **Swagger UI**: `https://your-app.ondigitalocean.app/swagger-ui.html`
   - Should show API documentation

---

## 🔄 Continuous Deployment

Once set up, your app will automatically deploy when you push to the `main` branch!

```bash
git add .
git commit -m "Update feature"
git push origin main
# DigitalOcean automatically deploys!
```

---

**Need the environment variable values?** Check your local `.env.dev` file - copy those values to DigitalOcean App Settings.
