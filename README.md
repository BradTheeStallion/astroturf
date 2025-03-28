[![JUnit Tests](https://github.com/BradTheeStallion/astroturf/actions/workflows/mavenTest.yml/badge.svg)](https://github.com/BradTheeStallion/astroturf/actions/workflows/mavenTest.yml)

# AstroTurf<br>
## QAP2 SDAT DEVOPS<br>
[Assignment Instructions](https://github.com/user-attachments/files/19438425/S4.Winter.2025.QAP.2.pdf)

This project is primarily a Java backend, configuring a REST API to interact with a MySQL database. The project is also Dockerized for ease of deployment.<br>
The app is configured to run on localhost:8080 when deployed locally (instructions below), but please feel free to check out the deployed version on my [portfolio](https://bradtheestallion.github.io/portfolio).<br>
Please accept my appologies for any lagging on the live version. Free database and API hosting comes with a price.<br>
If you want to test the deployed version on Postman, the base url is https://astroturf.onrender.com (see the endpoint guide in step 3 below)

## Instructions for Local Deployment<br>
1: [Clone the repository in IntelliJ](https://www.jetbrains.com/guide/tips/clone-from-github/).

2: Run the following docker commands:

&nbsp;&nbsp;&nbsp;&nbsp;*Build the docker containers*<br>
&nbsp;&nbsp;&nbsp;&nbsp;docker-compose build<br>
&nbsp;&nbsp;&nbsp;&nbsp;*Start the application*<br>
&nbsp;&nbsp;&nbsp;&nbsp;docker-compose up -d

3: Try some of these [API endpoints](https://github.com/user-attachments/files/19439234/AstroTurf.API.Documentation.pdf) out in [Postman](https://www.postman.com/downloads/).

4: Stop the app with the following command:<br>
&nbsp;&nbsp;&nbsp;&nbsp;docker-compose down

## <ins>Postman Photoshoot</ins>

### Add Member<br>
<img width="1279" alt="Screenshot 2025-03-24 at 7 59 05 PM" src="https://github.com/user-attachments/assets/8ac5cc8c-e43b-49d3-9965-08390a57b7dc" />

### Add Tournament<br>
<img width="1278" alt="Screenshot 2025-03-24 at 8 07 15 PM" src="https://github.com/user-attachments/assets/504dad16-8ae5-4c91-a9bd-7f1b9621ec14" />

### Add Members to Tournament
<img width="1275" alt="Screenshot 2025-03-24 at 8 10 25 PM" src="https://github.com/user-attachments/assets/e6b518d6-3283-4624-ad3c-40f1a6ad64ac" />

### Search Members by Tournament
<img width="1277" alt="Screenshot 2025-03-24 at 8 11 31 PM" src="https://github.com/user-attachments/assets/0978384c-dc7e-43ef-9de7-312d2123c007" />
