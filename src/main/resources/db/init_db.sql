-- Script de criação inicial do banco para desenvolvimento
-- Execute como superuser (por exemplo: psql -U postgres -f init_db.sql)

CREATE DATABASE pravdb;

-- Cria usuário de aplicação (altere a senha antes de usar em produção)
CREATE USER prav_user WITH ENCRYPTED PASSWORD 'troque_essa_senha';

GRANT ALL PRIVILEGES ON DATABASE pravdb TO prav_user;
