v1.0.0 :: Jan 05 2017
======================
* Created AWS Lambda function handler that allows the execution of the `kanaConvert` function from [kanatools-java](https://github.com/mariten/kanatools-java) via serverless computing
    * Will be used to power a live demo of `kanaConvert`
* Wrote basic set of utility helper functions for reading in Lambda-Proxy formatted API Gateway requests, and fetching parameter values from its JSON structure
* Handle HTTP status codes in return value
    * `200` for success, `400` if invalid parameters were received, or `500` if unexpected exceptions occur
