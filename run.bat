@echo off
if not exist out mkdir out
javac -d out src\InteractiveQuiz.java
if errorlevel 1 pause & exit /b
java -cp out InteractiveQuiz
pause
