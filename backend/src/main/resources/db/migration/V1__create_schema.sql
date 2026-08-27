CREATE TABLE IF NOT EXISTS profiles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    title VARCHAR(150) NOT NULL,
    tagline VARCHAR(255),
    bio TEXT,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    location VARCHAR(100),
    avatar_url VARCHAR(500),
    resume_url VARCHAR(500),
    github_url VARCHAR(300),
    linkedin_url VARCHAR(300),
    instagram_url VARCHAR(300), -- Mantendo o instagram que está no seu Java
    website_url VARCHAR(300),
    years_exp INT,
    available BOOLEAN DEFAULT TRUE,
    update_at TIMESTAMP          -- Ajustado para coincidir com o "update_at" do Java
);
