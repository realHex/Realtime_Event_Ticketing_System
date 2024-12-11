# 🎟️ Realtime Event Ticketing System Simulation
Realtime Event Ticketing System that simulates vendors and customers

<br>

<hr>

## 📖 Overview

This application simulates how Vendors add tickets and Customers purchase 
tickets from a shared ticketpool.
The multi-threading is handled using core Java.

---

## ✨ Features

- Sets system parameters needed for the program.
- Shows ticket status and transaction logs.
- Logs transactions and configurations in real time.
- Provides clear sections for status and control.
- Includes input fields, configuration, and control buttons.
- Alerts for invalid user inputs and real-time updates.
- Polls backend to fetch and display updates.
- Implements multi-threaded vendor and customer operations.
- Ensures thread safety with ReentrantLock.
- Manages tickets with thread-safe structures.
- Handles ticket additions and purchases.
- REST APIs for frontend-backend communication.
- Adds priority ticket access for VIP customers.
- Ability to add vendors/customers at runtime.
- Displays ticket sales with real-time updates using charts.
- Stores transaction data in a database.

---

## 🚀 Getting Started

### ✅Prerequisites

**Java Development Kit (JDK) 🛠️**  : `Version: ^21.0.2` </br>
**Node.js** : `Version: Latest` </br>
**npm 📦** 	: `Version: >= 8.x.x` </br>
**Angular CLI 🅰️** : `Version: "^18.2.0"` </br>
**Maven**: `Version: ^3.9.9` </br>
**MongoDB** : `Version: any`

### Installation

#### Build the project from source:


1. Navigate to the project directory:
```
❯ cd Realtime_Event_Ticketing_System
```

#### Backend Installation

1. Navigate to the backend directory:
```
❯ cd TicketingSystem-Back
```
2. Install dependencies and build the project:
```
❯ ./mvnw clean install
```

#### Frontend Installation

1. Navigate to the frontend directory:
```
❯ cd TicketingSystem-Front
```
2. Install dependencies:
```
❯ npm install
```



### 📘 Usage

To run the project, execute the following command:

#### Backend

1. Navigate to the backend directory:
```
❯ cd TicketingSystem-Back
```
2. Run the backend:
```
❯ ./mvnw spring-boot:run
```

#### Frontend Usage

1. Navigate to the frontend directory:
```
❯ cd TicketingSystem-Front
```

2. Run the frontend development server:

```
❯ ng serve
```

---

## 📚 API Documentation

---

###  Configuration

1. **Load Configuration**
    - **Endpoint**: `GET /api/configuration/load-configuration`
    - **Description**: Retrieves the current configuration settings.
    - **Response**:
      ```json
      {
         "totalTickets": 50,
         "ticketReleaseRate": 20,
         "customerRetrievalRate": 4,
         "maxTicketCapacity": 100
      }

2. **Save Configuration**
    - **Endpoint**: `POST /api/configuration/save-configuration`
    - **Description**: Saves user input.
    - **Request Body**:
      ```json
      {
        "totalTickets": 50,
        "ticketReleaseRate": 1,
        "customerRetrievalRate": 2,
        "maxTicketCapacity": 100
      }
      ```

---

### System

1. **Start System**
    - **Endpoint**: `POST /api/system/start`
    - **Description**: Starts the system.

2. **Stop System**
    - **Endpoint**: `POST /api/system/stop`
    - **Description**: Stops the system.

3. **Reset System**
    - **Endpoint**: `POST /api/system/reset`
    - **Description**: Resets the system.

4. **Get System State**
    - **Endpoint**: `GET /api/system/state`
    - **Description**: Retrieves the system state.
    - **Response**: "RUNNING","PAUSED","STOPPED"

---

### Customer

1. **Add Customer**
    - **Endpoint**: `POST /api/customer/add-customer`
    - **Description**: Adds a customer.

2. **Remove Customer**
    - **Endpoint**: `DELETE /api/customer/remove-customer`
    - **Description**: Removes a customer.

3. **Get Customer Count**
    - **Endpoint**: `GET /api/customer/get-customer`
    - **Description**: Gets total customers in the system.
    - **Response**: ex: 5 

4. **Add Priority Customer**
   - **Endpoint**: `POST /api/customer/add-priority-customer`
   - **Description**: Adds a priority customer.

5. **Remove Priority Customer**
   - **Endpoint**: `DELETE /api/customer/remove-priority-customer`
   - **Description**: Removes a priority customer.

6. **Get Priority Customer Count**
   - **Endpoint**: `GET /api/customer/get-priority-customer`
   - **Description**: Gets total priority customers in the system.
   - **Response**: ex: 3

---

### Vendor

1. **Add Vendor**
   - **Endpoint**: `POST /api/vendor/add-vendor`
   - **Description**: Adds a vendor.

2. **Remove Vendor**
   - **Endpoint**: `DELETE /api/vendor/remove-vendor`
   - **Description**: Removes a vendor.

3. **Get Vendor Count**
   - **Endpoint**: `GET /api/vendor/get-vendor`
   - **Description**: Gets total vendors in the system.
   - **Response**: ex: 8

---

### Ticket Pool

1. **Get Total Tickets**
    - **Endpoint**: `GET /api/ticketpool/total-tickets`
    - **Description**: Gets total tickets currently in the ticket pool.
    - **Response**: ex: 76

2. **Get Purchased Tickets**
   - **Endpoint**: `GET /api/ticketpool/purchased-tickets`
   - **Description**: Gets total tickets purchased by all customers.
   - **Response**: ex: 42

3. **Get Added Tickets**
   - **Endpoint**: `GET /api/ticketpool/added-tickets`
   - **Description**: Gets total tickets added by all customers.
   - **Response**: ex: 24

4. **Get Max Ticket Capacity**
   - **Endpoint**: `GET /api/ticketpool/max-ticket-capacity`
   - **Description**: Gets max ticket capacity of the system.
   - **Response**: ex: 31

---

### Logs

1. **Get Logs**
   - **Endpoint**: `GET /api/logs/get-logs`
   - **Description**: Gets the list of transaction logs
   - **Response**:
   ```json
      {
        "time": "2024/12/11 20:08:02",
        "actor": "Vendor 1",
        "action": "added 20 tickets",
        "ticketPoolSize": "Ticket Pool = 70"
      }
      ```
---

## 🙏 Acknowledgements

I would like to thank my mom, dad, and sister for aiding me in a lot of ways during the period
of me doing this coursework.



---
