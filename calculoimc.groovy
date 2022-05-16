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

    // Variaveis pra criação de OutPut
    def df = new DecimalFormat("#0.00")
    def imc =  jsonObject.peso / (jsonObject.altura * jsonObject.altura)

    def imcFormatado = df.format(imc)

    if (imc < 18.5){
        println ("Você está abaixo do peso ! e o seu IMC é de: ${imcFormatado}")
    }
    else if (imc > 18.5  && imc < 24.9){
        println ("Você está no peso ideal ! e o seu IMC é de: ${imcFormatado}")
    }
    else if (imc >25.0 && imc < 29.9){
        println ("Você está com sobrepeso ! e o seu IMC é de: ${imcFormatado}")
    }
    else if (imc >30.0 && imc < 39.9){
        println ("Você está com obesidade ! e o seu IMC é de: ${imcFormatado}")
    }else{
        println ("Você está com obesidade GRAVE! e o seu IMC é de: ${imcFormatado}")
    }

    return message
    }