# spring-boot-blog-api
API REST para Blog feito em Spring Boot


<table>
    <tr>
        <td>Segurança</td>
        <td>Permitir o cadastro de usuários e login com autenticação via token JWT</td>
    </tr>
    <tr>
        <td>Post</td>
        <td>Permitir o cadastro e consulta de posts com texto, imagens e links.
Apenas o criador do post poderá ter permissão para excluí-lo</td>
    </tr>
    <tr>
        <td>Comentários</td>
        <td>Suportar a adição e exclusão de comentários em posts. Os posts
poderão ser visíveis a todos os usuários. Apenas o criador do comentário poderá ter permissão para excluí-lo</td>
    </tr>
    <tr>
        <td>Fotos</td>
        <td>Permitir a criação de álbuns de fotos. As fotos dos álbuns poderão ser visíveis a todos os usuários. Apenas o dono de um álbum poderá excluí-lo</td>
    </tr>
</table>

## Tecnologias utilizadas
- Java 15
- Spring Boot
- Spring Data JPA
- Spring Security utilizando JWT e RefreshToken
- PostgreSQL
- Liquibase
- BeanValidation
- Docker
- Docker compose
- OpenAPI/Spring Docs

### Instalação com Docker Compose
```sh
git clone https://github.com/AndersonCRocha/spring-boot-blog-api
cd spring-boot-blog-api
docker compose up
```
