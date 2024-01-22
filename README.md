# receipt-processor-challenge
This is a repo for challenge

### Step 1: Build image
Run below command to build the image

`docker build --tag receipt-processor`

### Step 2: Run containers
Run below command to run the container

`docker run --publish 3007:3007 receipt-processor`

If not succeed, run `docker images` to check if receipt-processor is in the list

When you see `Started ReceiptProcessorApplication in 1.072 seconds `, that means it succeed

### Step 3: Test in Swagger
Open `http://localhost:3007/swagger-ui/index.html#/` in your browser, and you will see two api endpoints in the list.

Now you can start to play with it!