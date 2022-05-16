import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
import java.util.Date;
import java.util.TimeZone
import java.time.*;


def Message processData(Message message) {
    
    def properties = message.getProperties()
    def headers = message.getHeaders()
//    message.setBody("Teste Prodev")
//    println (headers.get("header"))
//    println (properties.get("property"))
//    println (message.getBody(String))  

// Criação de um Object JSON
    def body = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)

// Variaveis para pegar hora e data atual do sistema
    tz = TimeZone.getTimeZone("GMT-3")
    def date = new Date()
    def ts = new Date()

// Ajustes no JSON para OutPut
    jsonObject.remove("ibge")
    jsonObject.remove("gia")
    jsonObject.remove("siafi")  
    jsonObject.data = date.format("dd-MM-yyyy")
    jsonObject.hora = ts.format("HH:mm:ss", timezone = tz)


// Construindo JSON
    def json = new JsonBuilder(jsonObject).toPrettyString();

    message.setBody(json);

// Construção/Tradução do primeiro JSON,estou criando esse segundo com os dados do primeiro
    def json2 = JsonOutput.toJson(
            zip : jsonObject.cep,
            locality : jsonObject.localidade,
            neighborhood : jsonObject.bairro,
            street : jsonObject.logradouro
        )

        // JsonOutput.prettyPrint(json2)
        message.setBody(JsonOutput.prettyPrint(json2))

    return message
    }