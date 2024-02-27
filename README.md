## Getting Started

Follow these steps to build and run the Spring Boot application using Docker:

1. **Clone the Repository**

    ```bash
    git clone https://github.com/your-username/your-repository.git
    ```

2. **Build the Application**

    ```bash
    ./gradlew assemble
    ```

3. **Build the Docker Image**

    ```bash
    docker-compose build
    ```

4. **Run the Docker Container**

    ```bash
    docker-compose up
    ```

5. **(Optional) Run Without Docker**

    If you prefer to run the application without Docker, ensure you have the necessary environment variables configured. Refer to the docker-compose.yml file for details on setting up environment variables.

---

# API Documentation

<details>
  <summary><h2>Endpoint: Save Shipment Information</h2></summary>

- **URL**: `/api/shipments`
- **HTTP Method**: POST
- **Description**: This endpoint allows you to save shipment information.

**Request Body:**
```json
{
  "taxPayerRegistrationNo": "12345678900",
  "senderAddress": {
    "streetName": "Rua do remetente",
    "neighbourhood": "Bairro do remetente",
    "city": "Cidade do remetente",
    "stateCodeEnum": "SP",
    "addressNumber": "456",
    "zipCode": "12345678"
  },
  "recipientAddress": {
    "streetName": "Rua do destinatário",
    "neighbourhood": "Bairro do destinatário",
    "city": "Cidade do destinatário",
    "stateCodeEnum": "RJ",
    "addressNumber": "789",
    "zipCode": "98765432"
  },
  "value": 100.00,
  "weight": 5,
  "cubingMeasurement": 2.5
}
```
**Response:**
- **Status Code**: 201 Created
- **Description**: The shipment information was successfully saved.
- **Location Header**: The `Location` header in the response contains the URL of the newly created resource.

#### Example Response

```http
HTTP/1.1 201 Created
Location: /api/shipments/${UUID}
```
</details>


<details>
  <summary><h2>Endpoint: Find Shipment Information by Tracking Number</h2></summary>

- **URL**: `/api/shipments/{trackingNo}`
- **HTTP Method**: GET
- **Description**: This endpoint retrieves shipment information based on the provided tracking number.

**Request Parameters:**
- `trackingNo` (UUID, path parameter, required): The unique identifier of the shipment tracking number.

**Response:**
- **Status Code**: 200 OK
- **Description**: The shipment information was successfully retrieved.
- **Response Body**: A JSON object containing the shipment information.

#### Example Response Body

```json
{
  "trackingNo": "123e4567-e89b-12d3-a456-426614174000",
  "taxPayerRegistrationNo": "12345678900",
  "senderAddress": {
    "streetName": "Rua do remetente",
    "neighbourhood": "Bairro do remetente",
    "city": "Cidade do remetente",
    "stateCodeEnum": "SP",
    "addressNumber": "456",
    "zipCode": "98765432"
  },
  "recipientAddress": {
    "streetName": "Rua do destinatário",
    "neighbourhood": "Bairro do destinatário",
    "city": "Cidade do destinatário",
    "stateCodeEnum": "RJ",
    "addressNumber": "789",
    "zipCode": "98765432"
  },
  "shipmentStatus": "POSTED",
  "postingDate": "2024-02-27",
  "estimatedArrivalDate": "2024-03-03",
  "value": 100.00,
  "weight": 5,
  "cubingMeasurement": 2.5
}
```
</details>

<details>
  <summary><h2>Endpoint: Find All Shipments by Tax Payer Registration Number</h2></summary>

- **URL**: `/api/shipments`
- **HTTP Method**: GET
- **Description**: This endpoint allows you to retrieve all shipments associated with a specific tax payer registration number.

  #### Query Parameters

  - `taxPayerRegistrationNo` (String, required): The tax payer registration number (CPF or CNPJ) associated with the shipments.
  - `pageNo` (Integer, optional, default: 0): The page number of the results to retrieve.
  - `pageSize` (Integer, optional, default: 3): The number of shipments per page.

  #### Example Request

  `GET /api/shipments?taxPayerRegistrationNo=12345678901&pageNo=0&pageSize=10`

  #### Example Response

  ```json
  {
    "content": [
      {
        "trackingNumber": "123e4567-e89b-12d3-a456-426614174000",
        "taxPayerRegistrationNo": "12345678900",
        "senderAddress": {
          "streetName": "Rua do remetente",
          "neighbourhood": "Bairro do remetente",
          "city": "Cidade do remetente",
          "stateCodeEnum": "SP",
          "addressNumber": "456",
          "zipCode": "98765432"
        },
        "recipientAddress": {
          "streetName": "Rua do destinatário",
          "neighbourhood": "Bairro do destinatário",
          "city": "Cidade do destinatário",
          "stateCodeEnum": "RJ",
          "addressNumber": "789",
          "zipCode": "98765432"
        },
        "value": 100.00,
        "weight": 10,
        "cubingMeasurement": 2.5
      }, ... // other elements would be here
    ],
    "pageNo": 0,
    "pageSize": 3,
    "totalPages": 1,
    "totalElements": 1,
    "last": true
  }

</details>

<details>
<summary><h2>Endpoint: Search Shipments</h2></summary>

- **URL**: `/api/shipments/search`
- **HTTP Method**: GET
- **Description**: This endpoint allows you to search for shipments using a keyword.

  #### Query Parameters

  - `keyword` (String, required): The keyword to search for within shipment information.
  - `pageNo` (Integer, optional, default: 0): The page number of the results to retrieve.
  - `pageSize` (Integer, optional, default: 3): The number of shipments per page.

  #### Example Request

`GET /api/shipments/search?keyword=tracking&pageNo=0&pageSize=10`


#### Example Response

```json
{
    "content": [
      {
        "trackingNumber": "123e4567-e89b-12d3-a456-426614174000",
        "taxPayerRegistrationNo": "12345678900",
        "senderAddress": {
          "streetName": "Rua do remetente",
          "neighbourhood": "Bairro do remetente",
          "city": "Cidade do remetente",
          "stateCodeEnum": "SP",
          "addressNumber": "456",
          "zipCode": "98765432"
        },
        "recipientAddress": {
          "streetName": "Rua do destinatário",
          "neighbourhood": "Bairro do destinatário",
          "city": "Cidade do destinatário",
          "stateCodeEnum": "RJ",
          "addressNumber": "789",
          "zipCode": "98765432"
        },
        "value": 100.00,
        "weight": 10,
        "cubingMeasurement": 2.5
      }, ... // other elements would be here
    ],
    "pageNo": 0,
    "pageSize": 3,
    "totalPages": 1,
    "totalElements": 1,
    "last": true
  }
