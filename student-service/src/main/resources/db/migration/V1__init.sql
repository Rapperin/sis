CREATE TABLE IF NOT EXISTS students (
                                        id BIGSERIAL PRIMARY KEY,
                                        first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    birth_date DATE
    );
