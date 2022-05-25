/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en-US/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en-US/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.json.*;
import groovy.xml.*;
import java.io.*;
import java.util.Date;
import java.util.TimeZone
import java.time.*;
import java.text.*;

def Message processData(Message message) 
{
    def headers = message.getHeaders();
    def cookie = headers.get("Set-Cookie");
    StringBuffer bufferedCookie = new StringBuffer();
    for (Object item : cookie) 
    {
        bufferedCookie.append(item + "; ");      
    }
    message.setHeader("Cookie", bufferedCookie.toString());
    def messageLog = messageLogFactory.getMessageLog(message);
    if(messageLog != null)
    {
        messageLog.setStringProperty("Logging_Cookie", bufferedCookie.toString());
    }
    // def p = message.getProperties();
    // def proper = p.get("json").toString()
    def body = message.getBody(String)
    def json
    def jsonParser = new JsonSlurper()
    def jsonObject = jsonParser.parseText(body)
    
    
/*-----------------------Formas de fazer a formatação da Data----------------------*/

    // def date = jsonObj."Last Modified"
    // String[] d = date.split('-')
    // jsonObject."Last Modified" = d[2] + d[1] + d[0

    
    //   String sample = jsonObject.'Last Modified'; 
    //   jsonObject.'Last Modified' = sample[6..9]+sample[3..4]+sample[0..1]

    
    // def data = jsonObject.'Last Modified'
    // def date = new Date().parse("dd-MM-yyyy", data)
    // SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd")
    // jsonObject.'Last Modified' = formatador.format(date) 
    
    // Pode ser feita diretamente no JSON, assim como mostra na linha 66
/*----------------------------------------------------------------------------------*/


    json = JsonOutput.toJson(
            Codigo            : jsonObject.Code,
            Descricao         : jsonObject.Description,
            GrupoMateial      : jsonObject.MaterialGroup,
            CriadoPor         : jsonObject.CreatedBy,
            ModificadoPor     : jsonObject.ModifiedBy,
            UltimaModificacao : Date.parse("dd-MM-yyyy", jsonObject."Last Modified").format("yyyyMMdd"),
            Ativo             : jsonObject.Active
            )
            
            
            message.setBody(JsonOutput.prettyPrint(json))
    
    return message;
}
