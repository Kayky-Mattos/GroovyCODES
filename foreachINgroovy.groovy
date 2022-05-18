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

    //Variaveis para criação
    def json;
    def array = [];
    def jsonObj;
    def ntel;

    //Fazendo o forEach para alimentar o novo json, a partir do json q esta no body
    jsonObject.d.results.forEach{e ->
            json = JsonOutput.toJson(
                PrimeiroNome : e.firstName,
                Titulo       : e.title,
                Email        : e.email,
                Localizacao  : e.location
            )
            //json criando novamente virando um objeto
            jsonObj = jsonParser.parseText(json)

            e.empInfo.personNav.phoneNav.results.eachWithIndex{it,index->
            ntel = index + 1
            jsonObj."Telefone $ntel" = it.phoneNumber
        }
            json = JsonOutput.toJson(jsonObj)    
            array.push(json)

    }
        message.setBody(JsonOutput.prettyPrint(array.toString()))

    return message
    }