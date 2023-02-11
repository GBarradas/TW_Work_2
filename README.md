Uma associação de estudantes precisa de uma nova interface web para gerir os anúncios de quartos para
arrendar, e também as respetivas solicitações/procura. O seu trabalho é desenvolver a aplicação completa
(interface web e a funcionalidade server-side), usando técnicas estudadas na disciplina, por forma a conseguir:
-  páginas com os conteúdos necessários;
-  apresentação com layout fluido e comportamento responsivo;
-  armazenamento persistente, em PostgreSQL;
-  tolerância a quebras de conetividade com o servidor, mantendo interação amigável;
-  segurança, evitando vulnerabilidades comuns e observando regras de autorização de acesso.  

A estrutura da interface deve incluir:
-  topo: um cabeçalho, com o título do site, e opções de navegação;
-  o conteúdo principal;
-  e em baixo uma zona para patrocinadores, contactos, e os créditos da aplicação web (nomes dos alunos
que desenvolveram a solução).  


Um utilizador pode inserir oferta de alojamento, descrevendo se é quarto ou casa (T0, T1… TN), se é destinado a
arrendatário masculinho, feminino ou indiferente, o preço, e outros detalhes. Um utilizador pode também inserir
um anúncio do tipo procura, onde descreve o tipo de alojamento que pretende encontrar, com campos idênticos.
Cada anúncio deve ter também o nome do anunciante, contacto, a zona (localização aproximada), a data de registo
e um estado (inativo, ativo) atribuí do automaticamente (inativo), mas que será gerido pelo administrador.  

A aplicação web tem zonas distintas, consoante o tipo acesso:
1. Acesso público (não autenticado)
    1.  Um visitante do site deve encontrar na zona de conteúdo principal os destaques com os três últimos anúncios de cada tipo (oferta, procura – obtidos pela operação roomsearch) em modo resumido.
Clicando num dos anúncios, mostrará todos os detalhes desse anúncio.
    1.  Deve procurar otimizar a área de conteúdo principal, ocupando toda a área disponível naquele bloco.
    1.  Procurar, por tipo (oferta, procura) e opcionalmente por zona ou nome do anunciante.
    1.  Devem existir opções para listar as ofertas e os anúncios do tipo procura (com estado ativo).
    1.  Nos resultados de 3 e 4, se existirem mais de 4 anúncios, deve haver paginação.
2. Acesso autenticado (utilizador simples)
    1. Ao visualizar um anúncio, um utilizador pode enviar uma mensagem ao anunciante (ambos
registados). Prepare um formulário junto aos detalhes do anúncio (mostrado apenas a utilizadores
autenticados) que possa recolher a mensagem, que deverá enviar para o servidor.
    1. Relativamente a anúncios do próprio, deve ser possível apresentar mensagens, se existirem
    1. Para registar um anúncio, o utilizadore tem de estar registado no sistema. Terá então acesso a
formulário para recolher dados. O sistema deve atribuir um novo código único ao novo anúncio.
    1. Na resposta ao registo do anúncio, deve mostrar a informação de pagamento MB, obtida na hora a
partir de um serviço externo consultado para nova referência MB para determinado valor (campo
amount, numérico com separador decimal .): http://alunos.di.uevora.pt/tweb/t2/mbref4payment
3. Perfil de administrador
    1. Zona de acesso reservado para utilizadores com perfil específico de administração, que podem:
ativar um anúncio (após validação humana do conteúdo, não ofensivo, do respetivo texto); ou
inativar o anúncio por alteração do respetivo estado   

Não são aceites ficheiros de código-fonte obtidos online (excepto imagens). Os aspetos omissos são decididos
pelos alunos e mencionados no relatório. O trabalho será apresentado no final.  

### Nota: 17
[codigo](https://github.com/GBarradas/TW_Work_2)