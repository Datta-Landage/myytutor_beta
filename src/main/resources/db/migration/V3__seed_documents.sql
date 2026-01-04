-- ===========================================================================
-- V3__seed_documents.sql
-- MyyTutor - Legal Documents Seed Data
-- Version: v1.0 for all documents
-- Date: 2026-01-01
-- Source: src/main/resources/documents/*.txt
-- ===========================================================================

-- ===========================================
-- PRIVACY POLICY (v1.0)
-- ===========================================

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE documents;
SET FOREIGN_KEY_CHECKS = 1;

INSERT IGNORE INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('PRIVACY_POLICY', 'v1.0', '2026-01-01 00:00:00', 
'<h1>Privacy Policy</h1>
<p>Last updated: January 2026</p>

<p>
This Privacy Policy explains how MyyTutor.com ("we", "us", "our", or "the Platform")
collects, uses, stores, and discloses personal data when you use our website and services.
It also explains your rights under applicable Indian laws, including the Digital Personal
Data Protection Act, 2023.
</p>

<p>
By accessing or using the Service, submitting an inquiry, or registering as a tutor,
you agree to the collection and use of information in accordance with this Privacy Policy.
</p>

<h2>Interpretation and Definitions</h2>

<h3>Interpretation</h3>
<p>
Words with capitalized initial letters have meanings defined below.
These definitions apply regardless of whether they appear in singular or plural.
</p>

<h3>Definitions</h3>
<ul>
<li><p><strong>Company</strong> refers to MyyTutor.com, Pune, India.</p></li>
<li><p><strong>Service</strong> refers to the MyyTutor website.</p></li>
<li><p><strong>Personal Data</strong> means any information relating to an identified or identifiable individual.</p></li>
<li><p><strong>Data Principal</strong> means the individual whose personal data is processed (parent/guardian or tutor).</p></li>
<li><p><strong>You</strong> means the person accessing or using the Service.</p></li>
</ul>

<h2>Collecting and Using Your Personal Data</h2>

<h3>Types of Data Collected</h3>

<h4>Parent / Guardian Data</h4>
<p>
When a parent or guardian submits a tutor inquiry, we may collect:
</p>
<ul>
<li>Name</li>
<li>Phone number</li>
<li>City or locality</li>
<li>Student class/standard, board, and required subjects</li>
<li>Preferred days and time slots for tutoring</li>
<li>Optional message describing learning requirements</li>
</ul>

<h4>Tutor Data</h4>
<p>
When a tutor registers on the Platform, we may collect:
</p>
<ul>
<li>Name and profile description</li>
<li>Phone number and WhatsApp number</li>
<li>Educational qualifications and certifications</li>
<li>Teaching experience</li>
<li>Subjects and classes taught</li>
<li>Preferred teaching locations</li>
<li>Availability schedule and expected fees</li>
<li>Login credentials (stored only in encrypted form)</li>
</ul>

<h4>Usage Data</h4>
<p>
Usage Data is collected automatically and may include IP address, browser type,
pages visited, and time spent on the Service.
This data is used only for security, analytics, and performance improvement.
</p>

<h2>Purpose of Data Processing</h2>
<ul>
<li>Matching parents with suitable tutors</li>
<li>Sharing contact details after a successful match</li>
<li>Communication related to inquiries and platform operations</li>
<li>Improving platform functionality, reliability, and security</li>
<li>Legal compliance and fraud prevention</li>
</ul>

<h2>Matching Logic &amp; Automated Processing</h2>
<p>
Our system automatically suggests tutors based on subject match, availability,
location, and fee expectations. This automation is used only to improve relevance
and efficiency.
</p>

<h2>Explicit Consent for Matching &amp; Contact Sharing</h2>
<p>
By submitting an inquiry or requesting a tutor match, you explicitly consent to:
</p>
<ul>
<li>Sharing your phone number and inquiry details with matched tutors</li>
<li>Receiving the matched tutor''s contact details</li>
<li>Being contacted by tutors via call, SMS, or WhatsApp for tutoring discussions</li>
</ul>

<h2>SMS / WhatsApp / Email Communications</h2>
<p>
By using the Service, you consent to receive transactional and service-related
communications via SMS, WhatsApp, email, or phone calls.
These communications are essential to platform operation.
</p>

<h2>Data Sharing and Disclosure</h2>

<h3>Between Users</h3>
<p>
Once a match occurs, contact details are shared between parents and tutors.
Subsequent interactions take place outside the Platform and are the responsibility
of the users involved.
</p>

<h3>Legal Disclosure</h3>
<p>
We may disclose personal data if required by law, court order,
government authority, or to protect the rights and safety of users or the Platform.
</p>

<h2>Children''s Data</h2>
<p>
Children do not register or directly use the Platform.
Parents or guardians provide information on behalf of their children solely
to find suitable tutors.
</p>

<h2>Safety Disclaimer &amp; Verification Notice</h2>
<p>
MyyTutor does not conduct physical background checks, police verification,
or home safety inspections of tutors.
</p>
<p>
Parents are strongly advised to independently verify tutor identity,
qualifications, and references before allowing home visits.
</p>
<p>
MyyTutor is not responsible for any loss, harm, or incident arising from
offline interactions between users.
</p>

<h2>Data Security</h2>
<ul>
<li>Encrypted password storage</li>
<li>Secure HTTPS connections</li>
<li>Restricted internal access to personal data</li>
<li>Periodic security reviews</li>
</ul>

<h2>Data Retention</h2>
<ul>
<li>Active accounts: retained while active</li>
<li>Inactive accounts: archived after prolonged inactivity</li>
<li>Deletion requests: completed within 30–45 days unless legally required otherwise</li>
</ul>

<h2>Your Rights</h2>
<ul>
<li>Access your personal data</li>
<li>Correct inaccurate data</li>
<li>Request deletion of personal data</li>
<li>Withdraw consent by discontinuing platform use</li>
</ul>

<h2>Grievance Officer</h2>
<p>
In accordance with Indian law, the following contact is designated for grievances:
</p>
<ul>
<li><strong>Designation:</strong> Grievance Officer / Data Protection Contact</li>
<li><strong>Email:</strong> <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li><strong>Location:</strong> Pune, India</li>
</ul>

<h2>Cookies</h2>
<p>
We use only essential cookies required for core functionality.
We do not use behavioural advertising or third-party tracking cookies.
</p>

<h2>Limitation of Liability</h2>
<p>
MyyTutor acts solely as a facilitator and does not guarantee tutor performance
or outcomes. Offline arrangements are outside our control.
</p>

<h2>Changes to This Privacy Policy</h2>
<p>
We may update this Privacy Policy periodically.
Updates will be reflected on this page with a revised date.
</p>

<h2>Contact Us</h2>
<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- TERMS OF USE (v1.0)
-- ===========================================
INSERT IGNORE INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('TERMS_OF_USE', 'v1.0', '2026-01-01 00:00:00',
'<h1>Terms of Use</h1>

<p>Last updated: January 2026</p>

<p>
These Terms of Use ("Terms") govern your access to and use of MyyTutor.com
("Platform", "we", "us", or "our"). By accessing the Platform, submitting an inquiry,
registering as a tutor, or otherwise using our services, you agree to be bound by these Terms.
</p>

<p>
If you do not agree to these Terms, you must not use the Platform.
</p>

<h2>1. Nature of the Platform</h2>

<p>
MyyTutor.com is a technology-enabled facilitation platform that connects
parents or guardians seeking tutoring services with independent tutors.
</p>

<p>
MyyTutor does not employ tutors, does not act as an educational institution,
and does not supervise, control, or guarantee offline tutoring sessions.
</p>

<h2>2. Eligibility</h2>

<p>
By using the Platform, you confirm that:
</p>

<ul>
<li>You are at least 18 years of age</li>
<li>You have the legal capacity to enter into a binding agreement</li>
<li>If acting on behalf of a child, you are the parent or legal guardian</li>
</ul>

<h2>3. User Roles &amp; Responsibilities</h2>

<h3>Parents / Guardians</h3>

<ul>
<li>You are responsible for providing accurate inquiry information</li>
<li>You must independently verify tutor identity and suitability</li>
<li>You are responsible for student supervision and safety during offline sessions</li>
</ul>

<h3>Tutors</h3>

<ul>
<li>You confirm that all profile information and qualifications are accurate</li>
<li>You are responsible for professional conduct, punctuality, and service quality</li>
<li>You are solely responsible for compliance with tax, labor, and legal obligations</li>
<li>You are responsible for the authenticity of all submitted documents and certifications</li>
</ul>

<h2>4. Matching Logic &amp; Communication Consent</h2>

<p>
Tutor matching is performed using automated logic based on subject requirements,
availability, location, and other factors. Matches are indicative only and do not
constitute endorsement or verification by MyyTutor.
</p>

<p>
By submitting an inquiry or registering as a tutor, you expressly consent to:
</p>

<ul>
<li>Sharing of contact details between matched parents and tutors</li>
<li>Receiving communications via email, SMS, or WhatsApp related to Platform activity</li>
</ul>

<h2>5. Fees, Payments &amp; Refunds</h2>

<p>
MyyTutor does not primarily process payments on the Platform.
Tutoring fees and payment terms are agreed directly between parents and tutors.
</p>

<p>
Refunds, fee adjustments, or deductions are governed by the Refund Policy.
</p>

<h2>6. Safety Disclaimer</h2>

<p>
MyyTutor does not conduct police verification, background checks,
or physical inspections of tutors or parent premises.
</p>

<p>
All offline interactions occur at the sole risk of the users.
Parents are strongly advised to verify identity documents,
request references, and exercise reasonable caution.
</p>

<h2>7. Prohibited Activities</h2>

<ul>
<li>Submitting false, misleading, or incomplete information</li>
<li>Using the Platform for unlawful or fraudulent purposes</li>
<li>Harassing, abusing, or exploiting other users</li>
<li>Attempting to bypass platform safeguards or misuse shared data</li>
<li>Copying, scraping, or redistributing platform content without permission</li>
</ul>

<h2>8. Intellectual Property</h2>

<p>
All Platform content, including text, design, logos, and software,
is the intellectual property of MyyTutor.com.
Unauthorized use or reproduction is prohibited.
</p>

<h2>9. Limitation of Liability</h2>

<p>
To the maximum extent permitted by law, MyyTutor shall not be liable for:
</p>

<ul>
<li>Actions or omissions of parents or tutors</li>
<li>Quality of tutoring services or academic outcomes</li>
<li>Personal injury, loss, or damage arising from offline interactions</li>
<li>Indirect, incidental, or consequential damages</li>
</ul>

<h2>10. Indemnity</h2>

<p>
You agree to indemnify and hold harmless MyyTutor, its owners,
and representatives from any claims, losses, or disputes
arising from your use of the Platform or violation of these Terms.
</p>

<h2>11. Suspension &amp; Termination</h2>

<p>
MyyTutor reserves the right to restrict, suspend, or terminate
access to the Platform without prior notice if a user violates
these Terms or engages in harmful or unlawful conduct.
</p>

<h2>12. Modifications to the Terms</h2>

<p>
These Terms may be updated from time to time.
Revised versions will be published on the Platform with
an updated effective date. Continued use constitutes acceptance.
</p>

<h2>13. Governing Law &amp; Jurisdiction</h2>

<p>
These Terms shall be governed by the laws of India.
All disputes shall be subject to the exclusive jurisdiction
of courts located in Pune, Maharashtra.
</p>

<h2>14. Grievance Officer</h2>

<p>
For complaints, concerns, or legal notices, contact:
</p>

<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- TEACHER AGREEMENT (v1.0)
-- ===========================================
INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('TEACHER_AGREEMENT', 'v1.0', '2026-01-01 00:00:00',
'<h1>Teacher Agreement</h1>

<p>Last updated: January 2026</p>

<p>
This Teacher Agreement ("Agreement") governs the relationship between
MyyTutor.com ("Platform", "we", "us", or "our") and the individual registering
as a tutor ("Tutor", "you").
</p>

<p>
By registering on MyyTutor.com, submitting your details, or accepting
inquiries through the Platform, you confirm that you have read,
understood, and agreed to this Agreement.
</p>

<h2>1. Independent Contractor Relationship</h2>

<p>
Tutors registered on MyyTutor.com act as independent service providers.
Nothing in this Agreement shall be construed as creating an employer-employee,
agency, partnership, or joint venture relationship between MyyTutor and the Tutor.
</p>

<p>
Tutors are solely responsible for how tutoring services are delivered,
including teaching methodology, scheduling, and interaction with parents or students.
</p>

<h2>2. Eligibility &amp; Registration</h2>

<p>
By registering as a Tutor, you confirm that:
</p>

<ul>
<li>You are at least 18 years of age</li>
<li>You have the legal capacity to enter into a binding agreement</li>
<li>You possess the qualifications and experience claimed in your profile</li>
<li>All information submitted during registration is accurate and truthful</li>
</ul>

<h2>3. Tutor Profile &amp; Documentation</h2>

<p>
Tutors are responsible for ensuring that all profile information,
educational qualifications, certifications, and experience details
submitted on the Platform are accurate and up to date.
</p>

<p>
MyyTutor may request supporting documents or physical verification
at any time. Failure to provide valid documentation may result
in suspension or removal from the Platform.
</p>

<h2>4. Professional Conduct &amp; Safety Standards</h2>

<p>
Tutors agree to maintain professional behavior at all times, including:
</p>

<ul>
<li>Being punctual and prepared for scheduled sessions</li>
<li>Communicating respectfully with parents and students</li>
<li>Providing services consistent with declared expertise</li>
<li>Maintaining appropriate boundaries with students and families</li>
</ul>

<p>
<strong>Zero Tolerance Policy:</strong> MyyTutor follows a strict zero-tolerance
policy regarding harassment, child safety violations, or any form of
physical, verbal, or emotional abuse. Any such report may result in
immediate and permanent removal from the Platform and may be reported
to relevant authorities where required by law.
</p>

<h2>5. Fees, Payments &amp; Engagement Reporting</h2>

<p>
Tutors set their own fees and agree payment terms directly with parents.
MyyTutor does not guarantee payment collection unless explicitly agreed.
</p>

<p>
Tutors agree to notify MyyTutor promptly once a tuition arrangement
has been finalized with a parent or if a parent cancels the engagement.
</p>

<h2>6. No Circumvention</h2>

<p>
Tutors agree not to intentionally bypass or misuse the Platform
to avoid current or future platform policies, coordination processes,
or service controls.
</p>

<h2>7. Confidentiality &amp; Data Use</h2>

<p>
Tutors may receive personal information of parents or students
solely for the purpose of delivering tutoring services.
</p>

<p>
Such information must not be shared, misused, or retained
beyond what is necessary for service delivery.
</p>

<h2>8. Suspension &amp; Termination</h2>

<p>
MyyTutor reserves the right to suspend or terminate a Tutor''s
access to the Platform if:
</p>

<ul>
<li>False or misleading information is provided</li>
<li>Repeated or serious complaints are received and verified</li>
<li>Professional, ethical, or safety standards are violated</li>
<li>Legal or regulatory concerns arise</li>
</ul>

<h2>9. Limitation of Liability</h2>

<p>
To the maximum extent permitted by law, MyyTutor shall not be liable
for any loss, injury, dispute, or claim arising from:
</p>

<ul>
<li>Tutoring services delivered by the Tutor</li>
<li>Interactions with parents or students</li>
<li>Offline incidents beyond the Platform''s control</li>
</ul>

<h2>10. Indemnity</h2>

<p>
Tutors agree to indemnify and hold harmless MyyTutor,
its owners, and representatives from any claims,
damages, or disputes arising from:
</p>

<ul>
<li>Breach of this Agreement</li>
<li>Misrepresentation of qualifications</li>
<li>Negligence, misconduct, or safety violations</li>
</ul>

<h2>11. Governing Law &amp; Jurisdiction</h2>

<p>
This Agreement shall be governed by the laws of India.
All disputes shall be subject to the exclusive jurisdiction
of courts located in Pune, Maharashtra.
</p>

<h2>12. Grievance &amp; Contact</h2>

<p>
For complaints, concerns, or legal notices, contact:
</p>

<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- USER AGREEMENT (v1.0) - For Students/Parents
-- ===========================================
INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('USER_AGREEMENT', 'v1.0', '2026-01-01 00:00:00',
'<h1>User Agreement</h1>

<p>Last updated: January 2026</p>

<p>
This User Agreement ("Agreement") governs access to and use of
MyyTutor.com ("Platform", "we", "us", or "our") by any individual
("User", "you"), including parents, guardians, or other persons
submitting inquiries or interacting with tutors through the Platform.
</p>

<p>
By accessing the Platform, submitting an inquiry, sharing personal details,
or engaging with tutors, you confirm that you have read, understood,
and agreed to be bound by this Agreement.
</p>

<h2>1. Nature of the Platform</h2>

<p>
MyyTutor.com is a technology platform that facilitates connections
between parents or guardians seeking tutoring services and independent tutors.
</p>

<p>
MyyTutor does not employ tutors, does not provide tutoring services,
and does not supervise or control offline tutoring sessions.
</p>

<h2>2. Eligibility</h2>

<p>
By using the Platform, you confirm that:
</p>

<ul>
<li>You are at least 18 years of age</li>
<li>You have the legal capacity to enter into a binding agreement</li>
<li>If acting on behalf of a child, you are the parent or legal guardian</li>
</ul>

<h2>3. User Responsibilities</h2>

<p>
Users agree to:
</p>

<ul>
<li>Provide accurate, complete, and truthful information when submitting inquiries</li>
<li>Independently verify tutor identity, qualifications, and suitability</li>
<li>Ensure a safe, respectful, and appropriate environment for offline tutoring sessions</li>
<li>Supervise minors during tutoring sessions where required</li>
<li>Communicate clearly regarding schedules, expectations, and cancellations</li>
</ul>

<h2>4. Safety &amp; Offline Interactions</h2>

<p>
Tutoring sessions typically occur offline at the User''s premises
or at mutually agreed locations.
</p>

<p>
Users acknowledge that all offline interactions occur at their own risk.
MyyTutor does not conduct police verification, background checks,
or physical inspections of tutors or user premises.
</p>

<p>
Users are strongly advised to verify identity documents,
request references, and exercise reasonable caution.
</p>

<h2>5. Payments, Fees &amp; Refunds</h2>

<p>
MyyTutor does not primarily process payments on the Platform.
Tutoring fees and payment terms are agreed directly between Users and tutors.
</p>

<p>
Refunds, fee adjustments, or deductions are governed by the Refund Policy
and are handled on a case-by-case basis.
</p>

<h2>6. Communication &amp; Data Use</h2>

<p>
By using the Platform, Users consent to:
</p>

<ul>
<li>Sharing of contact details with matched tutors</li>
<li>Receiving communications via email, SMS, or WhatsApp related to Platform activity</li>
</ul>

<h2>7. Prohibited Conduct</h2>

<ul>
<li>Providing false or misleading information</li>
<li>Engaging in harassment, abuse, or unsafe behavior</li>
<li>Using the Platform for unlawful or fraudulent purposes</li>
<li>Attempting to bypass platform safeguards or coordination processes</li>
<li>Misusing tutor or student data for non-service purposes</li>
</ul>

<h2>8. Limitation of Liability</h2>

<p>
To the maximum extent permitted by law, MyyTutor shall not be liable for:
</p>

<ul>
<li>Actions or omissions of tutors or users</li>
<li>Quality of tutoring services or academic outcomes</li>
<li>Personal injury, loss, or damage arising from offline interactions</li>
<li>Indirect, incidental, or consequential damages</li>
</ul>

<p>
MyyTutor does not guarantee specific academic results,
grades, or learning outcomes.
</p>

<h2>9. Indemnity</h2>

<p>
Users agree to indemnify and hold harmless MyyTutor,
its owners, and representatives from any claims,
losses, or disputes arising from:
</p>

<ul>
<li>Breach of this Agreement</li>
<li>Misuse of the Platform</li>
<li>Violation of applicable laws or third-party rights</li>
</ul>

<h2>10. Governing Law &amp; Jurisdiction</h2>

<p>
This Agreement shall be governed by the laws of India.
All disputes shall be subject to the exclusive jurisdiction
of courts located in Pune, Maharashtra.
</p>

<h2>11. Grievance &amp; Contact</h2>

<p>
For complaints, concerns, or legal notices, contact:
</p>

<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- REFUND POLICY (v1.0)
-- ===========================================
INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('REFUND_POLICY', 'v1.0', '2026-01-01 00:00:00',
'<h1>Refund Policy</h1>
<p>Last updated: January 2026</p>

<p>
This Refund Policy explains how refunds, cancellations, and dispute support are handled on
MyyTutor.com ("Platform", "we", "us", or "our"). By accessing the Platform, submitting an inquiry,
or engaging with tutors through MyyTutor, you agree to the terms outlined below.
</p>

<h2>1. Nature of Payments on the Platform</h2>
<p>
MyyTutor.com does not primarily operate as a payment gateway or escrow service.
Tutoring fees and payment terms are generally agreed upon directly between parents or guardians
and tutors.
</p>

<p>
In certain cases, MyyTutor may assist parents and tutors through authorized relationship
or support representatives for coordination and operational convenience.
</p>

<h2>2. Assisted Offline Payments</h2>
<p>
Any payments collected offline by authorized representatives are intended solely to
facilitate tutoring arrangements and improve service coordination.
</p>

<p>
Such assistance does not constitute MyyTutor acting as a financial institution,
payment processor, or escrow service.
</p>

<h2>3. Refund Eligibility</h2>
<p>
Since payments are not processed through the Platform by default, MyyTutor does not
guarantee automatic refunds.
</p>

<p>
However, in cases involving genuine service-related concerns such as:
</p>
<ul>
<li>Tutor non-attendance or repeated cancellations</li>
<li>Early discontinuation of services without reasonable notice</li>
<li>Unprofessional conduct materially affecting service delivery</li>
</ul>

<p>
MyyTutor may, after internal review and at its discretion, support a fair resolution.
</p>

<h2>4. Platform Support &amp; Conflict Resolution</h2>
<p>
Where a dispute or service-related concern arises, parents or tutors must raise the issue
only through MyyTutor''s official support channels:
</p>
<ul>
<li>By email to <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Through the <strong>/help</strong> or support section available on the MyyTutor website</li>
</ul>

<p>
Any dispute, complaint, or request for coordination support must be reported through the
above official channels within <strong>48 to 72 hours</strong> of the scheduled session or the
occurrence of the issue.
</p>

<p>
<strong>Important:</strong> Any refund or fee adjustment is evaluated on a case-by-case basis.
MyyTutor does not guarantee full refunds and decisions are based on fairness,
service usage, and available information.
</p>

<h2>5. Trial / Demo Sessions</h2>
<p>
Fees paid for trial or demo sessions, once the session has been completed, are generally
<strong>non-refundable</strong>, as they compensate the tutor for time, preparation, and travel.
</p>

<h2>6. Cancellation of Tutoring Services</h2>
<p>
Cancellations of tutoring sessions or long-term arrangements must be communicated
as early as possible. Parents and tutors are encouraged to clearly discuss cancellation
terms before commencing services.
</p>

<h2>7. No Liability for Third-Party or Offline Payments</h2>
<p>
MyyTutor shall not be liable for any loss, delay, dispute, or claim arising from
payments made directly between users, including cash, UPI, bank transfer,
or third-party payment applications.
</p>

<h2>8. Governing Law</h2>
<p>
This Refund Policy shall be governed by the laws of India.
All disputes shall be subject to the exclusive jurisdiction
of courts located in Pune, Maharashtra.
</p>

<h2>9. Contact</h2>
<p>
For questions or concerns regarding this Refund Policy, you may contact us at:
</p>
<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- COOKIE POLICY (v1.0)
-- ===========================================
INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('COOKIE_POLICY', 'v1.0', '2026-01-01 00:00:00',
'<h1>Cookie Policy</h1>

<p>Last updated: January 2026</p>

<p>
This Cookie Policy explains how MyyTutor.com ("Platform", "we", "us", or "our")
uses cookies and similar technologies when you visit or interact with our website.
This policy should be read together with our Privacy Policy.
</p>

<p>
By continuing to use the Platform, you consent to the use of cookies
in accordance with this Cookie Policy, unless you disable them through
your browser settings.
</p>

<h2>1. What Are Cookies?</h2>

<p>
Cookies are small text files that are placed on your device
(computer, mobile phone, or tablet) when you visit a website.
They help websites recognize users, remember preferences,
and improve overall functionality.
</p>

<h2>2. Types of Cookies We Use</h2>

<h3>2.1 Essential Cookies</h3>

<p>
These cookies are necessary for the basic functioning of the Platform.
Without these cookies, core features such as form submissions,
security protections, and session handling may not work properly.
</p>

<ul>
<li>Session management cookies</li>
<li>Security and fraud-prevention cookies</li>
<li>Cookie consent preference cookies</li>
</ul>

<h3>2.2 Functional Cookies</h3>

<p>
Functional cookies help remember user preferences and improve usability,
such as language selection or previously entered form data.
</p>

<ul>
<li>Saved preferences</li>
<li>Basic user interaction improvements</li>
</ul>

<h3>2.3 Analytics &amp; Performance Cookies</h3>

<p>
These cookies help us understand how users interact with the Platform,
such as which pages are visited most frequently.
This information is used only for internal analysis and service improvement.
</p>

<ul>
<li>Page visit counts</li>
<li>Traffic source analysis</li>
<li>Performance monitoring</li>
</ul>

<h2>3. Third-Party Cookies</h2>

<p>
In some cases, third-party services (such as analytics or security tools)
may place cookies on your device.
These third parties have their own privacy and cookie policies,
and MyyTutor does not control their practices.
</p>

<h2>4. How We Use Cookie Information</h2>

<p>
Information collected through cookies is used to:
</p>

<ul>
<li>Ensure secure and reliable platform operation</li>
<li>Improve website performance and user experience</li>
<li>Understand usage trends and improve features</li>
<li>Detect and prevent abuse or fraudulent activity</li>
</ul>

<h2>5. Managing Cookies</h2>

<p>
You can control or disable cookies through your browser settings.
Most browsers allow you to:
</p>

<ul>
<li>View stored cookies</li>
<li>Delete existing cookies</li>
<li>Block all or specific cookies</li>
</ul>

<p>
Please note that disabling certain cookies may affect
the functionality and performance of the Platform.
</p>

<h2>6. Consent</h2>

<p>
By accessing or continuing to use the Platform,
you provide your consent to the use of cookies
as described in this Cookie Policy.
</p>

<h2>7. Updates to This Cookie Policy</h2>

<p>
This Cookie Policy may be updated from time to time
to reflect changes in technology, law, or platform practices.
Updated versions will be published on the Platform
with a revised effective date.
</p>

<h2>8. Contact</h2>

<p>
If you have questions or concerns regarding this Cookie Policy,
you may contact us at:
</p>

<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================
-- FAQ DOCUMENT (v1.0)
-- ===========================================
INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES
('FAQ_DOCUMENT', 'v1.0', '2026-01-01 00:00:00',
'<h1>Frequently Asked Questions (FAQ)</h1>

<p>Last updated: January 2026</p>

<h2>For Teachers</h2>

<h3>Q: How do I register as a teacher?</h3>
<p>A: Click "Register as Teacher," fill in your details, verify your email, and complete your profile.</p>

<h3>Q: Is registration free?</h3>
<p>A: Yes, MyyTutor is completely free for teachers. We don''t charge any commission.</p>

<h3>Q: How do I receive inquiries?</h3>
<p>A: Students submit inquiries based on your subjects and location. You''ll receive notifications via email and WhatsApp.</p>

<h3>Q: Can I set my own fees?</h3>
<p>A: Yes, you determine your own hourly rates. Payment is directly between you and students.</p>

<h3>Q: How do I update my availability?</h3>
<p>A: Log in to your account and update your availability slots anytime.</p>

<h2>For Students &amp; Parents</h2>

<h3>Q: How do I find a tutor?</h3>
<p>A: Submit an inquiry with your requirements (subjects, location, timing), and matching teachers will contact you.</p>

<h3>Q: Is the platform free?</h3>
<p>A: Yes, using MyyTutor to find tutors is completely free.</p>

<h3>Q: How do I contact a teacher?</h3>
<p>A: Submit an inquiry, and interested teachers will reach out to you via WhatsApp or phone.</p>

<h3>Q: How are fees determined?</h3>
<p>A: Teachers set their own rates. Discuss and agree on fees directly with your chosen tutor.</p>

<h3>Q: What if I''m not satisfied with a tutor?</h3>
<p>A: You can always search for another tutor. There''s no commitment until you agree with a teacher.</p>

<h2>General</h2>

<h3>Q: Is my information safe?</h3>
<p>A: Yes, we use industry-standard security measures. Read our Privacy Policy for details.</p>

<h3>Q: How do I delete my account?</h3>
<p>A: Contact support@myytutor.com with your account email to request deletion.</p>

<h3>Q: How do I report an issue?</h3>
<p>A: Email us at support@myytutor.com with details of your concern.</p>

<h2>Contact</h2>
<ul>
<li>Email: <a href="mailto:admin@myytutor.com">admin@myytutor.com</a></li>
<li>Location: Pune, India</li>
</ul>',
'2026-01-01 00:00:00', '2026-01-01 00:00:00', 0);

-- ===========================================================================
-- END OF DOCUMENT SEED DATA
-- ===========================================================================
