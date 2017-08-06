# Promeets

**Service for organizing meetings. Service to make you for effective.**

![Promeets image](architecture/general/promeets_main.png)

## Get started

[Presentation](architecture/general/Promeets_presentation_1.pptx)

### Build and run

* git clone https://github.com/zatsepinvl/promeets
* cd promeets

#### Local run

* mvn clean install
* cd src/main/docker/env
* docker-compose up --build
* run promeets-*VERSION*.war on server with the following parameters:
    * POSTGRES_DB - default: `promeets_db`
    * POSTGRES_URL - ex. `192.168.99.100:5432`
    * POSTGRES_USER - defualt: `pmadmin`
    * POSTGRES_PASSWORD - default: `admin`
    
#### Docker
* mvn clean install
* cd target
* docker-compose up --build