
CREATE TABLE EMPRESA_TRANSPORTE (
    id_empresa_transporte BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa VARCHAR(30) NOT NULL
);


CREATE TABLE TRANSPORTISTAS (
    id_transportista BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa_transporte BIGINT NOT NULL,
    p_nombre VARCHAR(10) NOT NULL,
    s_nombre VARCHAR(10) NOT NULL,
    p_apellido VARCHAR(20) NOT NULL,
    s_apellido VARCHAR(20) NOT NULL,
    cuerpo_rut VARCHAR(9) NOT NULL,
    dv_rut VARCHAR(1) NOT NULL,
    CONSTRAINT fk_transp_empresa FOREIGN KEY (id_empresa_transporte) REFERENCES EMPRESA_TRANSPORTE(id_empresa_transporte)
);


CREATE TABLE PEDIDOS (
    id_pedido BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_cliente BIGINT NOT NULL, -- Referencia lógica externa
    id_transportista BIGINT NOT NULL,   -- Relación física local
    id_boleta BIGINT NOT NULL,          -- Referencia lógica externa
    fecha_de_envio DATETIME NOT NULL,
    fecha_de_entrega DATETIME,          -- Sin NOT NULL para permitir pedidos en tránsito
    entregado BOOLEAN NOT NULL DEFAULT FALSE,
    INDEX idx_pedido_usuario (id_usuario_cliente),
    INDEX idx_pedido_boleta (id_boleta),
    CONSTRAINT fk_pedido_transportista FOREIGN KEY (id_transportista) REFERENCES TRANSPORTISTAS(id_transportista)
);
