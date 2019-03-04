compile & build jar
===================
mvn clean package

Build docker image
===================
docker-compose build
docker-compose up

curl http://localhost:8080/

Mixer Challenge Solution
=========================
1. Create a service to get deposit address
2. Move funds to house address
3. Run the mixer
4. Move the coin from mixer address to gateway address
4. Return the coins (from gateway address) to deposit addresses



Create coin using
=================
POST: http://jobcoin.projecticeland.net/harsh/create
address:BobsAddress
amount:11111111


Test
====
curl http://localhost:8080/depositAddress/M6-11,M3-12
curl http://localhost:8080/depositAddress/M6-13,M3-14,M3-15
curl http://localhost:8080/depositAddress/M6-16,M3-17
curl http://localhost:8080/depositAddress/M6-18,M3-19
curl http://localhost:8080/depositAddress/M6-110,M3-111
curl http://localhost:8080/depositAddress/M6-112,M3-13
curl http://localhost:8080/depositAddress/M6-114,M3-115
curl http://localhost:8080/depositAddress/M6-116,M3-117
curl http://localhost:8080/depositAddress/M6-118,M3-119
curl http://localhost:8080/depositAddress/M6-119,M3-120


curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress999&amount=9" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress998&amount=8" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress997&amount=7" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress996&amount=6" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress995&amount=5" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress994&amount=4" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress993&amount=3" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress992&amount=2" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress991&amount=1" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions
curl -d "fromAddress=BobsAddress&toAddress=4DepositAddress990&amount=10" -X POST http://jobcoin.projecticeland.net/harsh/api/transactions

