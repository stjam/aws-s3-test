**Steps to run the service:**
    
1. cd to the folder where you unpack zip archive
    
2. run in console 


    mvn clean package
    
3. run in console 
    if desire to start up in detached mode
         
    
    docker-compose up -d
otherwise
        
    docker-compose up
in all cases --build parameter may be ignored

4. cd to output build folder 

    
    cd .\ImageCatalogService\target

6. Run app

    
    java -jar .\ImageCatalogService.jar




