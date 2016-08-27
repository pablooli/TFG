package tfg;

/**
 * @author Pablo OLiver Remon
 * Nip: 595792
 */

import static com.google.common.collect.Lists.newArrayList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Response;

import tfg.OrionConnector;
import tfg.entities.Entities;
import tfg.entities.OrionAttribute;
import tfg.entities.OrionContextElement;
import tfg.entities.OrionNotifyConditions;
import tfg.entities.OrionSubscribeElement;
import tfg.entities.OrionUnsubscribeEntiti;



public class Test {


	@SuppressWarnings("unchecked")
	public static  void main(String[] args) throws OrionConnectorException, ClientProtocolException, IOException {
		/*OrionConnector op = new OrionConnector(
				new OrionConnectorConfiguration());
		Response resp = op.unsubscribeContext(new OrionUnsubscribeEntiti("559c0a6e985de32732847499"));
		System.out.println("Response: " +  resp.returnContent());*/
		/*Entities en = new Entities("Room1",false,"Room");
		List<Entities> len = new ArrayList<Entities>();
		len.add(en);
		List<String> lat = new ArrayList<String>();
		lat.add("temperature");
		List<OrionNotifyConditions> lonc = new ArrayList<OrionNotifyConditions>();
		List<String> lcv = new ArrayList<String>();
		lcv.add("PT10S");
		lonc.add(new OrionNotifyConditions("ONTIMEINTERVAL", lcv));
		*///OrionSubscribeElement ose = new OrionSubscribeElement(len, lat, "http://localhost:1028/accumulate"
		//		, "P1M", lonc);
		//OrionConnector op = new OrionConnector(
		//				new OrionConnectorConfiguration());

		//Response resp = op.subscribeContext(ose);
		//System.out.println("Response: " +  resp.returnContent());
		
		/*//Creamos Elementos de OrionContext Room1*/
		OrionContextElement oer1 = new OrionContextElement();
		oer1.setId("Room1");
		oer1.setPattern(false);
		oer1.setType("Room");
		/*//Añadimos atributos a elemento de Orion
		OrionAttribute<String> oa1 = new OrionAttribute<>("temperature", "float", "23");
		OrionAttribute<String> oa2 = new OrionAttribute<>("pressure", "integer","720");
		List<OrionAttribute<String>> oaList = newArrayList(oa1, oa2);
		oer1.getAttributes().addAll(oaList);
		//Creamos Elementos de OrionContext Room
		OrionContextElement oer2 = new OrionContextElement();
		oer2.setId("Room2");
		oer2.setPattern(false);
		oer2.setType("Room");
		//Añadimos atributos a elemento de Orion
		OrionAttribute<String> oa3 = new OrionAttribute<>("temperature", "float", "21");
		OrionAttribute<String> oa4 = new OrionAttribute<>("pressure", "integer", "711");
		List<OrionAttribute<String>> oaList2 = newArrayList(oa3, oa4);
		oer2.getAttributes().addAll(oaList2);*/
		//Configuracion del OrionConector {host, port y http}
		OrionConnector op = new OrionConnector(
				new OrionConnectorConfiguration());

		//Response resp = op.registerContextElements(oer1, oer2);

		//System.out.println("Response: " +  resp.returnContent());
		Response resp = op.deleteContextElements(oer1);
		System.out.println("Response: {}"+ resp.returnContent());
		//resp = op.deleteContextElements(oer2);
		//System.out.println("Response: {}"+ resp.returnContent());
		
		
		//Creamos Elementos de OrionContext Room1
		Response respQuery = op.queryContext("Room", "Room1");
		try {
			System.out.println("Response: " +  respQuery.returnContent());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
