-- ===========================================================================
-- V6__update_legal_documents.sql
-- Mahatutor - Comprehensive Legal Documents Insertion
-- Purpose: Insert new versions of legal documents (v1.1)
-- Compliance: IT Act 2000, SPDI Rules 2011, DPDP Act 2023, Consumer Protection (E-Commerce) Rules 2020
-- Operator: Mahatutor
-- ===========================================================================

INSERT INTO documents (type, version, published_at, content, created_at, updated_at, lock_version) VALUES

-- ===========================================
-- 1. PRIVACY POLICY (v1.1)
-- ===========================================
('PRIVACY_POLICY', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Privacy Policy</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>
<p><strong>Last Modified:</strong> January 2026</p>

<h2>1. GENERAL</h2>
<p>
    This Privacy Policy ("Policy") forms an integral part of the Terms of Use and covers the collection, use, and disclosure of personal information by <strong>Mahatutor</strong> ("Company", "We", "Us", "Our"), operating the website and platform known as <strong>Mahatutor</strong>. 
</p>
<p>
    We recognize the importance of your privacy and are committed to maintaining the confidentiality of the information you share with us. This Policy calls out our practices regarding the handling of your data in compliance with the <strong>Information Technology Act, 2000</strong> ("IT Act"), the <strong>SPDI Rules 2011</strong>, and the <strong>Digital Personal Data Protection Act, 2023</strong> ("DPDP Act").
</p>

<h2>2. COLLECTION OF INFORMATION & DATA MINIMIZATION</h2>
<p>
    We rely on the principle of <strong>Data Minimization</strong>. We only collect information that is strictly necessary for the purpose of facilitating tutoring services and ensuring platform safety.
</p>
<h3>A. Personal Information</h3>
<p>
    We may collect:
</p>
<ul>
    <li><strong>Identity Data:</strong> Name, gender, date of birth.</li>
    <li><strong>Contact Data:</strong> Address, email, phone number.</li>
    <li><strong>Verification Data:</strong> Govt ID proofs (Aadhaar/PAN) for identity verification (Teachers).</li>
    <li><strong>Educational Data:</strong> Qualifications (Teachers).</li>
</ul>

<h2>3. USE OF INFORMATION</h2>
<p>
    We use your data for:
    <ul>
        <li>Service facilitation (connecting Tutors & Students).</li>
        <li>Communication & Support.</li>
        <li>Legal Compliance.</li>
    </ul>
</p>

<h3>B. Third-Party Service Providers</h3>
<p>
    We may share limited data with trusted third-party service providers solely for platform operations:
    <ul>
        <li>SMS/Communication providers (for OTP verification)</li>
        <li>Cloud infrastructure providers</li>
        <li>Analytics services (anonymized data only)</li>
    </ul>
    All such providers are contractually bound to maintain confidentiality and use data only for specified purposes.
</p>

<h2>4. DATA LOCALIZATION & RETENTION</h2>
<p>
    <strong>Data Localization:</strong> All sensitive personal data is processed and stored on servers located within <strong>India</strong>, in compliance with the DPDP Act.
    <br>
    <strong>Retention:</strong> We retain data only as long as necessary. Transaction logs are kept for 180 days as per CERT-In directives.
</p>
<h3>Data Storage & Cross-Border Processing</h3>
<p>
    <strong>Primary Storage:</strong> Your personal data is primarily stored on secure servers provided by <strong>DigitalOcean</strong>, located in <strong>Bangalore, India</strong>.
    <br>
    <strong>Limited Processing:</strong> Limited cross-border processing may occur for infrastructure support services (e.g., email delivery, analytics) subject to reasonable security safeguards and compliance with the DPDP Act 2023.
</p>
<h3>Lawful Disclosure</h3>
<p>
    We may share your Personal Data with law enforcement agencies, courts, or statutory authorities upon receipt of valid legal requests (e.g., FIRs, court orders, notices). Such disclosure shall be limited to what is legally required under Indian law.
</p>

<h2>5. DATA SECURITY & BREACH NOTIFICATION</h2>
<p>
    We employ robust security measures (encryption, access controls).
    <br>
    <strong>Data Breach Notification:</strong> In the unlikely event of a personal data breach posing a risk to your rights, We will notify the <strong>Data Protection Board of India</strong> and the affected Data Principals within <strong>72 hours</strong> of becoming aware of the breach, as mandated by the DPDP Act.
</p>

<h2>6. YOUR RIGHTS (CONSENT WITHDRAWAL)</h2>
<p>
    As a Data Principal, you have rights under the DPDP Act:
</p>
<ul>
    <li><strong>Right to Access & Correction:</strong> Request a summary or correction of your data.</li>
    <li><strong>Right to Withdraw Consent:</strong> You may withdraw your consent for processing your data at any time by emailing the Grievance Officer. 
    <em>Note: Withdrawal of consent may lead to termination of services.</em></li>
    <li><strong>Right to Erasure:</strong> Request deletion of your data (subject to legal retention periods). We will process erasure requests within <strong>30 days</strong>.</li>
    <li><strong>Right to Grievance Redressal:</strong> Contact us for any privacy concerns.</li>
</ul>

<h2>7. CHILDREN''S PRIVACY</h2>
<p>
    Our Services are not intended for use by children under 18 directly. We <strong>do not</strong> strictly collect data from minors directly. All data regarding a minor Student is collected from the <strong>Parent/Legal Guardian</strong> with their verifiable consent. Parents have the right to review or request deletion of their child''s data.
</p>

<h2>8. COOKIES</h2>
<p>
    We use Essential and Analytics cookies to improve experience. You can manage these in your browser settings.
</p>

<h2>9. GRIEVANCE OFFICER & DATA PROTECTION OFFICER</h2>
<p>
    For any privacy concerns or to exercise your rights, contact our Data Protection Officer (DPO) / Grievance Officer:
</p>
<ul>
    <li><strong>Name:</strong> Grievance Officer, Mahatutor</li>
    <li><strong>Email:</strong> <a href="mailto:admin@mahatutor.com">admin@mahatutor.com</a></li>
    <li><strong>Address:</strong> Pune, Maharashtra, India</li>
</ul>
<p>
    We will acknowledge complaints within 24 hours and resolve them within the statutory timeline (usually 15 days).
</p>',
NOW(), NOW(), 0),

