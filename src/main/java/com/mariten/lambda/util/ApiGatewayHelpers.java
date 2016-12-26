package com.mariten.lambda.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

public class ApiGatewayHelpers
{
    public static JsonObject parseRequest(InputStream api_request, LambdaLogger logger)
    {
        BufferedReader request_reader = null;
        try {
            request_reader = new BufferedReader(new InputStreamReader(api_request));
            // On Success, return parsed JSON structure
            return (JsonObject) Jsoner.deserialize(request_reader);

        } catch (IOException io_ex) {
            logger.log("[EXCEPTION] Error while reading stream: " + io_ex.getMessage());
        } catch (DeserializationException parse_ex) {
            logger.log("[EXCEPTION] Error parsing request JSON: " + parse_ex.getMessage());
        } catch (Exception ex) {
            logger.log("[EXCEPTION] Unexpected error: " + ex.getMessage());
        }

        // On Failure, return null after catching exception
        if (request_reader != null) {
            logger.log("    Failed request: " + request_reader.toString());
        }
        return null;
    }


    public static void respondSuccess(OutputStream api_response, JsonObject body_json)
    throws IOException
    {
        JsonObject success_json = new JsonObject();
        success_json.put("statusCode", "200");
        success_json.put("body", body_json.toJson());
        sendResponse(api_response, success_json);
    }


    public static void respondError(OutputStream api_response, int http_code, List<String> error_messages)
    throws IOException
    {
        JsonObject body_json = new JsonObject();
        body_json.put("success", false);
        body_json.put("errors", new JsonArray(error_messages));

        JsonObject error_json = new JsonObject();
        error_json.put("statusCode", Integer.toString(http_code));
        error_json.put("body", body_json.toJson());
        sendResponse(api_response, error_json);
    }
    public static void respondError(OutputStream api_response, int http_code, String error)
    throws IOException
    {
        respondError(api_response, http_code, Arrays.asList(error));
    }


    protected static void sendResponse(OutputStream api_response, JsonObject full_response)
    throws IOException
    {
        // Set HTTP headers for response
        JsonObject response_headers = new JsonObject();
        response_headers.put("content-type", "application/json");
        full_response.put("headers", response_headers);

        // Send response as an OutputStream to API Gateway
        OutputStreamWriter stream_writer = new OutputStreamWriter(api_response, "UTF-8");
        stream_writer.write(full_response.toJson());
        stream_writer.close();
    }


    public static String getStringQueryParam(JsonObject request_json, String param_name, String default_value)
    {
        JsonObject query_params = request_json.getMapOrDefault("queryStringParameters", null);
        if (query_params == null) {
            return default_value;
        }

        return query_params.getStringOrDefault(param_name, default_value);
    }


    public static int getIntegerQueryParam(JsonObject request_json, String param_name, int default_value)
    {
        JsonObject query_params = request_json.getMapOrDefault("queryStringParameters", null);
        if (query_params == null) {
            return default_value;
        }

        String raw_value = query_params.getStringOrDefault(param_name, null);
        if (raw_value == null) {
            return default_value;
        }

        try {
            return Integer.parseInt(raw_value);
        } catch (NumberFormatException num_ex) {
            return default_value;
        }
    }
}
