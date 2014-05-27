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
public class CodeModify {
	private static final Logger LOG = Logger.getLogger(CodeModify.class.getName());

		public static final String MODIFY_MARK = "/**@code-modify*/";

		/**
		 * TODO: Modifyの実現方法
		 * <CodeModify>.....</CodeModify>
		 * とかでやるか それとも//CodeModify などを打ちまくってこれがあったら次の行が対象みたいにするか
		 */
		public ModifyParts createModifyParts(ReturnEntity entity) {
				List<Query> queries = entity.getQueries();
				StringBuilder namedQueryBuilder = new StringBuilder();
				StringBuilder staticQueryNameBuilder = new StringBuilder();
				StringBuilder staticParamNameBuilder = new StringBuilder();
				StringBuilder methodBuilder = new StringBuilder();
				StringBuilder staticQueryName_VarNameBuilder = new StringBuilder()
						.append(entity.getClassName())
						.append(".QUERY_");
				StringBuilder staticParamName_VarNameBuilder = new StringBuilder()
						.append(entity.getClassName())
						.append(".PARAM_");

				for (Query query : queries) {
						StringBuilder methodSetParamBuilder = new StringBuilder();
						staticQueryName_VarNameBuilder
								.append(query.getName());
						namedQueryBuilder
								.append("@NamedQuery(name=\"")
								.append(query.getName())
								.append("\", query=\"")
								.append(query.getStatement()
										.replaceAll("\r\n", "")
										.replaceAll("\n", ""))
								.append("\")\n");
						staticQueryNameBuilder
								.append("public static final String ")
								.append(staticQueryName_VarNameBuilder.toString())
								.append(" = \"")
								.append(query.getName())
								.append("\";\n");
						methodBuilder
								.append("public List<")
								.append(entity.getClassName())
								.append("> ")
								.append(query.getName())
								.append("(");

						// TODO: Parameterも使ってメソッドを作る
						if (null != query.getParameters()) {
								for (Parameter param : query.getParameters()) {
										staticParamName_VarNameBuilder
												.append(param.getName());
										staticParamNameBuilder
												.append("public static final String ")
												.append(staticParamName_VarNameBuilder.toString())
												.append(" = \"")
												.append(param.getName())
												.append("\";\n");
										methodBuilder
												.append(param.getType())
												.append(" ")
												.append(param.getName())
												.append(", ");
										methodSetParamBuilder
												.append("\tquery.setParam(")
												.append(staticParamName_VarNameBuilder.toString())
												.append(", ")
												.append(param.getName())
												.append(");\n");
								}
						}
						methodBuilder
								.append("){\n")
								.append("\tQuery query = getEntityManager().createNamedQuery(")
								.append(staticQueryName_VarNameBuilder.toString())
								.append(", ")
								.append(entity.getClassName())
								.append(".class);\n")
								.append(methodSetParamBuilder.toString())
								.append("\treturn query.getResultList();\n")
								.append("}\n\n");
				}
				ModifyParts modify = new ModifyParts();
				modify.namedQueries = namedQueryBuilder.toString().replaceAll("^", MODIFY_MARK).replaceAll("\n", "\n"+MODIFY_MARK);
				modify.staticQueryNames = staticQueryNameBuilder.toString().replaceAll("^", MODIFY_MARK).replaceAll("\n", "\n"+MODIFY_MARK);
				modify.staticParamNames = staticParamNameBuilder.toString().replaceAll("^", MODIFY_MARK).replaceAll("\n", "\n"+MODIFY_MARK);
				modify.methods = methodBuilder.toString().replaceAll("^", MODIFY_MARK).replaceAll("\n", "\n"+MODIFY_MARK);

				return modify;
		}

		public class ModifyParts {

				String namedQueries;
				String staticQueryNames;
				String staticParamNames;
				String methods;

		}

		// TODO: Facadeのメソッドの生成
		public class FacadeModify {

				String methods;
		}

}