-- ===========================================
-- 2. TERMS OF USE (v1.1)
-- ===========================================
('TERMS_OF_USE', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Terms of Use</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>
<p><strong>Last Updated:</strong> January 2026</p>

<div class="legal-document">

<!-- IMPORTANT DISCLAIMER BANNER -->
<div style="background-color: #fff3cd; color: #856404; padding: 15px; border: 1px solid #ffeeba; border-radius: 5px; margin-bottom: 20px;">
    <strong>PLATFORM DISCLAIMER:</strong> <br>
    Mahatutor is a <strong>purely technical intermediary</strong>. We <strong>DO NOT</strong> employ tutors.
    We <strong>ARE NOT RESPONSIBLE</strong> for the conduct, teaching quality, safety, or payments of any user.
    All interactions are peer-to-peer.
</div>

<p>
    This document is an electronic record in terms of the <strong>Information Technology Act, 2000</strong>.
</p>

<p>
    By using <strong>Mahatutor</strong>, you agree to these Terms.
</p>

<h2>1. PLATFORM ROLE & RANKING DISCLAIMER</h2>
<p>
    Mahatutor is an Intermediary under the IT Act.
    <br>
    <strong>Ranking & Recommendation Disclaimer:</strong> Any ranking or recommendation of Tutors on the platform is based on automated algorithms taking into account neutral factors (e.g., profile completeness, subject match, location). It does <strong>not</strong> constitute an endorsement or warranty of the Tutor''s quality or character by Mahatutor.
    <br>
    <strong>Verification Disclaimer:</strong> Mahatutor does not guarantee the accuracy of background checks or user information. Users are legally required to perform their own independent verification of credentials before engagement.
</p>

<h2>2. ELIGIBILITY</h2>
<p>
    Use is restricted to persons who can form binding contracts. Minors must use the platform under parent supervision.
</p>

<h2>3. FEES & PAYMENTS</h2>
<p>
    Mahatutor may charge fees for premium features. Tuition fees are settled strictly between Parent and Tutor. We are not liable for non-payment or refund disputes between users.
</p>

<h2>4. DISCLAIMER OF WARRANTIES</h2>
<p>
    Usage is "As Is". We disclaim all warranties including fitness for purpose, safety, or academic results.
</p>

<h2>5. LIMITATION OF LIABILITY</h2>
<p>
    TO THE MAX EXTENT PERMITTED BY LAW, MAHATUTOR SHALL NOT BE LIABLE FOR ANY INDIRECT, INCIDENTAL, OR CONSEQUENTIAL DAMAGES. OUR LIABILITY IS LIMITED TO THE AMOUNT PAID BY YOU TO MAHATUTOR (IF ANY) IN THE LAST 6 MONTHS.
</p>

<h2>6. FORCE MAJEURE</h2>
<p>
    Mahatutor shall not be liable for any failure to perform its obligations hereunder where such failure results from any cause beyond reasonable control, including, without limitation, mechanical, electronic or communications failure or degradation (including "line-noise" interference), acts of God, riots, corporate disputes, or government actions.
</p>

<h2>7. INDEMNIFICATION</h2>
<p>
    You agree to indemnify Mahatutor against claims arising from your use of the Platform or violation of these Terms.
</p>

<h2>8. GOVERNING LAW & ARBITRATION</h2>
<p>
    <strong>Governing Law:</strong> Laws of India.
    <br>
    <strong>Dispute Resolution:</strong> If a dispute cannot be resolved amicably within 30 days, it shall be referred to a <strong>Sole Arbitrator</strong> appointed by Mahatutor in accordance with the <strong>Arbitration and Conciliation Act, 1996</strong> (as amended).
    <br>
    The <strong>Seat and Venue</strong> of arbitration shall be <strong>Pune, Maharashtra</strong>. The language shall be English. The award shall be final and binding.
    <br>
    <strong>Jurisdiction:</strong> Subject to arbitration, courts in Pune shall have exclusive jurisdiction.
    <br>
    <strong>Consumer Rights:</strong> Nothing in this arbitration clause shall operate to restrict or exclude any statutory consumer rights that are mandatorily applicable to you under Indian law.
</p>

<h2>9. SURVIVAL</h2>
<p>
    Provisions regarding Intellectual Property, Indemnification, Limitation of Liability, and Dispute Resolution shall survive the termination of your account or these Terms.
</p>

<h2>10. STATUTORY RIGHTS & GENERAL DISCLAIMER</h2>
<p>
    No clause in these documents eliminates rights available to you under mandatory Indian law. All disclaimers and limitations of liability operate subject to applicable statutory protections.
</p>

<h2>11. CONTACT</h2>
<p>
    Email: <a href="mailto:admin@mahatutor.com">admin@mahatutor.com</a>
</p>

<h2>12. USER CONTENT & LICENSE</h2>
<p>
    By posting reviews, ratings, or any content on Mahatutor, you grant us a non-exclusive, royalty-free, worldwide license to use, display, and distribute such content for platform operations. You retain ownership but grant us the right to moderate or remove content violating our policies.
</p>

<h2>13. ACCOUNT SUSPENSION & TERMINATION</h2>
<p>
    <strong>Our Rights:</strong> Mahatutor reserves the right to suspend or permanently terminate any user account without prior notice for:
    <ul>
        <li>Violation of these Terms or applicable laws</li>
        <li>Fraudulent activity or suspected fraud</li>
        <li>Abusive behavior towards other users</li>
        <li>Providing false information during registration</li>
    </ul>
    Terminated users shall have no claim for refund of any fees paid to Mahatutor.
</p>

<h2>14. INTELLECTUAL PROPERTY</h2>
<p>
    The Mahatutor name, logo, website design, and all platform content are proprietary to Mahatutor and protected under Indian intellectual property laws. You may not copy, reproduce, or use any Mahatutor trademarks or content without prior written consent.
</p>

<h2>15. MODIFICATION OF TERMS</h2>
<p>
    Mahatutor reserves the right to modify these Terms at any time. Changes will be posted on this page with an updated "Last Modified" date. Continued use of the platform after changes constitutes acceptance of the modified Terms.
</p>

<h2>16. SEVERABILITY</h2>
<p>
    If any provision of these Terms is held to be invalid or unenforceable by a court of competent jurisdiction, such invalidity shall not affect the validity of the remaining provisions, which shall remain in full force and effect.
</p>

</div>',
NOW(), NOW(), 0),

