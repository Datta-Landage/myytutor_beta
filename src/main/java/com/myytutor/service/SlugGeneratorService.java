package com.myytutor.service;

import com.myytutor.entity.Teacher;
import com.myytutor.entity.TeacherSubjectMapping;
import com.myytutor.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Service for generating SEO-friendly, immutable slugs for teacher profiles.
 * 
 * SLUG FORMAT: {full-name}-class-{class-number}-{primary-subject}-{city}
 * Example: rahul-sharma-class-10-maths-pune
 * 
 * RULES:
 * - Generated ONCE on approval/publish
 * - Immutable after generation
 * - Globally unique with numeric suffix for conflicts
 * - Lowercase, hyphen-separated, no special characters
 */
@Service
public class SlugGeneratorService {
    
    private static final Logger logger = LoggerFactory.getLogger(SlugGeneratorService.class);
    
    private final TeacherRepository teacherRepository;
    
    // Centralized subject mapping table
    private static final Map<String, String> SUBJECT_SLUG_MAP = Map.ofEntries(
        Map.entry("mathematics", "maths"),
        Map.entry("math", "maths"),
        Map.entry("maths", "maths"),
        Map.entry("physics", "physics"),
        Map.entry("chemistry", "chemistry"),
        Map.entry("biology", "biology"),
        Map.entry("english", "english"),
        Map.entry("hindi", "hindi"),
        Map.entry("marathi", "marathi"),
        Map.entry("sanskrit", "sanskrit"),
        Map.entry("science", "science"),
        Map.entry("social studies", "social-studies"),
        Map.entry("social science", "social-studies"),
        Map.entry("history", "history"),
        Map.entry("geography", "geography"),
        Map.entry("civics", "civics"),
        Map.entry("economics", "economics"),
        Map.entry("computer science", "computer-science"),
        Map.entry("information technology", "it"),
        Map.entry("python", "python"),
        Map.entry("java", "java"),
        Map.entry("web development", "web-development"),
        Map.entry("jee", "jee"),
        Map.entry("neet", "neet"),
        Map.entry("mht-cet", "mht-cet")
    );
    
    // Allowed cities only
    private static final Set<String> ALLOWED_CITIES = Set.of("pune", "mumbai", "ahilyanagar");
    
    // Regex patterns
    private static final Pattern TITLE_PATTERN = Pattern.compile("^(mr\\.?|ms\\.?|mrs\\.?|dr\\.?|prof\\.?)\\s+", Pattern.CASE_INSENSITIVE);
    private static final Pattern SPECIAL_CHARS_PATTERN = Pattern.compile("[^a-z0-9\\s-]");
    private static final Pattern MULTIPLE_SPACES_PATTERN = Pattern.compile("\\s+");
    private static final Pattern MULTIPLE_HYPHENS_PATTERN = Pattern.compile("-+");
    
