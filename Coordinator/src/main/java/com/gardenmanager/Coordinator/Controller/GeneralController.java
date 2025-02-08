package com.gardenmanager.Coordinator.Controller;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/api")
public class GeneralController {

    @GetMapping("/getPlantData/{user}")
    public JsonNode getPlantDAta(@RequestHeader Map<String, String> headers, @PathVariable String user) throws JsonProcessingException {
        String DB_URL = "http://localhost:8080";

        String auth = headers.get("authorization");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String url = DB_URL + "/api/users/username/" + user;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        //transform the response to a json object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        int userId = jsonNode.get("id").asInt();

        //use id to get the plants - if any
        response = restTemplate.exchange(DB_URL + "/api/plants/user/" + userId, HttpMethod.GET, entity, String.class);
        jsonNode = mapper.readTree(response.getBody());
        return jsonNode;
    }

    @GetMapping("/searchPlant/{plant}")
    public JsonNode searchPlant(@RequestHeader Map<String, String> headers, @PathVariable String plant) throws JsonProcessingException {
        String Garden_URL = "http://localhost:8082/plants";

        String auth = headers.get("authorization");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String url = Garden_URL + "?name=" + plant;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        //transform the response to a json object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        return jsonNode;
    }

    @PostMapping("/addPlant")
    public ResponseEntity<String> addPlant(@RequestHeader Map<String, String> headers, @RequestBody JSONObject args) throws JsonProcessingException {
        String plantName = args.getAsString("plantName");
        String username = args.getAsString("username");
        String DB_URL = "http://localhost:8080";
        String auth = headers.get("authorization");

        //First get the user id
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String url = DB_URL + "/api/users/username/" + username;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        //transform the response to a json object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        int userId = jsonNode.get("id").asInt();

// get Last plant id
        url = DB_URL + "/api/plants/getId";
        response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        jsonNode = mapper.readTree(response.getBody());

        int plantId = jsonNode.asInt();
        plantId++;
        //System.out.println(plantId);
        //use id to add the plant as NotPlanted
        url = DB_URL + "/api/plants/" + userId;
        JSONObject plantData = new JSONObject();
        plantData.put("id", plantId);
        plantData.put("plantname", plantName);
        plantData.put("place", "NotPlanted");
        plantData.put("start_time_to_harvest", 1999 - 01 - 01);
        plantData.put("end_time_to_harvest", 1999 - 01 - 01);
        plantData.put("user_id", userId);
        HttpEntity<String> request = new HttpEntity<>(plantData.toString(), httpHeaders);
        response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        System.out.println(response.getStatusCode());
        return ResponseEntity.ok("Plant added successfully.");
    }

    @PutMapping("/plant")
    public ResponseEntity<String> modifyPlant(@RequestHeader Map<String, String> headers, @RequestBody JSONObject args) throws JsonProcessingException {
        String DB_URL = "http://localhost:8080/api";
        String Garden_URL = "http://localhost:8082/plants";
        String plantName = args.getAsString("plantName");
        int plantId = args.getAsNumber("plantId").intValue();

        Date planted = new Date();
        String place = "Backyard";

        //get plant info using the plantName
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = headers.get("authorization");
        httpHeaders.set("authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        plantName = plantName.replace(" ", "+");
        String url = Garden_URL + "?name=" + plantName;
        System.out.println("!!!!!!!" + url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.getBody());
        int add_days = jsonNode.get("growing_degree_days").asInt();
        Date toHarvest = new Date();
        toHarvest.setTime(planted.getTime() + add_days * 24 * 60 * 60 * 1000);

        //Update plant using the plantId
        JSONObject plantData = new JSONObject();
        plantData.put("id", plantId);
        plantData.put("place", place);
        plantData.put("planted", planted.getTime());
        plantData.put("toHarvest", toHarvest.getTime());
        HttpEntity<String> request = new HttpEntity<>(plantData.toString(), httpHeaders);

        url = DB_URL + "/plants/" + plantId;
        response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        return ResponseEntity.ok(response.getBody());
    }

    @DeleteMapping("/deletePlant/{plantId}")
    public ResponseEntity<String> deletePlant(@RequestHeader Map<String, String> headers, @PathVariable int plantId) {
        String DB_URL = "http://localhost:8080/api";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = headers.get("authorization");
        httpHeaders.set("authorization", auth);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        String url = DB_URL + "/plants/" + plantId;
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return ResponseEntity.ok(response.getBody());
    }
}
