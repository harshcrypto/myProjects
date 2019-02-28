compile & build jar
===================
mvn clean package

Build docker image
===================
docker-compose build
docker-compose up

curl http://localhost:8080/

Mixer Challenge
===============
1. Create a service to get deposit address
2. Move funds to house address
3. Run the mixer
4. Return the coins on deposit addresses



Create coin using
=================
POST: http://jobcoin.projecticeland.net/harsh/create
address:BobsAddress
amount:11111111
