-- Insere os tipos de usuário no banco de dados
INSERT INTO tipo_usuario (id, nome, descricao)
VALUES
    (1, 'DONO_RESTAURANTE', 'Usuário que pode gerenciar um restaurante.'),
    (2, 'CLIENTE', 'Usuário final que pode fazer pedidos e avaliações.') ON CONFLICT (id) DO NOTHING;

-- Sincroniza sequência para o maior id
SELECT setval('tipo_usuario_seq', COALESCE((SELECT MAX(id) FROM tipo_usuario), 0));