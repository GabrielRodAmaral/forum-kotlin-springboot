CREATE TABLE curso(
    id bigint not null auto_increment,
    nome varchar(50),
    categoria varchar(50),
    primary key(id)
);

-- No dia a dia não se faz inserts nesses arquivos mas nesse caso específico vou fazer
insert into curso values(1, 'Kotlin', 'Programação');