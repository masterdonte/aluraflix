ALTER TABLE video ADD categoriaid bigint;
ALTER TABLE video ADD CONSTRAINT fk_categoria_video FOREIGN KEY (categoriaid) REFERENCES categoria(id);
update video set categoriaid = 1;
--alter table video add categoriaid bigint not null;
