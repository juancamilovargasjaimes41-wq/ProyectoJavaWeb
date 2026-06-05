DELETE FROM detalles_pedido;
DELETE FROM pedidos;
DELETE FROM videojuegos;

INSERT IGNORE INTO categorias (id_categoria, nombre, descripcion) VALUES
(1, 'Accion', 'Juegos de ritmo rapido y combate'),
(2, 'RPG', 'Juegos de rol y aventura larga'),
(3, 'Deportes', 'Simuladores deportivos');

INSERT INTO videojuegos (titulo, descripcion, precio, stock, id_categoria, imagen_url, plataforma) VALUES
('Elden Ring', 'Aventura epica en un mundo abierto lleno de misterios y desafios.', 59.99, 10, 2, '/images/elden-ring.jpg', 'PC'),
('FIFA 24', 'Simulador de futbol profesional con los mejores equipos del mundo.', 69.99, 15, 3, '/images/fifa-24.jpg', 'PS5'),
('God of War Ragnarok', 'Kratos y Atreus en una epica aventura mitologica nordica.', 49.99, 5, 1, '/images/god-of-war.jpg', 'PS5'),
('Cyberpunk 2077', 'RPG de mundo abierto en una megalopolis distopica llena de tecnologia y peligro.', 49.99, 8, 1, '/images/cyberpunk-2077.jpg', 'PC'),
('DOOM Eternal', 'Combate frenetico contra demonios en una campana infernal de accion sin pausa.', 39.99, 12, 1, '/images/doom-eternal.jpg', 'PC'),
('The Witcher 3', 'Aventura de rol epica en un mundo fantastico lleno de monstruos y misiones.', 29.99, 20, 2, '/images/the-witcher-3.jpg', 'PC'),
('Baldur''s Gate 3', 'RPG tactico basado en D&D con una historia profunda y decisiones que importan.', 59.99, 7, 2, '/images/baldurs-gate-3.jpg', 'PC'),
('NBA 2K24', 'Simulador de baloncesto con graficos realistas y modos de juego inmersivos.', 59.99, 6, 3, '/images/nba-2k24.jpg', 'PS5'),
('Red Dead Redemption 2', 'Epica aventura del oeste con un mundo abierto inmenso y una historia inolvidable.', 49.99, 9, 1, '/images/red-dead-redemption-2.jpg', 'PC'),
('Grand Theft Auto V', 'Mundo abierto criminal en Los Santos con tres protagonistas y misiones sin fin.', 19.99, 25, 1, '/images/grand-theft-auto-v.jpg', 'PC'),
('Call of Duty MW III', 'Accion militar moderna con campana intensa y modo multijugador competitivo.', 69.99, 4, 1, '/images/call-of-duty-mw3.jpg', 'PS5'),
('Resident Evil 4', 'Survival horror con accion trepidante y una atmosfera de tension constante.', 39.99, 11, 1, '/images/resident-evil-4.jpg', 'PC'),
('Final Fantasy VII Remake', 'RPG epico con combates en tiempo real y una historia que redefined un clasico.', 49.99, 8, 2, '/images/final-fantasy-vii-remake.jpg', 'PS5'),
('Hogwarts Legacy', 'Sumergete en el mundo magico de Harry Potter con una aventura de rol inmersiva.', 59.99, 6, 2, '/images/hogwarts-legacy.jpg', 'PC'),
('Persona 5 Royal', 'RPG estilo anime con gestion de tiempo, mazmorras y una historia cautivadora.', 39.99, 10, 2, '/images/persona-5-royal.jpg', 'PS5'),
('Starfield', 'Explora el espacio exterior en este RPG de ciencia ficcion con miles de planetas.', 69.99, 3, 2, '/images/starfield.jpg', 'PC'),
('F1 24', 'Simulador oficial de Formula 1 con los pilotos, escuderias y circuitos reales.', 59.99, 7, 3, '/images/f1-24.jpg', 'PS5'),
('WWE 2K24', 'Simulador de lucha libre con superestrellas, modos historia y combates epicos.', 49.99, 5, 3, '/images/wwe-2k24.jpg', 'PS5'),
('EA Sports FC 25', 'Simulador de futbol de nueva generacion con jugabilidad renovada y modos inmersivos.', 69.99, 12, 3, '/images/ea-sports-fc-25.jpg', 'PC'),
('Tony Hawk Pro Skater 1+2', 'Clasico del skateboarding remasterizado con trucos, combos y pistas iconicas.', 29.99, 14, 3, '/images/tony-hawk-pro-skater.jpg', 'PC');