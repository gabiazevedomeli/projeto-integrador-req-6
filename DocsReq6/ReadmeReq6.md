# <p align="center"> <img alt="coffee_cup" src="https://user-images.githubusercontent.com/80721339/173413428-56d4f208-6f5f-437d-ad91-cb878a90a01a.png" width="30px" /> Cadastro de Produtos - Requisito 06 <img alt="coffee_cup" src="https://user-images.githubusercontent.com/80721339/173413428-56d4f208-6f5f-437d-ad91-cb878a90a01a.png" width="30px" /> </p>



<p align="center">
  <img align="" alt="mascoteJava" src="https://github.com/gabiazevedo/java-codes/blob/main/java_gif.gif" height="170px" width="100%" />
</p>

Esta feature se destina a `cadastrar` produtos, `atualizá-los` e `buscá-los` por categoria, visando agregar valor à aplicação tanto para a pessoa
`Representante` de um armazém, que terá como atribuição gerenciar a operação de Fulfillment da BU Mercado Frescos, quanto para a pessoa `Compradora`,
que poderá filtrar seus produtos de interesse por categorias mais específicas, agilizando assim a busca.

A proposta de melhoria para o `Projeto Integrador` comtempla a `implementação`, `testes` e `documentação` detalhada de 3 endpoints. São eles:


### - POST - Endpoint que tem como finalidade criar um produto inexistente.

`/api/v1/fresh-products/create-products`

#### Request Body

```json
[
    {
        "name": "Maracujá",
        "type": "Fresco",
        "categoryName": "Frutas",
        "price": 9.00
    },
    {
        "name": "Iogurte Sem Lactose",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 10.00
    }
]
```

#### Response

```json
[

    {
        "id": 14,
        "name": "Maracujá",
        "type": "Fresco",
        "categoryName": "Frutas",
        "price": 9.0
    },
    {
        "id": 15,
        "name": "Iogurte Sem Lactose",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 10.0
    }
]
```
---

### - PATCH - Endpoint que tem como finalidade atualizar parcialmente um produto existente.

`/api/v1/fresh-products/update-product/{product-id}`

#### Request Body

```json
{
    "name": "Iogurte Grego",
    "type": "Refrigerado",
    "categoryName": "Laticínios"
}
```

#### Response

```json
{
    "updateMessage": "The product Iogurte Grego was successfully updated!",
    "updatedProduct": {
        "id": 8,
        "name": "Iogurte Grego",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 20.1
    }
}
```

---

### - GET - Endpoint que tem como finalidade buscar todos os produtos existentes e pertencentes à uma categoria.

`/api/v1/fresh-products/category/{category-name}`

#### Response

 ```json
  [
    {
        "id": 5,
        "name": "Manteiga",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 20.1
    },
    {
        "id": 8,
        "name": "Iogurte Grego",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 20.1
    },
    {
        "id": 13,
        "name": "Iogurte Sem Lactose",
        "type": "Refrigerado",
        "categoryName": "Laticínios",
        "price": 10.0
    }
]
  ```
---

<p align="center">Feito com <b>Java 11</b>, <b>SpringBoot</b>, <b>Hibernate</b> e 💛 por <b>Gabi Azevedo</b></p>



