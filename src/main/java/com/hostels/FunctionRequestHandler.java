
package com.hostels;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.hostels.beans.Hostels;
import com.hostels.services.HostelsService;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import io.micronaut.json.JsonMapper;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Introspected
public class FunctionRequestHandler extends MicronautRequestHandler<
    APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>
{
    @Inject
    JsonMapper objectMapper;
    @Inject
    HostelsService hostelsService;
    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        try {
            Map<String, String> jsonObject = objectMapper.readValue(input.getBody(), Map.class);
            if("GET".equals(input.getHttpMethod())){
                response.setStatusCode(200);
                response.setBody(hostelsService.searchHostels(
                    Objects.nonNull(jsonObject.get("hostelId")) ? Long.valueOf(jsonObject.get("hostelId")) : null,
                    Objects.nonNull(jsonObject.get("hostelName")) ? jsonObject.get("hostelName") : null,
                    Objects.nonNull(jsonObject.get("hostelAddress")) ? jsonObject.get("timeIn") : null
                ).toString());
                return response;
            }
            if("DELETE".equals(input.getHttpMethod())){
                if (Objects.isNull(jsonObject.get("hostelId"))) throw new RuntimeException("Hostel id is mandatory.");
                hostelsService.deleteById(Long.valueOf(jsonObject.get("hostelId")));
                response.setStatusCode(200);
                response.setBody(String.format("Hostel %s deleted successfully", jsonObject.get("hostelId")));
                return response;
            }
            if (Objects.isNull(jsonObject.get("hostelAddress"))) throw new RuntimeException("Hostel address is mandatory.");
            if (Objects.isNull(jsonObject.get("hostelName"))) throw new RuntimeException("Hostel name is mandatory.");
            Hostels user = hostelsService.save(
                Hostels
                    .builder()
                    .hostelAddress(jsonObject.get("hostelAddress"))
                    .hostelName(jsonObject.get("hostelName"))
                    .build()
            );
            response.setStatusCode(200);
            response.setBody(user.toString());
        } catch (IOException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}