INSERT INTO profiles (name,title,tagline,bio,email,location,github_url,linkedin_url,years_exp,available)
VALUES ('João Silva','Desenvolvedor Full Stack Sênior',
    'Transformando ideias em produtos digitais de alta qualidade',
    'Desenvolvedor apaixonado por tecnologia com mais de 5 anos de experiência criando soluções web robustas e escaláveis.',
    'joao@example.com','São Paulo, SP — Brasil',
    'https://github.com/joaosilva','https://linkedin.com/in/joaosilva',5,TRUE)
ON CONFLICT DO NOTHING;

INSERT INTO skills (name,category,proficiency,icon_name,sort_order) VALUES
('React','Frontend',92,'react',1),('TypeScript','Frontend',88,'typescript',2),
('Tailwind CSS','Frontend',90,'tailwind',3),('Next.js','Frontend',82,'nextjs',4),
('Java 21','Backend',90,'java',5),('Spring Boot','Backend',90,'spring',6),
('PostgreSQL','Backend',85,'postgresql',7),('Docker','DevOps',78,'docker',8),
('AWS','DevOps',72,'aws',9),('Git','Ferramentas',95,'git',10)
ON CONFLICT DO NOTHING;
