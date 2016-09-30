# _Animal Sighting Tracker_

#### _Allows forest rangers to keep track of animal sightings._

#### By _**Joanna Anderson**_

## Description

_This application will allow forest rangers to keep track of animal sightings. It will record the location of the sighting in addition to animal information such as whether the animal is endangered or not.._

## Installation Requirements

1. Go to [my GitHub](https://github.com/jsaerom/forest)
2. Click Clone or download and choose either the Open in Desktop option or Download Zip option
**OR**
1. Open Terminal
2. Command $ git clone https://github.com/jsaerom/forest

In PSQL:
1. CREATE DATABASE wildlife_tracker;
2. CREATE TABLE animals (id serial PRIMARY KEY, name varchar, endangered boolean, age varchar, health varchar);
3. CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, date timestamp, rangerid int);
4. CREATE TABLE rangers (id serial PRIMARY KEY, name varchar, rangernumber varchar, email varchar);
5. CREATE TABLE rangers_sightings (id serial PRIMARY KEY, sighting_id int, ranger_id int);
6. CREATE TABLE animals_sightings (id serial PRIMARY KEY, animal_id int, sighting_id int);
7. Run the following command: $ psql media < media.sql

## Technologies Used

* _Java_
* _Spark_
* _HTML_
* _Velocity Template Engine_
* _CSS_
* _Bootstrap_

### License

Copyright (c) 2016 **_Joanna Anderson_**
