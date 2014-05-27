/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
	public static final String JSONMANAGEDREF_ANNOTATION = MODIFY_MARK + "@JsonManagedReference";
	public static final String JSONBACKREF_ANNOTATION = MODIFY_MARK + "@JsonBackReference";

	public static Pattern CLASSDEF_PATTERN = Pattern.compile("public class ");
	public static Pattern ONETOMANY_PATTERN = Pattern.compile("@OneToMany");
	public static Pattern MANYTOONE_PATTERN = Pattern.compile("@ManyToOne");
	//public static Pattern ONETOMANY_PATTERN = Pattern.compile("^[ \t]+@OneToMany");
	//public static Pattern MANYTOONE_PATTERN = Pattern.compile("^[ \t]+@ManyToOne");

	/**
	 * 
	 * @param entity
	 * @return 
	 */
	public ModifyParts createModifyParts(ReturnEntity entity) {
		List<Query> queries = entity.getQueries();
		StringBuilder namedQueryBuilder = new StringBuilder();
		StringBuilder staticQueryNameBuilder = new StringBuilder();
		StringBuilder staticParamNameBuilder = new StringBuilder();
		StringBuilder methodBuilder = new StringBuilder();
		StringBuilder staticQueryName_VarNameBuilder = new StringBuilder()
			//						.append(entity.getClassName())
			.append("QUERY_");

		for (Query query : queries) {
			StringBuilder staticParamName_VarNameBuilder = new StringBuilder()
				//							.append(entity.getClassName())
				.append("PARAM_")
				.append(query.getName())
				.append("_");
			StringBuilder methodSetParamBuilder = new StringBuilder();
//						staticQueryName_VarNameBuilder
//								.append(query.getName());
			namedQueryBuilder
				.append("@NamedQuery(name=\"")
				.append(query.getName())
				.append("\", query=\"")
				.append(query.getStatement()
					.replaceAll("\r\n", "")
					.replaceAll("\n", "")
					.replaceAll("\t+", " "))
				.append("\")\n");
			staticQueryNameBuilder
				.append("public static final String ")
				.append(staticQueryName_VarNameBuilder.toString())
				.append(query.getName())
				.append(" = \"")
				.append(query.getName())
				.append("\";\n");
			methodBuilder
				.append("public List<")
				.append(entity.getClassName())
				.append("> ")
				.append(query.getName())
				.append("(");

			if (null != query.getParameters()) {
				int lastIndex = query.getParameters().size() - 1;
				for (int index = 0; index <= lastIndex; index++) {
//								for (Parameter param : query.getParameters()) {
					Parameter param = query.getParameters().get(index);
//										staticParamName_VarNameBuilder
//												.append(param.getName());
					staticParamNameBuilder
						.append("public static final String ")
						.append(staticParamName_VarNameBuilder.toString())
						.append(param.getName())
						.append(" = \"")
						.append(param.getName())
						.append("\";\n");
					methodBuilder
						.append(param.getType())
						.append(" ")
						.append(param.getName());
					if (index != lastIndex) {
						methodBuilder.append(", ");
					}
					methodSetParamBuilder
						.append("\tquery.setParam(")
						.append(entity.getClassName())
						.append(".")
						.append(staticParamName_VarNameBuilder.toString())
						.append(param.getName())
						.append(", ")
						.append(param.getName())
						.append(");\n");
				}
			}
			methodBuilder
				.append("){\n")
				.append("\tQuery query = getEntityManager().createNamedQuery(")
				.append(entity.getClassName())
				.append(".")
				.append(staticQueryName_VarNameBuilder.toString())
				.append(query.getName())
				.append(", ")
				.append(entity.getClassName())
				.append(".class);\n")
				.append(methodSetParamBuilder.toString())
				.append("\treturn query.getResultList();\n")
				.append("}\n\n");
		}
		ModifyParts modify = new ModifyParts();
		modify.namedQueries = addModifyMark(namedQueryBuilder.toString());
		modify.staticQueryNames = addModifyMark(staticQueryNameBuilder.toString());
		modify.staticParamNames = addModifyMark(staticParamNameBuilder.toString());
		modify.methods = addModifyMark(methodBuilder.toString());

		return modify;
	}

	public static String addModifyMark(String org){
		return org.replaceAll("^", MODIFY_MARK).replaceAll("\n", "\n" + MODIFY_MARK);
	}

	/**
	 * 
	 */
	public class ModifyParts {

		String namedQueries;
		String staticQueryNames;
		String staticParamNames;
		String methods;

	}

	/**
	 * remove CodeModify.MODIFY_MARK ed lines
	 *
	 * @param orgLines
	 * @return
	 */
	public static List<String> cleaning(List<String> orgLines) {
		List<String> cleanLines = new ArrayList<>();
		for (String line : orgLines) {
			if (!line.startsWith(CodeModify.MODIFY_MARK)) {
				cleanLines.add(line);
			}
		}
		return cleanLines;
	}

	/**
	 * Cleaning NamedQuery, static queryName, paramName
	 *
	 * @param lines
	 * @param modifyParts
	 * @return 
	 */
	public static List<String> modifyEntity(List<String> lines, ModifyParts modifyParts) {
		List<String> cleanLines = cleaning(lines);
		List<String> modifiedLines = new ArrayList<>();
		for (String line : cleanLines) {
			if (CLASSDEF_PATTERN.matcher(line).find()) {
				modifiedLines.add(modifyParts.namedQueries);
				modifiedLines.add(line);
				modifiedLines.add(modifyParts.staticQueryNames);
				modifiedLines.add(modifyParts.staticParamNames);
			} else if (ONETOMANY_PATTERN.matcher(line).find()) {
				modifiedLines.add(JSONMANAGEDREF_ANNOTATION);
				modifiedLines.add(line);
			} else if (MANYTOONE_PATTERN.matcher(line).find()) {
				modifiedLines.add(JSONBACKREF_ANNOTATION);
				modifiedLines.add(line);
			} else {
				modifiedLines.add(line);
			}
		}
		return modifiedLines;
	}

	/**
	 * Cleaning Methods for query
	 * @param lines
	 * @param modifyParts
	 * @return 
	 */
	public static List<String> modifyFacade(List<String> lines, ModifyParts modifyParts) {
		List<String> cleanLines = cleaning(lines);
		List<String> modifiedLines = new ArrayList<>();
		for (String line : cleanLines) {
			if (CLASSDEF_PATTERN.matcher(line).find()) {
				modifiedLines.add(line);
				modifiedLines.add(modifyParts.methods);
			} else {
				modifiedLines.add(line);
			}
		}
		return modifiedLines;
	}
}
