## Setup
    
   
    mvn clean install
   
then run app in your IDE 

## Profiles
**loader** - Loads data from csv file

## Properties
**data.warehouse.file.name** - You can define which file to load, **data.csv** and **data_light.csv** are available.
Loading with **data.csv** lasts few minutes, so at the beginning I'd recommend running with **data_light.csv** and after first tests run application with **data.csv**.

## Comments
    <configuration>
    	<target>
    		<sleep seconds="7"/>
    	</target>
    </configuration>
    
Config above has been added due to give time Elasticsearch to up container

## REST API Docs

http://localhost:8029/swagger-ui.html

