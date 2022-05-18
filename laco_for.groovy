import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.json.*;
import java.time.*;
import java.text.DecimalFormat;

def Message processData(Message message) {
    
    //Mapeando JSON
    def body = message.getBody(String)
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)

    // Variaveis
    def json;
    def array = [];
    def jsonObj
    def ntel
    
    //Esse primeiro for eu pego os dados que não se repetem, os que se repetem encapsulo nele mesmo
    for(int i = 0; i < jsonObject.d.results.size(); i++) {
        json = JsonOutput.toJson(
            PrimeiroNome : jsonObject.d.results[i].firstName,
            Titulo       : jsonObject.d.results[i].title,
            Email        : jsonObject.d.results[i].email,
            Localizacao  : jsonObject.d.results[i].location,
            // Telefone     : jsonObject.d.results[i].empInfo.personNav.phoneNav.results[0].phoneNumber
        )

        // passando o JSON para objeto novamente, para manipular o mesmo, esse obj vai receber os outros dados
        jsonObj = jsonParser.parseText(json)

        for(int y = 0; y < jsonObject.d.results[i].empInfo.personNav.phoneNav.results.size(); y++){
            ntel = y + 1
            jsonObj."Telefone $ntel" = jsonObject.d.results[i].empInfo.personNav.phoneNav.results[y].phoneNumber
        }

        json = JsonOutput.toJson(jsonObj)    
        array.push(json)
    }
        message.setBody(JsonOutput.prettyPrint(array.toString()))

    return message
    }