/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parti.util.pojo;

import java.util.List;

/**
 *
 * @author msaiki
 */
public class MessageFacade {
/**@code-modify*/public List<Message> findSample(String param1, UserAccount param2){
/**@code-modify*/	Query query = getEntityManager().createNamedQuery(Message.QUERY_findSample, Message.class);
/**@code-modify*/	query.setParam(Message.PARAM_findSample_param1, param1);
/**@code-modify*/	query.setParam(Message.PARAM_findSample_param2, param2);
/**@code-modify*/	return query.getResultList();
/**@code-modify*/}
/**@code-modify*/
/**@code-modify*/public List<Message> findNotAccessMessage(String paramX, UserAccount paramY, List<XXEntity> paramZ){
/**@code-modify*/	Query query = getEntityManager().createNamedQuery(Message.QUERY_findNotAccessMessage, Message.class);
/**@code-modify*/	query.setParam(Message.PARAM_findNotAccessMessage_paramX, paramX);
/**@code-modify*/	query.setParam(Message.PARAM_findNotAccessMessage_paramY, paramY);
/**@code-modify*/	query.setParam(Message.PARAM_findNotAccessMessage_paramZ, paramZ);
/**@code-modify*/	return query.getResultList();
/**@code-modify*/}
/**@code-modify*/
/**@code-modify*/
	public void somemethod(){

	}
	public void somemethod2(){

	}
	
}
