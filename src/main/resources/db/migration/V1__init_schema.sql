CREATE TABLE roles (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(30) NOT NULL UNIQUE,
    description VARCHAR(100)
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL,
    last_login_at TIMESTAMP
);

CREATE TABLE user_roles (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id UUID NOT NULL REFERENCES users(id),
    role_id UUID NOT NULL REFERENCES roles(id),
    CONSTRAINT uk_user_roles_user_role UNIQUE (user_id, role_id)
);

CREATE TABLE categories (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(150),
    is_active BOOLEAN NOT NULL
);

CREATE TABLE tags (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    name VARCHAR(50) NOT NULL,
    slug VARCHAR(60) NOT NULL UNIQUE,
    description VARCHAR(150),
    is_active BOOLEAN NOT NULL
);

CREATE TABLE articles (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    title VARCHAR(150) NOT NULL,
    summary VARCHAR(300) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    view_count BIGINT NOT NULL,
    published_at TIMESTAMP,
    is_featured BOOLEAN NOT NULL,
    author_id UUID NOT NULL REFERENCES users(id),
    category_id UUID NOT NULL REFERENCES categories(id)
);

CREATE TABLE article_tags (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    article_id UUID NOT NULL REFERENCES articles(id),
    tag_id UUID NOT NULL REFERENCES tags(id),
    CONSTRAINT uk_article_tags_article_tag UNIQUE (article_id, tag_id)
);

CREATE INDEX idx_articles_status ON articles(status);
CREATE INDEX idx_articles_author_id ON articles(author_id);
CREATE INDEX idx_articles_category_id ON articles(category_id);
CREATE INDEX idx_user_roles_user_id ON user_roles(user_id);
CREATE INDEX idx_article_tags_article_id ON article_tags(article_id);
