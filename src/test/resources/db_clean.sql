-- Clean up restaurantes
DELETE FROM restaurante;

-- Clean up cardapio
DELETE FROM cardapio;

-- Clean up usuarios first (they depend on login, endereco, perfil)
DELETE FROM usuario;

-- Now clean up logins
DELETE FROM login;

-- Now clean up addresses
DELETE FROM endereco;

-- Finally clean up perfil
DELETE FROM perfil;