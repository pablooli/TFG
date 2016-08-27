package tfg;

import static tfg.entities.ContextUpdate.ContextUpdateAction.APPEND;
import static tfg.entities.ContextUpdate.ContextUpdateAction.DELETE;
import static tfg.entities.ContextUpdate.ContextUpdateAction.UPDATE;
import static org.apache.http.entity.ContentType.APPLICATION_JSON;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import tfg.entities.ContextUpdate;
import tfg.entities.OrionContextElement;
import tfg.entities.OrionQueryElement;
import tfg.entities.OrionSubscribeElement;
import tfg.entities.OrionUnsubscribeEntiti;
import tfg.entities.QueryContext;

import com.google.gson.Gson;

/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */


/**
 * Connector to the ORion context broker. This connector uses only the NGSI10
 * service from Orion, and none of it's convenience methods.
 * 
 * @author Ivan Gracia (izanmail@gmail.com)
 * @author Pablo OLiver Remon
 */
@SuppressWarnings("unused")
public class OrionConnector {

	private static final String QUERY_PATH = "/ngsi10/queryContext";
	private static final String UPDATE_PATH = "/ngsi10/updateContext";
	private static final String SUBSCRIBE_PATH = "/ngsi10/subscribeContext";
	private static final String ENTITIES_PATH = "/ngsi10/contextEntities";
	private static final String UNSUBSCRIBE_PATH = "/ngsi10/unsubscribeContext";
	private static final String UPDATE_SUBSCRIBE_PATH = "/ngsi10/updateContextSubscription";

	private static final Gson gson = new Gson();

	@Autowired
	private OrionConnectorConfiguration config;

	private URI orionAddr;

	/**
	 * Default constructor to be used when the orion connector is created from a
	 * spring context.
	 */
	public OrionConnector() {

	}

	/**
	 * Orion connector constructor. This constructor is to be used when the
	 * connector is used outside from a spring context.
	 * 
	 * @param config
	 *            Configuration object
	 * @throws OrionConnectorException 
	 */
	public OrionConnector(OrionConnectorConfiguration config) throws OrionConnectorException {
		this.config = config;
		this.init();
	}

	/**
	 * Initiates the {@link #orionAddr}. This step is performed to validate the
	 * fields from the configuration object.
	 * @throws OrionConnectorException 
	 */
	private void init() throws OrionConnectorException {
		try {
			orionAddr = new URIBuilder().setScheme(config.getOrionScheme())
					.setHost(config.getOrionHost())
					.setPort(config.getOrionPort()).build();
		} catch (URISyntaxException e) {
			throw new OrionConnectorException(
					"Could not build URI to make a request to Orion", e);
		}
	}

	/**
	 * Register context elements in the Orion context broker.
	 * 
	 * @param events
	 *            List of events
	 * @return The response from the context broker.
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 * 
	 */
	public Response registerContextElements(
			OrionContextElement... events) throws OrionConnectorException {
		ContextUpdate ctxUpdate = new ContextUpdate(APPEND, events);
		return sendRequestToOrion(ctxUpdate, UPDATE_PATH);
	}

