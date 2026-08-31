-- ==========================================
-- SEED PARA O PORTFÓLIO
-- ==========================================

-- 1. Profile
INSERT INTO profiles (
    id, name, title, tagline, bio, email, phone, location,
    avatar_url, resume_url, github_url, linkedin_url, instagram_url,
    website_url, years_exp, available, update_at
) VALUES (
    1,
    'Seu Nome',
    'Desenvolvedor Full Stack / TypeScript',
    'Construindo aplicações web modernas, escaláveis e de alta performance.',
    'Desenvolvedor apaixonado por tecnologia, com foco em ecossistemas TypeScript, arquitetura limpa e experiência do usuário impecável. Sempre buscando aprender novas ferramentas e resolver problemas complexos.',
    'seu.email@exemplo.com',
    '+55 (11) 99999-9999',
    'São Paulo, SP',
    'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500',
    '/resume.pdf',
    'https://github.com/seu-usuario',
    'https://linkedin.com/in/seu-usuario',
    'https://instagram.com/seu-usuario',
    'https://seusite.com',
    5,
    TRUE,
    CURRENT_TIMESTAMP
) ON CONFLICT (id) DO NOTHING;

-- 2. Projects
INSERT INTO projects (
    id, title, slug, short_desc, description, thumbnail_url,
    demo_url, github_url, featured, status, sort_order, active,
    started_at, finished_at, created_at
) VALUES
(
    1,
    'E-commerce Moderno',
    'ecommerce-moderno',
    'Plataforma de comércio eletrônico completa com painel administrativo e pagamentos.',
    'Sistema robusto desenvolvido para gerenciamento de produtos, carrinho de compras em tempo real, integração com gateway de pagamentos e painel administrativo completo para controle de estoque e vendas.',
    'https://images.unsplash.com/photo-1557821552-17105176674c?w=800',
    'https://demo-ecommerce.exemplo.com',
    'https://github.com/seu-usuario/ecommerce-moderno',
    TRUE,
    'Completed',
    1,
    TRUE,
    '2025-01-10',
    '2025-04-15',
    '2025-01-10'
),
(
    2,
    'TaskFlow Dashboard',
    'taskflow-dashboard',
    'Gerenciador de tarefas e projetos ágeis com gráficos dinâmicos e tempo real.',
    'Aplicativo web estilo Kanban projetado para equipes de desenvolvimento organizarem seus sprints, acompanharem métricas de produtividade e colaborarem eficientemente.',
    'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=800',
    'https://demo-taskflow.exemplo.com',
    'https://github.com/seu-usuario/taskflow',
    TRUE,
    'Completed',
    2,
    TRUE,
    '2025-05-01',
    '2025-07-20',
    '2025-05-01'
),
(
    3,
    'API Gateway & Auth Service',
    'api-gateway-auth',
    'Microsserviço de autenticação segura utilizando JWT, OAuth2 e Spring/Node.',
    'Serviço centralizado para controle de acesso, emissão e validação de tokens JWT, gerenciamento de permissões baseadas em papéis (RBAC) e proteção de rotas.',
    'https://images.unsplash.com/photo-1558494949-ef010cbdcc31?w=800',
    NULL,
    'https://github.com/seu-usuario/api-gateway-auth',
    FALSE,
    'Completed',
    3,
    TRUE,
    '2025-08-01',
    '2025-09-10',
    '2025-08-01'
) ON CONFLICT (id) DO NOTHING;

-- 3. Project Tags
INSERT INTO project_tags (project_id, tag) VALUES
(1, 'TypeScript'),
(1, 'React'),
(1, 'Node.js'),
(1, 'TailwindCSS'),
(2, 'TypeScript'),
(2, 'React'),
(2, 'TailwindCSS'),
(2, 'Git'),
(3, 'Java'),
(3, 'TypeScript'),
(3, 'Docker')
ON CONFLICT (project_id, tag) DO NOTHING;

