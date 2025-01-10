# API de Pedidos

Este repositório contém uma API RESTful para o gerenciamento de pedidos. A API permite que você receba e envie pedidos entre sistemas externos, adicione produtos a um pedido, calcule o total do pedido e consulte os produtos que fazem parte de um pedido. Abaixo estão os detalhes dos **endpoints** da API e uma explicação sobre o uso da anotação `@Transactional` para garantir a consistência dos dados.

## **Endpoints da API**

### 1. **Receber pedido do Sistema Externo A**

- **Endpoint:** `POST /orders/receive-from-external-A`
- **Descrição:** Este endpoint recebe uma lista de produtos de um sistema externo A. Para cada produto, ele será adicionado ao pedido atual.
- **Parâmetros de entrada:**
  - **Request Body:** Lista de objetos `Product` que representam os produtos do pedido.
  - Exemplo de corpo da requisição:
    ```json
    [
      {
        "id": 1,
        "name": "Produto 1",
        "price": 100.0
      },
      {
        "id": 2,
        "name": "Produto 2",
        "price": 200.0
      }
    ]
    ```
- **Resposta:** Retorna uma mensagem indicando que o pedido foi recebido com sucesso do sistema externo A.
  - **Exemplo de resposta:**
    ```json
    "Pedido recebido do sistema externo A."
    ```
- **Motivo de uso:** Este endpoint permite que o sistema receba pedidos de sistemas externos, sendo útil em integrações com outros sistemas que enviam pedidos para processamento interno.

---

### 2. **Enviar pedido para o Sistema Externo B**

- **Endpoint:** `POST /orders/send-to-external-B`
- **Descrição:** Envia todos os pedidos existentes para o sistema externo B.
- **Parâmetros de entrada:** Nenhum.
- **Resposta:**
  - **200 OK:** Retorna uma lista de todos os pedidos (`OrderTable`).
  - Exemplo de resposta:
    ```json
    [
      {
        "id": 1,
        "total": 300.0,
        "products": [
          {
            "id": 1,
            "name": "Produto 1",
            "price": 100.0
          },
          {
            "id": 2,
            "name": "Produto 2",
            "price": 200.0
          }
        ]
      }
    ]
    ```
- **Motivo de uso:** Permite integrar com sistemas externos (como ERPs ou outros sistemas de back-office) para enviar pedidos já processados.

---

### 3. **Adicionar um produto ao pedido**

- **Endpoint:** `POST /orders/add-product`
- **Descrição:** Adiciona um novo produto ao pedido atual.
- **Parâmetros de entrada:**
  - **Request Body:** Objeto `Product` contendo os dados do produto a ser adicionado.
  - Exemplo de corpo da requisição:
    ```json
    {
      "name": "Produto 3",
      "price": 150.0
    }
    ```
- **Resposta:**
  - **200 OK:** Retorna um status de sucesso (HTTP Status 200).
  - **409 Conflict:** Caso o produto já exista no sistema (por nome).
- **Motivo de uso:** Permite a adição de novos produtos ao pedido corrente. Pode ser utilizado, por exemplo, durante o processo de construção de um pedido em tempo real.

---

### 4. **Calcular o total do pedido**

- **Endpoint:** `GET /orders/total`
- **Descrição:** Calcula e retorna o total do pedido atual.
- **Resposta:**
  - **200 OK:** Retorna o valor total do pedido.
  - Exemplo de resposta:
    ```json
    {
      "total": 450.0
    }
    ```
- **Motivo de uso:** Este endpoint é utilizado para calcular e retornar o valor total dos produtos que foram adicionados ao pedido.

---

### 5. **Obter todos os produtos do pedido**

- **Endpoint:** `GET /orders/products`
- **Descrição:** Retorna todos os produtos que foram adicionados ao pedido atual.
- **Resposta:**
  - **200 OK:** Retorna a lista de produtos presentes no pedido.
  - Exemplo de resposta:
    ```json
    [
      {
        "id": 1,
        "name": "Produto 1",
        "price": 100.0
      },
      {
        "id": 2,
        "name": "Produto 2",
        "price": 200.0
      }
    ]
    ```
- **Motivo de uso:** Permite visualizar os produtos que compõem o pedido atual. Útil para auditar ou visualizar os itens do pedido antes de realizar o pagamento ou enviar o pedido para outro sistema.
