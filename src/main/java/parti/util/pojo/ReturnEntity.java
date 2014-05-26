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
public class ReturnEntity {

		private String className;
		private List<Query> queries;

		public ReturnEntity() {
		}

		@Override
		public String toString() {
				return "ReturnEntity{" + "className=" + className + ", queries=" + queries + '}';
		}

		public String getClassName() {
				return className;
		}

		public void setClassName(String className) {
				this.className = className;
		}

		public List<Query> getQueries() {
				return queries;
		}

		public void setQueries(List<Query> queries) {
				this.queries = queries;
		}

}
