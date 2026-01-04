-- ===========================================================================
-- V5__add_technology_subjects.sql
-- MyyTutor - Additional Technology & AI Subjects
-- Created: 2026-01-04
-- 
-- Adds new subjects for emerging technologies:
--   - Artificial Intelligence & Machine Learning
--   - Python Development & Frameworks
--   - Data Science & Analytics
--   - Blockchain & Web3
--   - No-Code/Low-Code Platforms
--   - Cloud & Modern DevOps
-- ===========================================================================

-- ===========================================
-- ARTIFICIAL INTELLIGENCE & MACHINE LEARNING (New additions)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- Generative AI & LLMs
('Generative AI Fundamentals'),
('ChatGPT & Prompt Engineering'),
('OpenAI API Development'),
('LangChain Development'),
('LLM Fine-tuning'),
('Hugging Face Transformers'),
('Stable Diffusion & Image Generation'),
('DALL-E API Integration'),
('Midjourney Prompt Design'),
('AI for Content Creation'),
('Conversational AI & Chatbots'),
('RAG (Retrieval Augmented Generation)'),
('Vector Databases (Pinecone, Weaviate)'),
('AI Agents Development'),
('AutoGPT & Autonomous Agents'),

-- Advanced ML
('Reinforcement Learning'),
('Neural Network Architecture'),
('Convolutional Neural Networks (CNN)'),
('Recurrent Neural Networks (RNN)'),
('Transformer Architecture'),
('GANs (Generative Adversarial Networks)'),
('Time Series Forecasting with ML'),
('Recommendation Systems'),
('Anomaly Detection'),
('MLOps & Model Deployment');

-- ===========================================
-- PYTHON DEVELOPMENT & FRAMEWORKS
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- Python Full Stack
('Python Full Stack Development'),
('Python for Automation'),
('Python Scripting'),
('Python OOP & Design Patterns'),
('Python Testing (pytest, unittest)'),

-- Django
('Django Web Development'),
('Django REST Framework'),
('Django Admin Customization'),
('Django Security & Authentication'),
('Django Deployment (Gunicorn, Nginx)'),
('Django with PostgreSQL'),
('Django Channels (WebSockets)'),

-- Flask
('Flask Web Development'),
('Flask REST API'),
('Flask Blueprints & Modular Apps'),
('Flask with SQLAlchemy'),

-- FastAPI
('FastAPI Development'),
('FastAPI Async Programming'),
('FastAPI with Pydantic'),
('FastAPI Authentication (OAuth2, JWT)'),
('FastAPI Microservices'),

-- Other Python
('Python Web Scraping (BeautifulSoup, Scrapy)'),
('Selenium Automation with Python'),
('Python CLI Applications'),
('Python Package Development'),
('Python Asyncio & Concurrency'),
('Celery Task Queues'),
('Python for Finance'),
('Algorithmic Trading with Python');

-- ===========================================
-- DATA SCIENCE & ANALYTICS (Expanded)
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
-- Core Libraries
('Pandas for Data Analysis'),
('NumPy for Numerical Computing'),
('Matplotlib & Seaborn Visualization'),
('Plotly Interactive Dashboards'),
('SciPy for Scientific Computing'),

-- Machine Learning Libraries
('Scikit-learn Machine Learning'),
('XGBoost & LightGBM'),
('CatBoost Development'),
('Model Selection & Hyperparameter Tuning'),
('Feature Engineering'),
('Feature Selection Techniques'),

-- Deep Learning Frameworks
('TensorFlow 2.0 Development'),
('Keras Deep Learning'),
('PyTorch Deep Learning'),
('PyTorch Lightning'),
('JAX Machine Learning'),

-- Data Engineering
('Apache Airflow'),
('dbt (Data Build Tool)'),
('Data Warehousing Concepts'),
('ETL Pipeline Development'),
('Data Lake Architecture'),
('Snowflake Data Platform'),
('Databricks & Delta Lake'),

