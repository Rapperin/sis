-- Courses
CREATE TABLE IF NOT EXISTS courses (
                                       id           BIGSERIAL PRIMARY KEY,
                                       code         VARCHAR(20)  NOT NULL UNIQUE,
    name         VARCHAR(150) NOT NULL,
    credit       SMALLINT     NOT NULL CHECK (credit BETWEEN 1 AND 15),
    created_at   TIMESTAMP    NOT NULL DEFAULT now()
    );

-- Enrollment (öğrenci-derse kaydı)
CREATE TABLE IF NOT EXISTS enrollments (
                                 student_id BIGINT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
    course_id  BIGINT NOT NULL REFERENCES courses(id)  ON DELETE CASCADE,
    semester   VARCHAR(10) NOT NULL,            -- e.g. 2025F, 2026S
    status     VARCHAR(20) NOT NULL DEFAULT 'ENROLLED', -- ENROLLED, DROPPED, COMPLETED
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (student_id, course_id, semester)
    );

CREATE INDEX IF NOT EXISTS idx_enroll_student ON enrollments(student_id);
CREATE INDEX IF NOT EXISTS idx_enroll_course  ON enrollments(course_id);
