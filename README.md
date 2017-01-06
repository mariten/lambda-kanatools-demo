Lambda Live Demo of Kana Tools for Java
=======================================
Code Powering the Live Demo of "Kana Tools for Java" on AWS Lambda and API Gateway

## Features
* Requires Java 8
* Live API is hosted on "serverless" architecture using Amazon AWS **Lambda** and **API Gateway**
* All information about HTTP requests made to API Gateway is passed via a **Lambda Proxy** JSON string `InputStream` to a function handler as which is executed on AWS Lambda

## Build
After cloning this repository, package is built using the Gradle wrapper

    cd lambda-kanatools-demo
    ./gradlew jar

After building the `jar`, it can then be uploaded to AWS Lambda as-is

# Endpoints
## `convertKana`
#### API Gateway Path
    GET /kana/convert

#### Lambda Handler
    com.mariten.lambda.ConvertKanaDemo::handleLambdaRequest

#### Query Params
| Name               | Type         | Description                                             | Required? |
| ------------------ |:------------:| ------------------------------------------------------- |:---------:|
| **`input_str`**    | String       | UTF-8 string to convert                                 | Yes       |
| **`conv_ops`**     | Positive Int | One or more flags defining which conversions to perform | Yes       |
| **`ignore_chars`** | String       | No characters in this string will be converted          | No        |

#### Result Format
```
{
    "success": true,
    "output_str": "<converted result string>"
}
```
