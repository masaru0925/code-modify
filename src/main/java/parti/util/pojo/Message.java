/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util.pojo;

/**
 *
 * @author msaiki
 */
//@NamedQuery
//@NamedQuery
//@NamedQuery
/**@code-modify*/@NamedQuery(name="findSample", query=" select * from tab where a = :param1 b = :param2 ")
/**@code-modify*/@NamedQuery(name="findNotAccessMessage", query=" select * from tab where a = param1 = :paramX and param2 = :paramY or param3 in :paramZ ")
/**@code-modify*/
public class Message {
/**@code-modify*/public static final String QUERY_findSample = "findSample";
/**@code-modify*/public static final String QUERY_findNotAccessMessage = "findNotAccessMessage";
/**@code-modify*/
/**@code-modify*/public static final String PARAM_findSample_param1 = "param1";
/**@code-modify*/public static final String PARAM_findSample_param2 = "param2";
/**@code-modify*/public static final String PARAM_findNotAccessMessage_paramX = "paramX";
/**@code-modify*/public static final String PARAM_findNotAccessMessage_paramY = "paramY";
/**@code-modify*/public static final String PARAM_findNotAccessMessage_paramZ = "paramZ";
/**@code-modify*/

/**@code-modify*/@JsonManagedReference
	//@OneToMany

	public static void oneToMany1() {
		String a = new String();
	}

/**@code-modify*/@JsonManagedReference
	//@OneToMany

	public static void oneToMany2() {
		String a = new String();
	}

/**@code-modify*/@JsonBackReference
	//@ManyToOne

	public static void manyToOne1() {
		String a = new String();
	}

}
