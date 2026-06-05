-- =============================================================
-- Script de inicializacion de la base de datos
-- Pixel Store - Tienda de Videojuegos
-- =============================================================
-- INSTRUCCIONES:
-- 1. Abrir MySQL Workbench o consola MySQL
-- 2. Ejecutar este script
-- 3. La aplicacion Spring Boot creara las tablas automaticamente
--    y las poblara con datos iniciales al iniciar
-- =============================================================

CREATE DATABASE IF NOT EXISTS tienda_videojuegos
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE tienda_videojuegos;

SELECT CONCAT('Base de datos "tienda_videojuegos" lista.') AS Estado;