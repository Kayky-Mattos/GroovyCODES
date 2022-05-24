import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApi
import com.sap.it.api.ITApiFactory
import com.sap.it.api.securestore.*;
import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    //Body 
       def body = message.getBody(String);
       
       String ts_marvel_pass;
       String api_key_pass;
       String hash_marvel_pass;
       
       def service = ITApiFactory.getApi(SecureStoreService.class, null);

       //Name of secure parameter
       def credential_ts     = service.getUserCredential("ts_marvel"); 
       def credential_apikey = service.getUserCredential("api_key_marvel"); 
       def credential_hash   = service.getUserCredential("hash_marvel"); 
       
        if (credential_ts == null){
            throw new IllegalStateException("No credential found for alias 'ts_marvel'");             
        }else if(credential_apikey == null){
            throw new IllegalStateException("No credential found for alias 'api_key_marvel'");
        }else if(credential_hash == null){
            throw new IllegalStateException("No credential found for alias 'hash_marvel'");
        }
        else{
            ts_marvel_pass   = new String(credential_ts.getPassword());
            api_key_pass     = new String(credential_apikey.getPassword());
            hash_marvel_pass = new String(credential_hash.getPassword());
            }

        //store it in property which can be used in later stage of your integration process.
        message.setProperty("ts", ts_marvel_pass);
        message.setProperty("apikey", api_key_pass);
        message.setProperty("hash", hash_marvel_pass);
    return message;
}