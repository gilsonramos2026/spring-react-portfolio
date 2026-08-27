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

CREATE TABLE IF NOT EXISTS projects (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    slug VARCHAR(200) NOT NULL UNIQUE,
    short_desc VARCHAR(300) NOT NULL,
    description TEXT,
    thumbnail_url VARCHAR(500),
    demo_url VARCHAR(300),
    github_url VARCHAR(300),
    featured BOOLEAN DEFAULT FALSE,
    status VARCHAR(30) DEFAULT 'Completed', -- Ajustado para 'Completed' com 'C' maiúsculo
    sort_order INT DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    started_at DATE,
    finished_at DATE,
    created_at DATE NOT NULL                 -- Ajustado para DATE para casar com o LocalDate do Java
);

CREATE TABLE IF NOT EXISTS project_tags (
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE,
    tag VARCHAR(80) NOT NULL,
    PRIMARY KEY (project_id, tag)           -- Adicionada Chave Primária Composta profissional
);

CREATE TABLE IF NOT EXISTS project_images (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE,
    url VARCHAR(500) NOT NULL,
    alt_text VARCHAR(200),
    sort_order INT DEFAULT 0,
    created_at TIMESTAMP NOT NULL            -- Mantido TIMESTAMP pois o Java de ProjectImage usa LocalDateTime
);
