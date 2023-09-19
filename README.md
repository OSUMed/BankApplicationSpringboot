# Assignment 13 - Spring Boot Application

## Table of Contents

1. [Introduction](#introduction)
2. [Folder Structure](#folder-structure)
3. [Features](#features)
   - [Endpoints](#endpoints)
4. [Installation](#installation)
5. [Usage](#usage)

## Introduction

This is a Spring Boot application designed to manage users and their corresponding accounts and addresses. It showcases relationships between entities and provides CRUD (Create, Read, Update, Delete) functionalities.

## Deployed Site: 
https://bankapplication.up.railway.app/users

   To Add Users: https://bankapplication.up.railway.app/register
   
   To Update Users: Click on a user and access users' address and account details

## Project Gif Preview
![Kapture 2023-09-19 at 12 36 38](https://github.com/OSUMed/srikanth-assignment13/assets/62990411/f4c837b5-557c-4182-b0ab-c6a61c70ffa1)

## Folder Structure

<img width="270" alt="image" src="https://github.com/OSUMed/srikanth-assignment13/assets/62990411/0389a57f-a03e-41dc-891a-aac0184678e4">

## Features

## Technologies Used:
- SpringBoot, Java, Thymeleaf, SQL, Hibernate

### Service Layer Functionalities

#### Account Management
- **Total Account Calculation**: Utilizes a custom query to compute the aggregate number of accounts.
- **Account Modification with Many-to-Many Relationships**: Provides both updating and addition of new accounts, while maintaining many-to-many relationships with users.
- **Account Detail Retrieval**: Enables the fetching of individual account details.
- **Dynamic Account Number Assignment**: Implements automatic generation of new account numbers based on the highest existing account number.

#### User Management
- **Comprehensive User Listing with Associations**: Lists users along with their 1-to-1 associated addresses and many-to-many associated accounts.
- **Initial Account Allocation for New Users**: Automatically sets up initial 'Checking' and 'Savings' accounts for new users and establishes many-to-many relationships with them.
- **User and Address Modification**: Supports updating and deletion of users, along with their 1-to-1 associated addresses, while preserving many-to-many relationships with accounts.

#### General Features
- **Form-Based Operations**: All user interactions are facilitated through HTML forms, with no JavaScript involved.

### API Endpoints

#### POST `/register`
- **User Registration**: Allows for the registration of new users through the UI.
  
#### GET `/users`
- **User Listing with Associations**: Shows all registered users, along with their login credentials, 1-to-1 associated addresses, and clickable many-to-many associated accounts.
  - **Associated Service Method**: `UserService.findAll()`

#### GET, PUT, DELETE `/users/{user_id}`
- **User Detail Operations**: 
  - **GET**: Retrieves user details and their many-to-many associated accounts.
  - **PUT**: Updates user and 1-to-1 address details while maintaining many-to-many associations with accounts.
  - **DELETE**: Deletes a user and their 1-to-1 address, but keeps the many-to-many relationships with accounts intact.
  - **Associated Service Methods**: `UserService.findById()`, `UserService.saveUser()`, `UserService.delete()`
  
#### GET, PUT `/users/{user_id}/accounts/{account_id}`
- **Account Detail Operations**: 
  - **GET**: Enables fetching of specific account details.
  - **PUT**: Allows for the updating of account details, including name modification.
  - **Associated Service Methods**: `AccountService.getAccount()`, `AccountService.saveAccount()`


