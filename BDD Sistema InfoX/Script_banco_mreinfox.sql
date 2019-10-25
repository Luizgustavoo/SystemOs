create database dbinfox;
use dbinfox;
create table usuarios;
create table usuarios(
iduser int primary key,
usuario varchar (50) not null,
fone varchar (15),
login varchar (15) not null unique,
senha varchar (15)not null


);

insert into usuarios(iduser, usuario, fone, login, senha)
values(1, 'Luiz Gustavo', '9999-9999', 'lgustavo', '123456');

select * from usuarios;

insert into usuarios(iduser, usuario, fone, login, senha)
values(2, 'Administrador', '9999-9999', 'admin', 'admin');
insert into usuarios(iduser, usuario, fone, login, senha)
values(3, 'Bill Gates', '9999-9999', 'bill', '123456');

update usuarios set fone = '8888-8888' where iduser=2;
 delete from usuarios where iduser=3;
 use dbinfox;
 
 create table clientes(idcli int primary key auto_increment,
 nomecli varchar (50) not null, 
 endcli varchar (100),
 fonecli varchar (15) not null,
 emailcli varchar (50),
 cpfcli varchar (11) not null
 );
 
 insert into clientes (nomecli, endcli, fonecli, emailcli, cpfcli)
 values ('Heber Dias', 'Rua Fogo no cu, 111', '8888-8888', 'heber@gmail.com', '12354879654');
 
 select * from clientes;
 
 create table os(idos int primary key auto_increment,
 data_os timestamp default current_timestamp,
 equipamento varchar (60) not null,
 defeito varchar (150) not null,
 servico varchar (150),
 tecnico varchar (30),
 valor decimal (10,2),
 idcli int not null,
 foreign key (idcli) references clientes (idcli)
 );
 insert into os (equipamento, defeito, servico, tecnico, valor, idcli)
 values ('Celular', 'Não liga', 'Troca da bateria', 'Zé', '90.55', 1);
 
 select * from os;
 select
 O.idos,equipamento,defeito,servico,valor,
 C.nomecli,fonecli
 from os as O
 inner join clientes as C
 on (O.idcli = C.idcli);
 
 use dbinfox;
 describe clientes;
 alter table os add tipo varchar (15) not null after data_os;
 describe os;
  alter table os add situacao varchar (20) not null after tipo;
  
-- A instrução abaixo seleciona e ordena por nome todos os clientes cadastrados --
select * from clientes order by nomecli;
  use dbinfox;
-- A instrução abaixo faz a inserção e a união de dados de duas tabelas --
-- OSER é uma variavel que contém os campos desejados da tabela os --
-- CLI é outra variavel que contem os campos esejados da tabela clientes --
select
OSER.idos,data_os,tipo,situacao,equipamento,valor,
CLI.nomecli,fonecli
from os as OSER
inner join clientes as CLI
on (CLI.idcli = OSER.idcli);