-- 4. Project Images
INSERT INTO project_images (id, project_id, url, alt_text, sort_order, created_at) VALUES
(1, 1, 'https://images.unsplash.com/photo-1557821552-17105176674c?w=1200', 'Tela inicial do E-commerce', 1, CURRENT_TIMESTAMP),
(2, 1, 'https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1200', 'Painel administrativo', 2, CURRENT_TIMESTAMP),
(3, 2, 'https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=1200', 'Quadro Kanban do TaskFlow', 1, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;

-- 5. Skills
INSERT INTO skills (id, name, category, proficiency, icon_name, sort_order, active) VALUES
(1, 'TypeScript', 'Linguagens', 95, 'typescript', 1, TRUE),
(2, 'JavaScript', 'Linguagens', 90, 'javascript', 2, TRUE),
(3, 'React', 'Frontend', 90, 'react', 3, TRUE),
(4, 'TailwindCSS', 'Frontend', 85, 'tailwind', 4, TRUE),
(5, 'Node.js', 'Backend', 85, 'nodejs', 5, TRUE),
(6, 'Git', 'Ferramentas', 90, 'git', 6, TRUE),
(7, 'IntelliJ IDEA', 'Ferramentas', 85, 'intellij', 7, TRUE),
(8, 'Docker', 'DevOps', 75, 'docker', 8, TRUE)
ON CONFLICT (id) DO NOTHING;

-- 6. Experiences
INSERT INTO experiences (
    id, company, role, description, logo_url, location,
    type, started_at, ended_at, current, sort_order, active
) VALUES
(
    1,
    'Tech Solutions Ltda',
    'Desenvolvedor Full Stack Sênior',
    'Liderança técnica no desenvolvimento de aplicações web escaláveis baseadas em TypeScript e microsserviços. Otimização de performance e implementação de testes automatizados.',
    'https://images.unsplash.com/photo-1549923746-c502d488b3ea?w=200',
    'São Paulo, SP (Remoto)',
    'full_time',
    '2023-02-01',
    NULL,
    TRUE,
    1,
    TRUE
),
(
    2,
    'Inovação Digital S/A',
    'Desenvolvedor Frontend',
    'Criação de interfaces responsivas e componentizadas utilizando React, TailwindCSS e integração com APIs RESTful.',
    'https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=200',
    'São Paulo, SP',
    'full_time',
    '2021-06-01',
    '2023-01-31',
    FALSE,
    2,
    TRUE
) ON CONFLICT (id) DO NOTHING;

-- 7. Experience Technologies
INSERT INTO experience_technologies (experience_id, technology) VALUES
(1, 'TypeScript'),
(1, 'React'),
(1, 'Node.js'),
(1, 'Git'),
(2, 'TypeScript'),
(2, 'React'),
(2, 'TailwindCSS')
ON CONFLICT (experience_id, technology) DO NOTHING;

-- 8. Educations
INSERT INTO educations (
    id, institution, degree, field_of_study, description,
    logo_url, grade, started_at, ended_at, current, sort_order, active
) VALUES (
    1,
    'Universidade de São Paulo (USP)',
    'Bacharelado',
    'Ciência da Computação',
    'Foco em engenharia de software, estruturas de dados, algoritmos e arquitetura de sistemas.',
    'https://images.unsplash.com/photo-1523050854058-8df90110c9f1?w=200',
    '9.2 / 10',
    '2017-02-01',
    '2021-12-15',
    FALSE,
    1,
    TRUE
) ON CONFLICT (id) DO NOTHING;

-- 9. Certifications
INSERT INTO certifications (
    id, name, issuer, credential_id, credential_url,
    image_url, issued_at, expires_at, sort_order, active
) VALUES (
    1,
    'Advanced TypeScript Developer',
    'Certiify Global',
    'TS-984210-BR',
    'https://certiify.io/verify/TS-984210-BR',
    'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=200',
    '2024-06-15',
    NULL,
    1,
    TRUE
) ON CONFLICT (id) DO NOTHING;

-- 10. Testimonials
INSERT INTO testimonials (
    id, name, role, company, content, avatar_url,
    rating, featured, sort_order, active
) VALUES (
    1,
    'Carlos Eduardo',
    'Product Manager',
    'Tech Solutions Ltda',
    'Profissional extremamente competente, focado em qualidade de código e entrega de valor contínua para o negócio. Trabalhar junto foi excelente para o time.',
    'https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200',
    5,
    TRUE,
    1,
    TRUE
) ON CONFLICT (id) DO NOTHING;

-- 11. Contacts
INSERT INTO contacts (
    id, name, email, subject, message, phone, status, ip_address, created_at
) VALUES (
    1,
    'Ana Souza',
    'ana.souza@exemplo.com',
    'Proposta de Projeto',
    'Olá, vi seu portfólio e gostaria de conversar sobre um projeto de desenvolvimento web.',
    '+55 (11) 98888-7777',
    'new',
    '192.168.1.10',
    CURRENT_TIMESTAMP
) ON CONFLICT (id) DO NOTHING;