-- ===========================================
-- 3. TEACHER AGREEMENT (v1.1)
-- ===========================================
('TEACHER_AGREEMENT', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Teacher/Tutor Service Agreement</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>
<p><strong>Last Updated:</strong> January 2026</p>

<div class="legal-document">

<h2>1. PREAMBLE</h2>
<p>
    This Teacher Service Agreement is a binding contract between You ("Tutor") and Mahatutor.
    <br>
    <strong>Independent Contractor:</strong> You are an independent freelancer. No employment relationship exists.
    <br>
    <strong>Insurance Disclaimer:</strong> Mahatutor does <strong>not</strong> provide any insurance coverage (medical, liability, or theft) for Tutors. You are advised to obtain your own professional insurance.
</p>

<h2>2. CODE OF CONDUCT</h2>
<p>
    You agree to maintain high professional standards.
    <br>
    <strong>Zero Tolerance:</strong> Physical abuse, sexual harassment, or showing up under the influence of substances will lead to immediate permanent ban and police reporting.
</p>

<h2>3. SAFETY & SURVILLANCE CONSENT</h2>
<h3>3.1. CCTV Consent (Narrow Scope)</h3>
<p>
    You acknowledge that Parents may use CCTV for <strong>safety and security purposes only</strong>. 
    <br>
    You consent to being recorded solely for the purpose of ensuring the physical safety of the student and yourself. 
    <br>
    <em>Limitation:</em> This consent does NOT extend to the publication of such recordings on social media or for any commercial purpose by the Parent.
</p>

<h3>3.2. Physical Boundaries</h3>
<p>
    Strictly no physical contact with students.
</p>

<h2>4. PAYMENT & TAXES</h2>
<p>
    You collect fees directly from Parents. You are responsible for your own taxes. Mahatutor is not liable for non-payment by Parents.
</p>

<h2>5. BACKGROUND CHECKS</h2>
<p>
    Mahatutor reserves the right to conduct background checks (criminal/education). Falsification of documents is fraud.
</p>

<h2>6. INDEMNITY</h2>
<p>
    You indemnify Mahatutor against any claims arising from your misconduct or negligence.
</p>

<h2>7. LAW ENFORCEMENT COOPERATION</h2>
<p>
    Mahatutor cooperates fully with police and legal authorities. We may share your registration details and logs without prior notice in case of FIRs, fraud investigations, or safety incidents, as permitted by applicable Indian laws.
</p>

<h2>8. NON-SOLICITATION</h2>
<p>
    You agree not to directly solicit or advertise to Parents/Students initially contacted through Mahatutor to bypass the platform. This restriction applies during your active registration and for 6 months after account termination.
    <br>
    <em>Clarification:</em> This does not apply to students you already knew before joining Mahatutor.
</p>

<h2>9. ACCOUNT TERMINATION</h2>
<p>
    Either party may terminate this Agreement at any time. You may delete your account via settings. Mahatutor may terminate your account for policy violations. Upon termination, your profile will be removed, but we may retain data as required by law.
</p>

<h2>10. SEVERABILITY</h2>
<p>
    If any provision of this Agreement is held to be invalid or unenforceable, the remaining provisions shall remain in full force and effect.
</p>

</div>',
NOW(), NOW(), 0),

