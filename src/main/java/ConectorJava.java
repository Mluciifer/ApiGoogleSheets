import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.awt.Dimension;
import java.net.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/* 
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Luciifer
 */
public class ConectorJava {
    
    private static final String  APPLICATION_NAME="Google sheets APIS";
    private static final JacksonFactory JSON_FACTORY= JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH="token";
    private static final String USER_ENTERED= "USER_ENTERED";
    /* instacia global de los alcances requeridos por este proyecto*/

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret_1037846472894-c2kepjpijevknfacbgia1letddi54vhu.apps.googleusercontent.com (3).json";
    /* crea un objeto de credencial autorizado 
    @param HTTP_TRANSPORT es el transporte HTTP de la red
    @return devuelve un objeto autorizado
    @throws lanza IOExceptions si el archivo credencial.json no se encuentra
     */  
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)throws IOException {
      //carga el cliente secreto 
      InputStream in =ConectorJava.class.getResourceAsStream( CREDENTIALS_FILE_PATH);
      if(in ==null){
          throw new FileNotFoundException("no encuentra credencial: " + CREDENTIALS_FILE_PATH);
      }
      GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
      
      //Construccion de flujo y solicitudes autirazadas de usuarios
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
              .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
              .setAccessType("offline")
              .build();
      LocalServerReceiver receiver =new LocalServerReceiver.Builder().setPort(8080).build();
      return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        }  
        /*
            //imprime los datos de un rango elegido desde una hoja de calculo SHEETS en google docs
             // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
             *el spreadsheetID es el codigo de la cuarta posicion del directorio URL
     */
  public static void main(String[] args)throws IOException, GeneralSecurityException {
        //construye una nueva API con servicio de cliente
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "15Lzhev2_xhMx06feltoLLuG4Ykt0_1WrQFAkgDCDmlk";
        final String range ="A1:I";
        Sheets servicio =new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
//LEER UN SOLO RANGO----------------------------------------------------------------------------------------------------------------------- 
        //Leer e imprimir un solo rango
       /* ValueRange response = servicio.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues(); */
      
              /*  if(values == null || values.isEmpty()){
            System.out.println("no se encontraron datos");
        }else{
            System.out.println("DATOS");
            //recorre cada valor dentro de su rango 
            for(List row : values){
                //imprime las columnas elegidas desde el puesto 0
                System.out.printf("%s, %s, %s, %s\n",row.get(0),row.get(1),row.get(2),row.get(3));
            }
        }*/    
 //LEER MULTIPLES DATOS-----------------------------------------------------------------------------------------------------------------------             
              //lee e imprime multiples rangos
           /* List <String> ranges=Arrays.asList("A9:B13","F9:F20");
            BatchGetValuesResponse result = servicio.spreadsheets().values().batchGet(spreadsheetId).setMajorDimension("COLUMNS")
                .setRanges(ranges) 
                .execute();
       List<ValueRange> val = result.getValueRanges();
    
        System.out.printf("rangos recividos: %s, %s\n",val.get(0),val.get(1)); */
        
 //ESCRIBIR--------------------------------------------------------------------------------------------------------------------------       
            
            //escribe datos y valores en la tabla GoogleSheets
          /*List<List<Object>> Valor = Arrays.asList(Arrays.asList("carlitos","","wey"));
        ValueRange body = new ValueRange().setValues(Valor);
        UpdateValuesResponse result=servicio.spreadsheets().values().update(spreadsheetId, range, body)
                .setValueInputOption(USER_ENTERED)
                .execute();
        System.out.println("%d cells update."+ result.getUpdatedCells()); */   
          
//ESCRIBIR DATOS MULTIPLES-----------------------------------------------------------------------------------------------------------------------------
                
                //Escribe datos multiples en filas o columnas
           /* List<List<Object>> valorM= Arrays.asList(Arrays.asList("","","","",""),Arrays.asList("","","","",""));
            List<ValueRange> data =new ArrayList<>();
            data.add(new ValueRange().setMajorDimension("ROWS")
                    .setRange(range)
                    .setValues(valorM));
            
            BatchUpdateValuesRequest body= new BatchUpdateValuesRequest()
                    .setValueInputOption(USER_ENTERED)
                    .setData(data);
            BatchUpdateValuesResponse resl =servicio.spreadsheets()
                    .values()
                    .batchUpdate(spreadsheetId, body)
                    .execute();
            System.out.printf(" celdas actualizadas: %d",resl.getTotalUpdatedCells()); */
            
 //ADJUNTAR VALORES-----------------------------------------------------------------------------------------------------------------------------           
                // adjunta valores al final de los datos escritos en cada columnda
              List <List<Object>> Avalues= Arrays.asList(Arrays.asList("BOLETA"));
              ValueRange body =new ValueRange().setValues(Avalues);
              AppendValuesResponse reltd= servicio.spreadsheets().values().append(spreadsheetId, range, body)
                      .setValueInputOption(USER_ENTERED)
                      .execute();
              System.out.printf("%d celdas adjuntadas", reltd.getUpdates().getUpdatedCells());
                  
            
            
         
       
        
  }
    
       
    }


