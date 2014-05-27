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
import java.nio.file.Paths;
import java.util.ArrayList;
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
				// TODO: String entityDirectory = args[0];
				// TODO: String facadeDirectory = args[1];
				// <-----------------------------------------------------------

				CodeModify modify = new CodeModify();
				// TODO: remove ---------------------------------------------->
				String filePath = "src/main/java/parti/util/pojo/Message.xml";
				String entitySourcePath = filePath.replaceAll(".xml", ".java");
				String facadeSourcePath = filePath.replaceAll(".xml", "Facade.java");
				File file = new File(filePath);
				// <----------------------------------------------------------

				ReturnEntity entity = JAXB.unmarshal(file, ReturnEntity.class);
				ModifyParts modifyParts = modify.createModifyParts(entity);
				LOG.log(Level.INFO, modifyParts.namedQueries);
				LOG.log(Level.INFO, modifyParts.staticQueryNames);
				LOG.log(Level.INFO, modifyParts.staticParamNames);
				LOG.log(Level.INFO, modifyParts.methods);

				List<String> orgEntityLines = Files.readAllLines(Paths.get(entitySourcePath), Charset.forName("UTF-8"));
				List<String> orgFacadeLines = Files.readAllLines(Paths.get(facadeSourcePath), Charset.forName("UTF-8"));
				List<String> cleanEntityLines = new ArrayList<>();
				List<String> cleanFacadeLines = new ArrayList<>();

				// CLEANING
				for(String line: orgEntityLines){
						if(!line.startsWith(CodeModify.MODIFY_MARK)){
								cleanEntityLines.add(line);
						}
				}
				for(String line: orgFacadeLines){
						if(!line.startsWith(CodeModify.MODIFY_MARK)){
								cleanFacadeLines.add(line);
						}
				}
				//Files.write(Paths.get(entitySourcePath), cleanEntityLines, Charset.forName("UTF-8"));
				//Files.write(Paths.get(facadeSourcePath), cleanFacadeLines, Charset.forName("UTF-8"));

				// ENTTIY
				// TODO: CLASSDEF_PATTERN-> MODIFY_MARK, NamedQuery , [line], static queryname, static paramname
				// TODO: ONETOMANY_PATTERN -> MODIFY_MARK, ANNOTATION, [line]
				// TODO: MANYTOONE_PATTERN -> MODIFY_MARK, ANNOTATION, [line]

				// FACADE
				// TODO: CLASSDEF_PATTERN-> [line], MODIFY_MARK, method
		}
}