-- ===========================================
-- 4. USER AGREEMENT (v1.1)
-- ===========================================
('USER_AGREEMENT', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Parent/Student Safety &amp; Usage Agreement</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>
<p><strong>Last Updated:</strong> January 2026</p>

<div class="legal-document">

<h2>1. PREAMBLE</h2>
<p>
    This Agreement is a contract between You ("Parent", "Guardian", "Student") and Mahatutor. 
    It governs your responsibilities while hiring a Tutor through our Platform. 
    <strong>Primary Objective:</strong> Ensure a safe, conducive, and professional learning environment for home tutoring.
</p>

<h2>2. PARENTAL RESPONSIBILITIES: THE TEACHING ENVIRONMENT</h2>
<p>
    As the host of the home tutoring session, You (the Parent) are legally and ethically responsible for the environment provided to the Tutor.
</p>

<h3>2.1. Safety &amp; Location</h3>
<ul>
    <li><strong>Open Door Policy:</strong> Tutoring sessions MUST be conducted in an open area of the house such as the living room or dining hall. 
    <br>If a bedroom must be used (e.g., for quietness), the <strong>door must remain completely open</strong> at all times. Closed-door sessions are strictly prohibited for safety reasons.</li>
    <li><strong>Presence of Guardian:</strong> For students under the age of 18 (minors), or for female students with male tutors (and vice versa), the physical presence of a parent or adult guardian in the house is <strong>MANDATORY</strong> regarding the entire duration of the session.</li>
    <li><strong>CCTV (Recommended):</strong> We highly recommend installing CCTV cameras in the tutoring area for the safety and security of both the child and the tutor.</li>
</ul>

<h3>2.2. Conduct Towards Tutor</h3>
<ul>
    <li><strong>Respect:</strong> You and your family members must treat the Tutor with respect and dignity. Abusive language, shouting, or disparaging remarks are grounds for the Tutor to terminate the engagement immediately.</li>
    <li><strong>Payments:</strong> You agree to pay the agreed tuition fees promptly on the due date. Delays in payment may lead to suspension of services.</li>
    <li><strong>Privacy:</strong> You shall respect the Tutor''s privacy and not request personal favors, errands, or non-academic tasks (e.g., babysitting, pick-up/drop).</li>
</ul>

<h2>3. MEDICAL &amp; SPECIAL NEEDS DISCLOSURE (IMPORTANT)</h2>
<p>
    <strong>You MUST inform the Tutor</strong> prior to the commencement of classes about any:
    <ul>
        <li><strong>Medical Conditions:</strong> Specific allergies (food/chalk dust), asthma, epilepsy, or other conditions that might require emergency attention.</li>
        <li><strong>Learning Disabilities:</strong> Dyslexia, ADHD, or other needs effectively requiring specialized teaching methods.</li>
    </ul>
    Failure to disclose critical medical information releases the Tutor and Mahatutor from liability in case of a related emergency.
</p>

<h2>4. VERIFICATION: YOUR DUTY</h2>
<p>
    <strong>WARNING:</strong> While Mahatutor collects documents, we are a digital platform. We do not physically visit every tutor''s house. Therefore, the <strong>FINAL VERIFICATION responsibility lies with the Parent.</strong>
</p>
<h3>4.1. Mandatory Check Steps</h3>
<p>Before the first class begins, You MUST:</p>
<ol>
    <li><strong>Check ID:</strong> Ask the Tutor to show their original Aadhaar Card or PAN Card. Match the photo with the person standing in front of you.</li>
    <li><strong>Check Qualifications:</strong> Ask to see the original Degree Certificates (B.A., B.Sc, B.Ed, etc.).</li>
    <li><strong>Emergency Contact:</strong> Take an alternate emergency contact number from the Tutor (e.g., their parent or spouse).</li>
</ol>

<h2>5. STUDENT CODE OF CONDUCT</h2>
<p>
    You (Parent) are responsible for your child''s behavior. The Student must:
</p>
<ul>
    <li>Be ready with books and stationery before the Tutor arrives.</li>
    <li>Complete homework assignments on time.</li>
    <li>Dress appropriately for the session.</li>
    <li>Not distract the Tutor with non-academic conversation.</li>
</ul>

<h2>6. PAYMENT &amp; REFUNDS</h2>
<h3>6.1. Direct Engagement</h3>
<p>
    You acknowledge that Mahatutor is a connector. You are hiring the Tutor directly. 
    <strong>Mahatutor is NOT liable</strong> for financial disputes.
</p>

<h3>6.2. Recommendations</h3>
<ul>
    <li><strong>No Large Advances:</strong> We strongly advise AGAINST paying large advance fees (e.g., 6 months in advance). Pay monthly or weekly after classes are conducted.</li>
    <li><strong>Receipts:</strong> Maintain a simple diary or digital record (WhatsApp chat) of payments made.</li>
</ul>

<h2>7. CANCELLATION POLICY</h2>
<p>
    <ul>
        <li><strong>Student Cancellation:</strong> If you cancel a scheduled class with short notice (e.g., less than 2 hours), the Tutor may count it as a paid session at their discretion, depending on travel time invested.</li>
        <li><strong>Respect for Time:</strong> Please respect the professional time of the Tutor.</li>
    </ul>
</p>

<h2>8. EMERGENCY &amp; SAFETY INCIDENTS</h2>
<h3>8.1. Reporting Mechanism</h3>
<p>
    If you suspect any inappropriate behavior by a Tutor (e.g., wrong touch, weird messages, under influence of alcohol), You must:
</p>
<ol>
    <li><strong>Stop the session immediately.</strong></li>
    <li><strong>Ask the Tutor to leave.</strong></li>
    <li><strong>Report to Mahatutor:</strong> Email <a href="mailto:admin@mahatutor.com">admin@mahatutor.com</a> within 24 hours.</li>
    <li><strong>Legal Action:</strong> If a crime has been committed, file a police complaint (FIR) immediately. Mahatutor will assist police with the Tutor''s registration data.</li>
</ol>

<h2>9. LIMITATION OF LIABILITY</h2>
<p>
    YOU EXPRESSLY UNDERSTAND AND AGREE THAT MAHATUTOR IS MERELY A FACILITATOR. WE SHALL NOT BE LIABLE FOR:
    <ul>
        <li>Any theft, damage to property, or loss caused by the Tutor.</li>
        <li>Any physical injury or harm caused to the Student.</li>
        <li>Any failure of the Student to pass exams or achieve desired grades.</li>
    </ul>
    BY HIRING A TUTOR, YOU ASSUME ALL RISKS ASSOCIATED WITH BRINGING A STRANGER INTO YOUR HOME.
</p>

<h2>10. INDEMNIFICATION & SURVIVAL</h2>
<p>
    You agree to indemnify Mahatutor against any claims or legal costs arising from:
    <ul>
        <li>Your failure to provide a safe teaching environment.</li>
        <li>Any false accusation made by you against a Tutor.</li>
        <li>Your violation of labor laws or non-payment of agreed fees.</li>
    </ul>
    <br>
    <strong>Survival:</strong> Clauses regarding Indemnification and Liability shall survive termination of this Agreement.
</p>

<h2>11. LAW ENFORCEMENT SHARING</h2>
<p>
    In the event of user complaints, safety incidents, or fraud investigations, Mahatutor may share relevant user data with police and statutory authorities provided a valid legal request is received or as required by Indian law.
</p>

</div>',
NOW(), NOW(), 0),

