/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util.pojo;

/**
 *
 * @author masaru
 */
public class Parameter {

	private String name;
	private String type;

	public Parameter() {
	}

	@Override
	public String toString() {
		return "Parameter{" + "name=" + name + ", type=" + type + '}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
