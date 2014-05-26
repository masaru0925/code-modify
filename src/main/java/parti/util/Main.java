/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import parti.util.pojo.Parameter;
import parti.util.pojo.Query;
import parti.util.pojo.ReturnEntity;

/**
 *
 * @author masaru
 */
public class Main {

		private static final Logger LOG = Logger.getLogger(Main.class.getName());

		public static void main(String args[]) throws IOException, JAXBException, ClassNotFoundException {
				Main main = new Main();
				String filePath = "src/main/java/parti/util/pojo/Message.xml";
				File file = new File(filePath);
				ReturnEntity entity = JAXB.unmarshal(file, ReturnEntity.class);
				EntityModify modify = main.createEntityModify(entity.getQueries());
				LOG.log(Level.INFO, modify.namedQueries);
				LOG.log(Level.INFO, modify.staticQueryNames);
				LOG.log(Level.INFO, modify.staticParamNames);

				List lines = Files.readAllLines(Paths.get(filePath), Charset.forName("UTF-8"));
//		String source = 
		}

		/**
		 * <EntityConverter>.....</EntityConverter>
		 * とかでやるか
		 *
		 * @EntityModify を打ちまくってこれがあったら次の行が対象みたいにするか
		 */
		private EntityModify createEntityModify(List<Query> queries) {
				StringBuilder namedQueryBuilder = new StringBuilder();
				StringBuilder staticQueryNameBuilder = new StringBuilder();
				StringBuilder staticParamNameBuilder = new StringBuilder();
				for (Query query : queries) {
						namedQueryBuilder
								.append("@NamedQuery(name=\"")
								.append(query.getName())
								.append("\", query=\"")
								.append(query.getStatement()
										.replaceAll("\r\n", "")
										.replaceAll("\n", ""))
								.append("\")\n");
						staticQueryNameBuilder
								.append("public static final String QUERY_")
								.append(query.getName())
								.append(" = \"")
								.append(query.getName())
								.append("\";\n");
						// TODO: Parameterも使ってメソッドを作る
						if (null != query.getParameters()) {
								for (Parameter param : query.getParameters()) {
										staticParamNameBuilder
												.append("public static final String PARAM_")
												.append(query.getName())
												.append("_")
												.append(param.getName())
												.append(" = \"")
												.append(param.getName())
												.append("\";\n");
								}
						}
				}
				EntityModify modify = new EntityModify();
				modify.namedQueries = namedQueryBuilder.toString();
				modify.staticQueryNames = staticQueryNameBuilder.toString();
				modify.staticParamNames = staticParamNameBuilder.toString();

				return modify;
		}

		class EntityModify {

				String namedQueries;
				String staticQueryNames;
				String staticParamNames;
				String methods;

		}

}
