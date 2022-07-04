1. Get image from service

For first test image:

    curl --location --request GET 'http://localhost:9191/api/v1/image/show/thumbnail/dept-blazer?reference=f3adb4e8707c768b3dd00c7f1ecb3080.jpg' --data-raw ''

For second test image:

    curl --location --request GET 'http://localhost:9191/api/v1/image/show/thumbnail/dept-blazer?reference=490.jpg' --data-raw ''

2. Flush image from S3 
   
   
    curl --location --request GET 'http://localhost:9191/api/v1/image/flush/thumbnail/dept-blazer?reference=490.jpg' --data-raw ''