/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util.pojo;

import java.util.List;

/**
 *
 * @author masaru
 */
public class Query {

	@Override
	public String toString() {
		return "Query{" + "name=" + name + ", author=" + author + ", desc=" + desc + ", statement=" + statement + ", parameters=" + parameters + '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public Query(String name, String author, String desc, String statement, List<Parameter> parameters) {
		this.name = name;
		this.author = author;
		this.desc = desc;
		this.statement = statement;
		this.parameters = parameters;
	}

	public Query() {
	}
	private String name;
	private String author;
	private String desc;
	private String statement;
	private List<Parameter> parameters;
}
