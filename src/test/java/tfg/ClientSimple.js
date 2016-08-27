 function capture_sensor_data(){  
 var contentTypeRequest = $.ajax({  
      url: 'http://orion.lab.fi-ware.eu:1026/NGSI10/queryContext',  
      data: '<?xml version="1.0" encoding="UTF-8"?>  
                <queryContextRequest>  
                     <entityIdList>  
                          <entityId type = "Sensor" isPattern="true">  
                               <id>urn:smartsantander:testbed:.*</id>   
                          </entityId>   
                     </entityIdList>   
                     <attributeList>   
                          <attribute>Latitud</attribute>  
                          <attribute>Longitud</attribute>  
                     </attributeList>       
                </queryContextRequest>  ',  
      type: 'POST',  
      dataType: 'xml',  
      contentType: 'application/xml',  
      headers: { 'X-Auth-Token' :   
           'Dwamy5XjNDo3hJrhZK64MO1uHmMXJz-WvHC-OVHi8LlT1ll2wkG4psKzrjiTNE-VazC21R-jzTtUll7-AghgCA'}  
      });  
      contentTypeRequest.done(function(xml){   
           var latitud = new Array();  
           var longitud = new Array();  
           var i = 0;       
           var len = $(xml).find("contextAttribute").children().size();  
           $(xml).find('contextAttribute').each(function(){  
              if (  $(this).find('type').text() == "urn:x-ogc:def:phenomenon:IDAS:1.0:latitude"  
                  && $(this).find('contextValue').text() != "" )  
              {   
                latitud[i] = $(this).find('contextValue').text(); i=i+1;  
              }  
              if ($(this).find('type').text() == "urn:x-ogc:def:phenomenon:IDAS:1.0:longitude"  
                  && $(this).find('contextValue').text() != ""       )  
              {   
                longitud[i] = $(this).find('contextValue').text();   
              }  
                                                                  });  
           for (var j=0; j<i; j++){                 
                console.log("DEBUG :" + latitud[j] + "," + longitud[j]);                 
           }                      
      });                 
      contentTypeRequest.fail(function(jqXHR, textStatus){     
           console.log( "DEBUG :  Ajax request failed... ("
                                  + textStatus + ' - ' + jqXHR.responseText + ")." );  
      });  
      contentTypeRequest.always(function(jqXHR, textStatus){       });  
 } 