-- ===========================================
-- 5. REFUND POLICY (v1.1)
-- ===========================================
('REFUND_POLICY', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Refund, Cancellation &amp; Dispute Policy</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>

<h2>1. PLATFORM FEES (ZERO LIABILITY)</h2>
<p>
    <strong>Mahatutor is a connection platform.</strong> We currently do not charge a registration fee (`0`) for Tutors or Parents.
    <br>
    In the future, if we introduce a "Premium Subscription" or "Lead Fee", such fees will be <strong>non-refundable</strong> once the service (listing/lead access) is delivered, regardless of whether a tuition engagement materializes.
</p>

<h2>2. TUITION FEES (PEER-TO-PEER TRANSACTION)</h2>
<p>
    <strong>CRITICAL NOTICE:</strong> Mahatutor does <strong>NOT</strong> process, hold, or guarantee tuition fees. All financial transactions happen directly between the Parent and the Tutor.
</p>

<h3>2.1. No Refund from Platform</h3>
<p>
    If you pay a Tutor in advance and they stop coming, or if you are a Tutor and a Parent refuses to pay:
    <ul>
        <li><strong>Mahatutor CANNOT refund the money</strong> because we never held it.</li>
        <li>We are not a financial arbitrator/court.</li>
    </ul>
</p>

<h3>2.2. Best Practices to Avoid Loss</h3>
<ul>
    <li><strong>Pay Weekly/Monthly:</strong> Never pay for 6 months or 1 year in advance.</li>
    <li><strong>Receipts:</strong> Always take a written acknowledgement for cash payments.</li>
    <li><strong>Demo:</strong> Take a paid/free demo class before finalizing.</li>
</ul>

<h2>3. DISPUTE RESOLUTION MECHANISM</h2>
<p>
    While we are not liable, we care about our community. If a financial fraud occurs:
    <ol>
        <li><strong>Report:</strong> Email <a href="mailto:admin@mahatutor.com">admin@mahatutor.com</a> with chat screenshots and payment proof.</li>
        <li><strong>Investigation:</strong> We will review the case. If the other party is found guilty of fraud, we will <strong>PERMANENTLY BAN</strong> their device and account.</li>
        <li><strong>Legal Aid:</strong> We will provide the other party''s registration details (IP, Phone, Email) to you/Police for filing a legal complaint.</li>
    </ol>
</p>

<h2>4. CANCELLATION POLICY</h2>
<table>
    <tr>
        <th>Action</th>
        <th>Policy</th>
    </tr>
    <tr>
        <td><strong>Tutor Cancels</strong></td>
        <td>Must give 2 hours notice. Must provide a compensatory class for missed sessions.</td>
    </tr>
    <tr>
        <td><strong>Parent Cancels</strong></td>
        <td>Must give 2 hours notice. If cancelled at the last minute, the Tutor may count it as a paid session (subject to mutual understanding).</td>
    </tr>
</table>
',
NOW(), NOW(), 0),