-- BI & Visualization
('Looker Development'),
('Metabase Dashboards'),
('Apache Superset'),
('Google Data Studio'),
('Data Storytelling');

-- ===========================================
-- BLOCKCHAIN & WEB3
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Blockchain Fundamentals'),
('Ethereum Development'),
('Solidity Smart Contracts'),
('Web3.js Development'),
('Ethers.js Development'),
('Hardhat Development'),
('Truffle Framework'),
('NFT Development'),
('ERC-20 Token Development'),
('ERC-721 Token Development'),
('DeFi Development'),
('Smart Contract Security'),
('Solana Development'),
('Rust for Blockchain'),
('Polygon Development'),
('IPFS Decentralized Storage'),
('The Graph Protocol'),
('Chainlink Oracles'),
('DAO Development'),
('MetaMask Integration');

-- ===========================================
-- NO-CODE / LOW-CODE PLATFORMS
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Bubble.io Development'),
('Webflow Design & Development'),
('Framer Development'),
('Wix Advanced Development'),
('WordPress Development'),
('WordPress Theme Development'),
('WordPress Plugin Development'),
('WooCommerce Development'),
('Shopify Theme Development'),
('Shopify App Development'),
('Airtable Development'),
('Notion for Productivity'),
('Zapier Automation'),
('Make (Integromat) Automation'),
('n8n Workflow Automation'),
('Retool Development'),
('Appsmith Development'),
('Power Apps Development'),
('Power Automate'),
('OutSystems Development'),
('Mendix Development');

-- ===========================================
-- MODERN CLOUD & INFRASTRUCTURE
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Serverless Architecture'),
('AWS Lambda Development'),
('AWS API Gateway'),
('AWS DynamoDB'),
('AWS S3 & CloudFront'),
('AWS Cognito Authentication'),
('Azure Functions'),
('Azure Cosmos DB'),
('Google Cloud Functions'),
('Firebase Development'),
('Firebase Realtime Database'),
('Firebase Firestore'),
('Firebase Authentication'),
('Supabase Development'),
('PlanetScale Database'),
('Vercel Deployment'),
('Netlify Deployment'),
('Railway Deployment'),
('DigitalOcean App Platform'),
('Infrastructure as Code (IaC)'),
('Pulumi Infrastructure'),
('CloudFormation');

-- ===========================================
-- MODERN FRONTEND & FULL STACK
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Next.js 14 Development'),
('Next.js App Router'),
('Next.js Server Components'),
('Remix Development'),
('Astro Development'),
('SvelteKit Development'),
('Nuxt.js Development'),
('TailwindCSS'),
('Headless UI'),
('Radix UI'),
('shadcn/ui Components'),
('Framer Motion Animation'),
('GSAP Animation'),
('Three.js 3D Graphics'),
('WebGL Development'),
('Progressive Web Apps (PWA)'),
('Electron Desktop Apps'),
('Tauri Desktop Apps');

-- ===========================================
-- MOBILE & CROSS-PLATFORM
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Expo Development'),
('React Native Expo'),
('SwiftUI Development'),
('Jetpack Compose (Android)'),
('Capacitor Development'),
('Ionic Development'),
('.NET MAUI Development');

-- ===========================================
-- TESTING & QUALITY
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES
('Jest Testing'),
('React Testing Library'),
('Cypress E2E Testing'),
('Playwright Testing'),
('Selenium WebDriver'),
('Postman API Testing'),
('JMeter Performance Testing'),
('k6 Load Testing'),
('API Load Testing'),
('Test Automation Strategy');

-- ===========================================
-- SOFT SKILLS FOR TECH
-- ===========================================
INSERT IGNORE INTO extra_subject (extra_subject_name) VALUES

('API Documentation'),
('System Design Interviews'),
('Coding Interview Preparation'),
('FAANG Interview Preparation'),
('Tech Resume Building'),
('LinkedIn Profile Optimization'),
('Open Source Contribution'),
('GitHub Portfolio Building'),
('Tech Blogging');

-- ===========================================================================
-- END OF V5 MIGRATION
-- ===========================================================================
