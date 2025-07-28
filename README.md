# 🚀 Plataforma de Processamento de Pedidos

Sistema completo e escalável para ingestão, persistência e monitoramento de pedidos — desenvolvido com foco em arquitetura orientada a eventos, monitoramento e boas práticas de microsserviços.

## 🧱 Tecnologias Utilizadas

- Java 17 + Spring Boot
- Kafka + Zookeeper
- PostgreSQL
- Prometheus + Grafana
- Docker + Docker Compose

## 📦 Arquitetura

- Mensageria com Kafka para comunicação assíncrona
- Banco PostgreSQL para persistência
- Monitoramento com Prometheus e visualização com Grafana
- Infra totalmente conteinerizada via Docker Compose
- Suporte à integração com novos microserviços via Kafka topics

## 🐳 Como executar localmente

```bash
# Subir todos os containers
docker-compose up -d

# Verificar se Kafka está ativo
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --list

# Acessar Grafana
http://localhost:3000 (admin/admin)

# Acessar Prometheus
http://localhost:9090