-- ===========================================
-- 6. FAQ (v1.1)
-- ===========================================
('FAQ_DOCUMENT', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Frequently Asked Questions</h1>

<h2>1. FOR PARENTS</h2>
<h3>Q: Is Mahatutor safe?</h3>
<p>A: We verify phone numbers and collect ID proofs, but <strong>you must verify original documents</strong> yourself. We also recommend our "Open Door Policy" and CCTV for home sessions.</p>

<h3>Q: Do I pay Mahatutor or the Teacher?</h3>
<p>A: You pay the <strong>Teacher directly</strong>. Mahatutor does not take a cut from the monthly fee.</p>

<h3>Q: What if the teacher takes money and runs away?</h3>
<p>A: Since you pay directly, we cannot refund it. However, report them immediately. We will ban them and help you file a police complaint.</p>

<h3>Q: Can I ask for a female tutor?</h3>
<p>A: Yes, you can filter tutors by gender preferences to ensure comfort.</p>

<h2>2. FOR TUTORS</h2>
<h3>Q: Is it free to join?</h3>
<p>A: Yes, creating a profile is free.</p>

<h3>Q: Why do I need to consent to CCTV?</h3>
<p>A: Many homes have cameras for safety. To protect yourself from false accusations and to ensure student safety, consent to recording is part of our terms.</p>

<h3>Q: What if a parent doesn''t pay?</h3>
<p>A: Stop classes immediately. We can ban the parent from the platform, but we cannot recover the money for you.</p>

<h2>3. GENERAL</h2>
<h3>Q: How do I report abuse?</h3>
<p>A: Use the "Report User" button on their profile or email <a href="mailto:admin@mahatutor.com">admin@mahatutor.com</a> (Emergency Response).</p>',
NOW(), NOW(), 0),