```

</details>

<details>
<summary><h2>Endpoint: Update Shipment Information Status by Tracking Number</h2></summary>

- **URL**: `/api/shipments/{trackingNo}/status`
- **HTTP Method**: PATCH
- **Description**: This endpoint allows you to update the status of a shipment using its tracking number.

  #### Path Parameters

  - `trackingNo` (UUID, required): The unique identifier of the shipment.

  #### Request Body

  The request body should be a JSON object containing the following field:

  - `shipmentStatusEnum` (String, required): The new status of the shipment. Possible values are `POSTED`, `IN_TRANSIT`, `DELIVERED`, `FAILED_TO_DELIVER`, `RETURNING_TO_SENDERS`.

  #### Example Request Body

  ```json
  {
    "shipmentStatusEnum": "IN_TRANSIT"
  }
  ```

Example Response

   - **Status Code**: 200 OK
   - **Description**: The shipment status was successfully updated.

```json
{
  "message": "Shipment status for the shipment of tracking number {trackingNo} has been successfully updated"
}
```

   - **Status Code**: 404 Not Found
   - **Description**: The shipment with the specified tracking number was not found.

```
json
{
  "error": "Shipment with tracking number {trackingNo} not found"
}
```
</details>


<details>
<summary><h2>Endpoint: Update Shipment Recipient Address by Tracking Number</h2></summary>

- **URL**: `/api/shipments/{trackingNo}/recipientAddress`
- **HTTP Method**: PATCH
- **Description**: This endpoint allows you to update the recipient's address of a shipment using its tracking number.

#### Path Parameters

- `trackingNo` (UUID, required): The unique identifier of the shipment.

#### Request Body

The request body should be a JSON object containing the following fields:

- `streetName` (String, required): The street name of the recipient's address.
- `neighbourhood` (String, required): The neighborhood of the recipient's address.
- `city` (String, required): The city of the recipient's address.
- `stateCodeEnum` (String, required): The state code of the recipient's address.
- `addressNumber` (String, required): The address number of the recipient's address.
- `zipCode` (String, required): The ZIP code of the recipient's address.

#### Example Request Body

```json
{
  "streetName": "Rua nova",
  "neighbourhood": "Bairro novo",
  "city": "Cidade nova",
  "stateCodeEnum": "AC",
  "addressNumber": "456",
  "zipCode": "98765432"
}
```

#### Example Response

- **Status Code**: 200 OK
- **Description**: The recipient address of the shipment was successfully updated.

```json
{
  "message": "Recipient address for the shipment with tracking number {trackingNo} has been successfully updated"
}
```

- **Status Code**: 404 Not Found
- **Description**: The shipment with the specified tracking number was not found.

```json
{
  "error": "Shipment with tracking number {trackingNo} not found"
}
```

</details>

<details>
<summary><h2>Endpoint: Delete Shipment Information by Tracking Number</h2></summary>

- **URL**: `/api/shipments/{trackingNo}`
- **HTTP Method**: DELETE
- **Description**: This endpoint allows you to delete a shipment using its tracking number.

#### Path Parameters

- `trackingNo` (UUID, required): The unique identifier of the shipment to be deleted.

#### Example Request

```
DELETE /api/shipments/{trackingNo}
```

#### Example Response

```json
{
  "message": "Shipment for the shipment of tracking number {trackingNo} has been successfully deleted"
}
```

- **Status Code**: 200 OK
- **Description**: The shipment with the specified tracking number was successfully deleted.

```json
{
  "error": "Shipment with tracking number {trackingNo} not found"
}
```

- **Status Code**: 404 Not Found
- **Description**: The shipment with the specified tracking number was not found.

</details>

