package com.reafs;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.reafs.estates.EstateProperty;
import com.reafs.estates.EstatePropertyFactory;
import com.reafs.input.UserInput;
import com.reafs.input.types.DistanceFromTheSea;

/**
 * This is a sample class to launch a rule.
 */
public class RealEstateAdvisor {

	public static final void main(String[] args) {
		try {
			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase
					.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
					.newFileLogger(ksession, "test");
			// go !
			EstatePropertyFactory estateFactory = EstatePropertyFactory
					.getInstance();

			/*
			 * Add estates
			 */
			List<EstateProperty> estateProperties = estateFactory
					.getEstateProperties();
			for (EstateProperty estateProperty : estateProperties) {
				ksession.insert(estateProperty);
			}

			/*
			 * User input
			 */
			List<UserInput> userInputs = new ArrayList<UserInput>();
			userInputs.add(DistanceFromTheSea.NOT_IMPORTANT);
			for (UserInput userInput : userInputs) {
				ksession.insert(userInput);
			}

			ksession.fireAllRules();

			/*
			 * Log result
			 */
			System.out.println(estateProperties);
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();

		for (String ruleFile : AdvisorProperties.RULE_FILES) {
			kbuilder.add(ResourceFactory.newClassPathResource(ruleFile),
					ResourceType.DRL);
		}

		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}
}
