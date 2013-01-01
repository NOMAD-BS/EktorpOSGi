import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.TextNode;
import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

public class Start {

	/**
	 * @param args
	 */
	public static void main2(String[] args) {
		try {
			HttpClient httpClient = new StdHttpClient.Builder().url(
					"http://localhost:5984").build();

			CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
			CouchDbConnector db = new StdCouchDbConnector("mydatabase",
					dbInstance);

			db.createDatabaseIfNotExists();

			SofaRepository repo = new SofaRepository(db);

			// Sofa s = new Sofa();
			// s.setColor("green");
			//
			// repo.add(s);

			InputStream data = new FileInputStream(
					"c:\\tmp\\Beispielscreenshots.zip");
			String contentType = "application/octet-stream";

			AttachmentInputStream a = new AttachmentInputStream(
					"attachment_id", data, contentType);

			db.createAttachment("test", "2-1dd778fe4af761eb76087b87c602d25f", a);

			// List<Sofa> f = repo.getAll();
			// for (Sofa sofa : f) {
			// System.out.println(sofa.toString());
			// // repo.remove(sofa);
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			HttpClient httpClient = new StdHttpClient.Builder().url(
					"http://localhost:5984").build();

			CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
			CouchDbConnector db = new StdCouchDbConnector("mydatabase",
					dbInstance);

			db.createDatabaseIfNotExists();

			ObjectNode myNode = JsonNodeFactory.instance.objectNode();
			myNode.put("color", "green");
			myNode.put("_id", UUID.randomUUID().toString());
//			myNode.put("_rev", "1");
			

			db.update(myNode);
			System.out.println(myNode.get("_id").getTextValue());
			System.out.println(myNode.get("_rev").getTextValue());

			InputStream data = new FileInputStream(
					"c:\\tmp\\Beispielscreenshots.zip");
			String contentType = "application/x-zip-compressed";

			AttachmentInputStream a = new AttachmentInputStream(
					"Beispielscreenshots.zip", data, contentType);
			db.createAttachment(myNode.get("_id").getTextValue(),
					myNode.get("_rev").getTextValue(), a);
			//
			//
			//
			//
			// db.createAttachment("test","2-1dd778fe4af761eb76087b87c602d25f",
			// a);
			//

			// List<Sofa> f = repo.getAll();
			// for (Sofa sofa : f) {
			// System.out.println(sofa.toString());
			// // repo.remove(sofa);
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
