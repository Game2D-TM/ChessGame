# ♟️ 2D Java Chess Game

A fully functional 2D Chess game built from scratch in Java using only AWT & Swing. No engines, no frameworks — just pure code. This was a personal learning project developed during my second year of university to deepen my understanding of game loops, AI, and OOP design.

---

## 🚀 Features

- Full implementation of official chess rules:
  - Castling
  - Check / Checkmate detection
  - Pawn promotion
- Rule-based AI (no algorithm, self-designed logic)
- Turn-based game system with promotion and win conditions
- Fully custom game loop targeting 144 FPS
- UI built entirely with AWT and Swing
- Encoded image assets (`.dat` files using Base64) for protection
- OOP-based architecture with modular components

---

## 🧠 AI System

The AI simulates basic human decision-making using a priority system:

- Prioritizes safe captures of high-value pieces
- Evaluates enemy threat range before making moves
- Attempts castling and pawn promotion when available
- Selects fallback random safe moves when no captures possible

> All logic was written from scratch without using Minimax or prebuilt libraries.

---

## 🧱 Core Implements

- **2D Chessboard Engine** using 2D array logic
- **Object-Oriented Piece Classes** (`Pawn`, `Knight`, `King`, etc.)
- **Frame-Based Custom Game Loop** using `System.nanoTime()`
- **Manual Rule Handling** with internal logic (no game engine)
- **Encoded Asset Loader**: images stored in `.dat` files as Base64
- **Input System** via AWT MouseListeners

---

## 📊 Quick Stats

|                     |                            |
|---------------------|----------------------------|
| **Dev Time**        | ~3 weeks                   |
| **Type**            | 2D Chess Simulation        |
| **Systems**         | 20+                        |
| **Built By**        | 100% Solo                  |
| **Graphics**        | Custom AWT + Swing         |
| **Engine**          | None (Pure Java)           |
| **Architecture**    | Object-Oriented & Modular  |
| **Codebase**        | Medium-scale               |
| **Audio**           | No                         |
| **Asset Handling**  | Base64-encoded `.dat` files|

---

## 🧩 Challenges & Solutions

### 🟥 Challenge:  
Creating a responsive and consistent game loop without using any engine.

### 🟩 Solution:  
Built a custom frame-timed loop with `System.nanoTime()` + `Thread.sleep()` to maintain stable FPS and smooth rendering.

---

### 🟥 Challenge:  
Building a rule-following chess AI without using algorithms like Minimax.

### 🟩 Solution:  
Designed a rule-based AI that evaluates threats, prioritizes captures, and uses fallback logic for safe movement decisions.

---

## 📦 Tech Stack

- Java 8+
- AWT / Swing
- No external libraries or frameworks

---

## 📷 Screenshots

# Main Menu
![alt text](https://github.com/Game2D-TM/ChessGame/blob/main/Examples/menu.png?raw=true)

# Ingame 1
![alt text](https://github.com/Game2D-TM/ChessGame/blob/main/Examples/Ingame_1.png?raw=true)

# Ingame 2
![alt text](https://github.com/Game2D-TM/ChessGame/blob/main/Examples/Ingame_2.png?raw=true)

---

## 🎞️ Demo

-> Youtube: https://www.youtube.com/watch?v=G6sk4dqY0ks

---

## 📜 License

This project is for educational purposes. Free to use and modify.

---

## 🧑‍💻 Author
Made by Ton Minh – Check out more at: https://ikminhton.github.io

---
