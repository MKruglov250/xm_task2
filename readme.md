This is a framework for XM UI Test Automation task

To run:

1. Pull from Github
2. Maven > Clean > Test OR Run from testng.xml

Comments:
1. Added Surefire report for base reporting. I'd like Allure, but it's not part of task stack - so I decided to put it off.
2. Didn't make complete POM with Business Steps as the task is relatively small, decided to minimize the code and classes.
3. Added Retry to deal with test flakiness: single stock page doesn't open sometimes

Works on my PC - hope it will work on yours :)