    public SlugGeneratorService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }
    
    /**
     * Generate a unique, SEO-friendly slug for a teacher.
     * Format: {full-name}-class-{class-number}-{primary-subject}-{city}
     * 
     * @param teacher The teacher entity
     * @return Unique slug string
     * @throws IllegalArgumentException if required fields are missing or invalid
     */
    public String generateSlug(Teacher teacher) {
        logger.info("Generating slug for teacher: id={}, name={}", teacher.getId(), teacher.getFullName());
        
        // Validate and normalize each component
        String normalizedName = normalizeName(teacher.getFullName());
        String normalizedClass = normalizeClass(getPrimaryClassId(teacher));
        String normalizedSubject = normalizeSubject(getPrimarySubject(teacher));
        String normalizedCity = normalizeCity(teacher.getCity());
        
        // Build base slug
        String baseSlug = String.format("%s-%s-%s-%s", 
            normalizedName, normalizedClass, normalizedSubject, normalizedCity);
        
        // Clean up any edge cases
        baseSlug = cleanSlug(baseSlug);
        
        // Ensure uniqueness
        String uniqueSlug = ensureUniqueness(baseSlug);
        
        logger.info("Generated slug: {} for teacher id={}", uniqueSlug, teacher.getId());
        return uniqueSlug;
    }
    
    /**
     * Normalize name: lowercase, hyphenate, remove titles
     */
    String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name is required for slug generation");
        }
        
        String normalized = name.toLowerCase().trim();
        
        // Remove titles (Mr, Ms, Dr, etc.)
        normalized = TITLE_PATTERN.matcher(normalized).replaceAll("");
        
        // Remove special characters
        normalized = SPECIAL_CHARS_PATTERN.matcher(normalized).replaceAll("");
        
        // Collapse multiple spaces and convert to hyphens
        normalized = MULTIPLE_SPACES_PATTERN.matcher(normalized).replaceAll("-");
        
        // Remove multiple hyphens
        normalized = MULTIPLE_HYPHENS_PATTERN.matcher(normalized).replaceAll("-");
        
        // Trim leading/trailing hyphens
        normalized = normalized.replaceAll("^-+|-+$", "");
        
        return normalized;
    }
    
    /**
     * Normalize class: always prefix with "class-"
     */
    String normalizeClass(Integer classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID is required for slug generation");
        }
        return "class-" + classId;
    }
    
    /**
     * Normalize subject using mapping table
     */
    String normalizeSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject is required for slug generation");
        }
        
        String key = subject.toLowerCase().trim();
        String mapped = SUBJECT_SLUG_MAP.get(key);
        
        if (mapped != null) {
            return mapped;
        }
        
        // If not in mapping table, create a slug-friendly version
        logger.warn("Subject '{}' not found in mapping table, using normalized form", subject);
        return key.replaceAll("\\s+", "-").replaceAll("[^a-z0-9-]", "");
    }
    
    /**
     * Normalize city: validate against allowed list
     */
    String normalizeCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City is required for slug generation");
        }
        
        String normalized = city.toLowerCase().trim();
        
        if (!ALLOWED_CITIES.contains(normalized)) {
            throw new IllegalArgumentException("City '" + city + "' is not in allowed list: " + ALLOWED_CITIES);
        }
        
        return normalized;
    }
    
    /**
     * Clean up the final slug
     */
    private String cleanSlug(String slug) {
        // Remove multiple hyphens
        slug = MULTIPLE_HYPHENS_PATTERN.matcher(slug).replaceAll("-");
        // Remove leading/trailing hyphens
        slug = slug.replaceAll("^-+|-+$", "");
        return slug;
    }
    
    /**
     * Get primary mapping deterministically
     */
    private TeacherSubjectMapping getPrimaryMapping(Teacher teacher) {
        if (teacher.getSubjects() == null || teacher.getSubjects().isEmpty()) {
            throw new IllegalArgumentException("Teacher must have at least one subject for slug generation");
        }
        
        return teacher.getSubjects().stream()
            .sorted((m1, m2) -> {
                // 1. Sort by Class ID descending (Higher classes preferred as primary)
                Integer c1 = m1.getSubjectClass().getClassId();
                Integer c2 = m2.getSubjectClass().getClassId();
                int classCompare = c2.compareTo(c1);
                if (classCompare != 0) return classCompare;
                
                // 2. Sort by Subject Name alphabetical (Stability)
                String s1 = m1.getSubjectClass().getSubjectName();
                String s2 = m2.getSubjectClass().getSubjectName();
                return s1.compareToIgnoreCase(s2);
            })
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unable to determine primary subject"));
    }

    /**
     * Get primary class ID from teacher's subject mappings
     */
    private Integer getPrimaryClassId(Teacher teacher) {
        return getPrimaryMapping(teacher).getSubjectClass().getClassId();
    }
    
    /**
     * Get primary subject name from teacher's subject mappings
     */
    private String getPrimarySubject(Teacher teacher) {
        return getPrimaryMapping(teacher).getSubjectClass().getSubjectName();
    }
    
    /**
     * Ensure slug uniqueness using numeric suffix strategy
     */
    private String ensureUniqueness(String baseSlug) {
        // Check if base slug is available
        if (!teacherRepository.existsBySlug(baseSlug)) {
            return baseSlug;
        }
        
        // Find next available suffix
        int suffix = 2;
        String candidateSlug;
        
        do {
            candidateSlug = baseSlug + "-" + suffix;
            suffix++;
        } while (teacherRepository.existsBySlug(candidateSlug) && suffix < 1000);
        
        if (suffix >= 1000) {
            throw new IllegalStateException("Unable to generate unique slug after 1000 attempts");
        }
        
        return candidateSlug;
    }
}
