@echo off
dir /s /B *.java > sources
javac @sources
java rbit.Main