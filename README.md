-------------
Dependencies:
-------------
1. Download Amazon DynamoDB local client from the link below
Link: <b> <u> https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html#DynamoDBLocal.DownloadingAndRunning </u></b><br>
2. cd /Users/swetha/Downloads/dynamodb_local_latest <br>
Run Dynamo DB local client using the command below  <br>
swetha-macbook-pro$<b> java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb </b> <br>
Initializing DynamoDB Local with the following configuration: <br>
Port: 8000 <br>
InMemory: false <br>
DbPath: null <br>
SharedDb: true <br>
shouldDelayTransientStatuses: false <br>
CorsParams: *  <br>
3. Move to a different terminal <Br>
4. Using the command below, create a table UrlMap with a String attribute AttributeName=id with keyType=Hash <br> 
swetha-macbook-pro$<b> aws dynamodb create-table --table-name UrlMap --attribute-definitions AttributeName=id,AttributeType=S  --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --endpoint-url http://localhost:8000 </b> <br>
{ <br>
    "TableDescription": {
        "TableArn": "arn:aws:dynamodb:ddblocal:000000000000:table/UrlMap",<br> 
        "AttributeDefinitions": [
            {
                "AttributeName": "id", 
                "AttributeType": "S"
            }
        ], 
        "ProvisionedThroughput": {
            "NumberOfDecreasesToday": 0, 
            "WriteCapacityUnits": 1, 
            "LastIncreaseDateTime": 0.0, 
            "ReadCapacityUnits": 1, 
            "LastDecreaseDateTime": 0.0
        }, 
        "TableSizeBytes": 0, 
        "TableName": "UrlMap", 
        "TableStatus": "ACTIVE", 
        "KeySchema": [
            {
                "KeyType": "HASH", 
                "AttributeName": "id"
            }
        ], 
        "ItemCount": 0, 
        "CreationDateTime": 1524638450.506
    } <br>
} <br>

------------------
Running the spring boot application
------------------ <br>
To compile , issue following commands in the current directory <br>
-1. mvn compile <br>
-2. mvn package <br>
-3. mvn install <br>

This should create a jar file in target directory.
<b>java -jar target/cmpe282-0.0.1-SNAPSHOT.jar</b> <br>

Please verify that various endpoints are registered, displayed and spring application runs 
