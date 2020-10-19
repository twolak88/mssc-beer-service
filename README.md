[![CircleCI](https://circleci.com/gh/twolak88/mssc-beer-service.svg?style=svg&circle-token=d4f359a636bc1ac3105be9fe5e47574aa7d94275)](https://app.circleci.com/pipelines/github/twolak88/mssc-beer-service?branch=master)

# mssc-beer-service
My MSSC Beer Service 

# Default Port Mappings - For Single Host
| Service Name | Port | 
| --------| -----|
| Brewery Beer Service | 8080 |
| [Brewery Beer Order Service](https://github.com/twolak88/mssc-beer-order-service) | 8081 |
| [Brewery Beer Inventory Service](https://github.com/twolak88/mssc-beer-inventory-service) | 8082 |

Docker commands\
docker run --name tw-mysql -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -v ~/prog/docker/data/mysql:/var/lib/mysql -p 3306:3306 --cap-add sys_nice -d mysql\
docker run -it --rm -p 8161:8161 -p 61616:61616 vromero/activemq-artemis