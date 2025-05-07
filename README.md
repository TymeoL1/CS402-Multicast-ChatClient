# CS402-Multicast-ChatClient

A simple standalone Java chat client using UDP multicast, created for **CS402 Assignment 4**.

This application allows multiple users on the same network to send and receive messages in real time via multicast communication. Each instance runs independently without needing a central server.

---

## Features

- Prompts for username on startup
- Displays a welcome message (`Hello <username>!`)
- Sends and receives text messages over multicast (IP: `224.2.2.3`, Port: `8000`)
- Filters out empty messages
- Uses two threads: one for sending, one for receiving
- Displays messages in the format: `Username : Message`
- Typing `/exit` will quit the program

---

## How to Run

1. Compile the Java source file:

   ```bash
   javac ChatClient.java
