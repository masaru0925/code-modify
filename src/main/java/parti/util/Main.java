/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parti.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import parti.util.CodeModify.ModifyParts;
import parti.util.pojo.ReturnEntity;

/**
 *
 * @author masaru
 */
public class Main {

				private static final Logger LOG = Logger.getLogger(Main.class.getName());

				public static void main(String args[]) throws IOException, JAXBException, ClassNotFoundException {
				// TODO: activate -------------------------------------------->
								// TODO: String queryXMLDirectory = args[0];
								// TODO: String entityDirectory = args[1];
								// TODO: String facadeDirectory = args[2];
								// <-----------------------------------------------------------
								// TODO: remove ---------------------------------------------->
								String filePath = "src/main/java/parti/util/pojo/Message.xml";
//								String entitySourcePath = filePath.replaceAll(".xml", ".java");
//								String facadeSourcePath = filePath.replaceAll(".xml", "Facade.java");
//								File file = new File(filePath);
								// <----------------------------------------------------------

								String xmlDir = //args[0];
												"src/main/java/parti/util/pojo";
								String entityDir = //args[1];
												"src/main/java/parti/util/pojo";
								String facadeDir = //args[2];
												"src/main/java/parti/util/pojo";

								//modify(filePath);
								File xmlDirFile = new File(xmlDir);
								File[] xmlFiles = xmlDirFile.listFiles();
								for(File xmlFile: xmlFiles){
												String xmlFileName = xmlFile.getName();
												if(xmlFileName.endsWith("xml")){
													String returnEntityName = xmlFileName.split("\\.")[0];
													Path entitySourcePath = Paths.get(entityDir+"/"+returnEntityName+".java");
													Path facadeSourcePath = Paths.get(facadeDir+"/"+returnEntityName+"Facade.java");

													CodeModify codeModify = new CodeModify();
													ModifyParts modifyParts = codeModify.createModifyParts(
															JAXB.unmarshal(xmlFile, ReturnEntity.class)
													);
													modifySourceCodeFiles(modifyParts, entitySourcePath, facadeSourcePath);
												}
								}
				}

				/**
				 * 
				 * @param modifyParts
				 * @param entitySourcePath
				 * @param facadeSourcePath
				 * @throws IOException 
				 */
				public static void modifySourceCodeFiles(ModifyParts modifyParts, Path entitySourcePath, Path facadeSourcePath) throws IOException{

								List<String> orgEntityLines = Files.readAllLines(entitySourcePath, Charset.forName("UTF-8"));
								List<String> orgFacadeLines = Files.readAllLines(facadeSourcePath, Charset.forName("UTF-8"));
								List<String> modifiedEntityLines = CodeModify.modifyEntity(orgEntityLines, modifyParts);
								List<String> modifiedFacadeLines = CodeModify.modifyFacade(orgFacadeLines, modifyParts);

								Files.write(entitySourcePath, modifiedEntityLines, Charset.forName("UTF-8"));
								Files.write(facadeSourcePath, modifiedFacadeLines, Charset.forName("UTF-8"));
//								LOG.log(Level.INFO, String.join("\n", modifiedEntityLines));
//								LOG.log(Level.INFO, String.join("\n", modifiedFacadeLines));

				// FACADE
								// TODO: CLASSDEF_PATTERN-> [line], MODIFY_MARK, method

				}
}
