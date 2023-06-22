insert into pais(id_pais, nome_pais) 
values(1, 'Brasil');
insert into pais(id_pais, nome_pais) 
values(2, 'Argentina');

insert into equipe (id_equipe,nome_equipe)
values (1,'equipe1');
insert into equipe (id_equipe,nome_equipe)
values (2,'equipe2');

insert into campeonato (id_campeonato,descricao_campeonato,ano_campeonato)
values (1,'campeonato1','2015');
insert into campeonato (id_campeonato,descricao_campeonato,ano_campeonato)
values (2,'campeonato2','2010');

insert into pista (id_pista,tamanho_pista,pais_id_pais)
values (1,'3000',1);
insert into pista (id_pista,tamanho_pista,pais_id_pais)
values (2,'4000',2);

insert into piloto (id_piloto, nome, equipe_id_equipe, pais_id_pais)
values (1, 'Piloto1', 1, 2);
insert into piloto (id_piloto,nome, equipe_id_equipe, pais_id_pais)
values(2,'piloto2',2,2);
insert into piloto (id_piloto,nome, equipe_id_equipe, pais_id_pais)
values(3,'piloto3',2,1);

insert into corrida(id_corrida, data, pista_id_pista, campeonato_id_campeonato)
values(1, '2010-05-20T18:10:00Z', 1, 1);
insert into corrida(id_corrida, data, pista_id_pista, campeonato_id_campeonato) 
values(2, '2015-10-14T19:20:00Z', 2, 2);
