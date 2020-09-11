# EXCHANGE RATE CONVERTER




#### _Maven_

build project



`mvn clean package `



#### _Docker_

build image

`docker build -t @username/exchange-rate .`

run image

`docker run -p 8080:8080 @username/exchange-rate`

#### _Tests_

**convert money**

`curl --location --request GET 'http://localhost:8080/exchange-rate?currencyFrom=PEN&currencyTo=USD&amount=3.45' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjMmFmZDQyZi0zNjNjLTQ1MmQtOTdiYy05NzRmYjkzMmRiYzYiLCJzdWIiOiJqb2giLCJyb2wiOiJVU0VSIiwiaWF0IjoxNTk5ODI5MDg1fQ.m204K1vGHbgtufBHFjFOwhsTSNbWHdZEtPGEnEKF1OYlvzJitHJ9m-gKgLYXAyj18duY9Ft4ZlnN-tWEJNncWQ'
`

**update exchange rate**

`curl --location --request POST 'http://localhost:8080/exchange-rate' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJjMmFmZDQyZi0zNjNjLTQ1MmQtOTdiYy05NzRmYjkzMmRiYzYiLCJzdWIiOiJqb2giLCJyb2wiOiJVU0VSIiwiaWF0IjoxNTk5ODI5MDg1fQ.m204K1vGHbgtufBHFjFOwhsTSNbWHdZEtPGEnEKF1OYlvzJitHJ9m-gKgLYXAyj18duY9Ft4ZlnN-tWEJNncWQ' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 10002,
    "amount": 3.5
}'`




**(Optional) push image**

`docker push @username/exchange-rate`