	/**
	 * Updates context elements that exist in Orion.
	 * 
	 * @param events
	 * @return The response from the context broker.
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public Response updateContextElements(
			OrionContextElement... events) throws OrionConnectorException {
		ContextUpdate ctxUpdate = new ContextUpdate(UPDATE, events);
		return sendRequestToOrion(ctxUpdate, UPDATE_PATH);
	}

	/**
	 * Deletes one or more context elements from Orion
	 * 
	 * @param events
	 * @return The response from the context broker.
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public Response deleteContextElements(
			OrionContextElement... events) throws OrionConnectorException {
		ContextUpdate ctxUpdate = new ContextUpdate(DELETE, events);
		return sendRequestToOrion(ctxUpdate, UPDATE_PATH);
	}

	/**
	 * Queries the context broker for a certain element.
	 * 
	 * @param type
	 *            The type of context element
	 * @param id
	 *            the id of the context element
	 * @return The response from the context broker.
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public Response queryContext(String type, String id) throws OrionConnectorException {
		OrionQueryElement element = new OrionQueryElement();
		element.setId(id);
		element.setType(type);
		QueryContext query = new QueryContext(element);
		return sendRequestToOrion(query, QUERY_PATH);
	}
	
	public Response subscribeContext(
			OrionSubscribeElement events) throws OrionConnectorException {
		return sendRequestToOrion(events, SUBSCRIBE_PATH);
	}
	
	public Response unsubscribeContext(
			OrionUnsubscribeEntiti events) throws OrionConnectorException {
		return sendRequestToOrion(events, UNSUBSCRIBE_PATH);
	}

	/**
	 * Queries the context broker for a pattern-based group of context elements
	 * 
	 * @param type
	 *            the type of the context element.
	 * @param pattern
	 *            the pattern to search IDs that fulfil this pattern.
	 * @return The response from the context broker.
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	public Response queryContextWithPattern(String type,
			String pattern) throws OrionConnectorException {
		OrionQueryElement element = new OrionQueryElement();
		element.setId(pattern);
		element.setPattern(true);
		element.setType(type);
		QueryContext query = new QueryContext(element);
		return sendRequestToOrion(query, QUERY_PATH);
	}

	
	/**
	 * Sends a request to Orion
	 * 
	 * @param ctxElement
	 *            The context element
	 * @param path
	 *            the path from the context broker that determines which
	 *            "operation"will be executed
	 * @param responseClazz
	 *            The class expected for the response
	 * @return The object representing the JSON answer from Orion
	 * @throws OrionConnectorException
	 *             if a communication exception happens, either when contacting
	 *             the context broker at the given address, or obtaining the
	 *             answer from it.
	 */
	private <E, T> Response sendRequestToOrion(E ctxElement, String path) 
			throws OrionConnectorException {
		String jsonEntity = gson.toJson(ctxElement);
		System.out.println("Send request to Orion: {}"+ jsonEntity);

		Request req = Request.Post(orionAddr.toString() + path)
				.addHeader("Accept", APPLICATION_JSON.getMimeType())
				.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000)
				.socketTimeout(5000);
		Response response;
		try {
			response = req.execute();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not execute HTTP request",
					e);
		}
		return response;
	}
	

/*	private <T> HttpResponse checkResponse(Response response) throws OrionConnectorException {
		HttpResponse httpResponse;
		try {
			httpResponse = response.returnResponse();
		} catch (IOException e) {
			throw new OrionConnectorException("Could not obtain HTTP response",
					e);
		}

		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new OrionConnectorException("Failed with HTTP error code : "
					+ httpResponse.getStatusLine().getStatusCode());
		}

		return httpResponse;
	}

	private <T> T getOrionObjFromResponse(HttpResponse httpResponse,
			Class<T> responseClazz) {
		InputStream source = null;
		try {
			source = httpResponse.getEntity().getContent();
		} catch (IllegalStateException | IOException e) {
			try {
				throw new OrionConnectorException(
						"Could not obtain entity content from HTTP response", e);
			} catch (OrionConnectorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		T ctxResp = null;
		try (Reader reader = new InputStreamReader(source)) {
			ctxResp = gson.fromJson(reader, responseClazz);
		} catch (IOException e) {
			System.out.println("Could not close input stream from HttpResponse."+ e);
		}

		return ctxResp;
	}
	
	private <T> T getOrionObjFromResponseQuery(Response httpResponse,
			Class<T> responseClazz) {
		InputStream source = null;
		try {
			source = httpResponse.returnContent().asStream();
		} catch (IllegalStateException | IOException e) {
			try {
				throw new OrionConnectorException(
						"Could not obtain entity content from HTTP response", e);
			} catch (OrionConnectorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		T ctxResp = null;
		try (Reader reader = new InputStreamReader(source)) {
			ctxResp = gson.fromJson(reader, responseClazz);
			System.out.println(ctxResp);
		} catch (IOException e) {
			System.out.println("Could not close input stream from HttpResponse."+ e);
		}

		return ctxResp;
	}

	// contextEntities consultas a entidades, borrados...
	// queryContext
	// notifyContext notifyContextRequest notifyContextResponse
	// updateContext

	// queryContext access the context information stored. con lista de
	// atributos
	// devuelve sólo los atributos deseados
	// contextEntities

	/*-
	 *	subscribeContext
	 *	updateContextSubscription
	 *	unsubscribeContext
	 * 
	 * (curl localhost:1026/NGSI10/subscribeContext -s -S 
	 *  --header 'Content-Type: application/json' 
	 *  --header 'Accept: application/json' -d @- | python -mjson.tool) <<EOF
	 *	{
	 *		"entities": [
	 *  		{
	 *				"type": "Room",
	 *				"isPattern": "false",
	 *				"id": "Room1"
	 *			}
	 *		],
	 *		"attributes": [
	 *			"temperature"
	 *		],
	 *		"reference": "http://localhost:1028/accumulate",
	 *		"duration": "P1M",
	 *		"notifyConditions": [
	 *			{
	 *				"type": "ONTIMEINTERVAL",
	 *				"condValues": [
	 *				"PT10S"
	 *				]
	 *			}
	 *		]
	 *	}
	 *	EOF
	 */
}
