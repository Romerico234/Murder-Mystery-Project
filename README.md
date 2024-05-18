# Text-Based Storyline Interactive Game

A husband and wife were murdered. It is up to you to explore the crime scene, find evidence, investigate clues, and reach a conclusion.

## Download Project
$ git clone https://github.com/Romerico234/Murder-Mystery-Project.git

## Run in terminal
$ java -jar Murder-Mystery-Project/app/app.jar

## Project Details
**Background:**

This was my final project for COSC 236 at Towson University for Dr. Dastyni Loksa along with my partner Michael Reifer. The project's first version, which can be seen in older commits, shows very basic coding skills and minimal use of object-oriented programming principles. This led me to completely refactor the codebase to use more OOP concepts. This project uses all 4 fundamental OOP concepts: Abstraction, Encapsulation, Inheritance, and Polymorphism.

**Credit:**
- *Older Version:* Collaborated with Michael Reifer where we developed the program and storyline. Utilized starter code provided by Dr. Dastyni Loksa.
- *Newer Version:* This version was done completely independently. I slighlty changed the original storyline (clarified and used different clues). I only utilized the helper methods and the game loop functionality from the starter code Dr. Loksa provided.


Next, I will explain how to reach the endings. I am including this section because reaching the conclusion requires you to interpret the clues. If you have the time, I highly recommend skipping this part until you have gotten stuck or lost.

## Endings
This game offers two possible conclusions:
1. Collect all evidence (straightforward).
2. Deciphering the code to the safe.

The first ending requires knowing the game mechanics. I will discuss the intuition behind the code for the safe.

## How to determine the 8-digit code to the safe:

### Evidence Pieces
- Safe
- Letter
- Note
- Chess Board
- Crime Report

At first glance, the 8-digit code may seem like one of the birth dates of our characters:

Richard Jr's Birthday: 07 30 2007
Joseph's Birthday: 01 22 2005
Uncle Bob's Birthday: 02 03 1975
However, the real clues lie in:

But, you will quickly realize that this is wrong.

Let's consider the evidence:

### Note:
The note says "rth month"
No tricks here, the the entire phrase is supposed to be "Birth Month"

### Letter: 
aye i need you to do a 
lil favor for me. Yo
pops been pressing me about working so i 
have to go job hunting this 
afternoon. do my laundry while im out 
buddy. ill come back with ur favorite
ensaimadas
thanks 

The first letter of each line spells "alphabet"

### Chess board with pawns on A1 and B2:
The letter a is the first letter in the alphabet and the letter b is the second letter on the alphabet

So, to crack the clues, consider the birth months of the characters and the alphabetical positions of each letter:

Richard Jr's Birth Month: July
J = 10
U = 21
L = 12
Y = 25

Thus, the code 10211225 corresponds to Richard Jr's birth month encrypted based on the position of each letter of July in the alphabet.