-- ===========================================
-- 7. COOKIE POLICY (v1.1)
-- ===========================================
('COOKIE_POLICY', 'v1.1', '2026-01-05 00:00:00', 
'<h1>Cookie Policy</h1>
<p><strong>Effective Date:</strong> January 5, 2026</p>

<h2>1. WHAT ARE COOKIES?</h2>
<p>
    Cookies are small text files stored on your device (computer/mobile) when you visit Mahatutor. They help us remember your login sessions and preferences.
</p>

<h2>2. TYPES OF COOKIES WE USE</h2>
<ul>
    <li><strong>Essential Cookies:</strong> Strictly necessary for the website to function (e.g., keeping you logged in). You cannot switch these off.</li>
    <li><strong>Analytics Cookies:</strong> Help us understand how many users visit our site (e.g., Google Analytics). These incorporate anonymous data.</li>
    <li><strong>Functional Cookies:</strong> Remember your language or location preferences.</li>
</ul>

<h2>3. YOUR CONTROL</h2>
<p>
    You can choose to disable cookies through your browser settings. However, disabling "Essential Cookies" may cause parts of Mahatutor (like Login/Dashboard) to stop working.
</p>

<h2>4. THIRD PARTY COOKIES</h2>
<p>
    We may use third-party services (like payment gateways or map services) that set their own cookies. We do not control these cookies.
</p>',
NOW(), NOW(), 0);