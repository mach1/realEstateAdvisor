import static org.junit.Assert.*;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import com.reafs.AdvisorProperties;
import com.reafs.estates.EstateProperty;
import com.reafs.estates.EstatePropertyFactory;
import com.reafs.input.UserInput;
import com.reafs.input.types.DistanceFromTheNeighbors;
import com.reafs.input.types.DistanceFromTheSea;
import com.reafs.output.UserOutput;
import com.reafs.output.types.PlotSize;
import com.reafs.output.types.RealEstateType;

public class RuleTest {

	private static KnowledgeBase knowledgeBase;
	private static EstatePropertyFactory estatePropertyFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		knowledgeBase = getKnowledgeBase();
		estatePropertyFactory = EstatePropertyFactory.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSuitableForSameBuildingDistance() {
		List<UserInput> userInputs = new ArrayList<UserInput>();
		userInputs.add(DistanceFromTheNeighbors.SAME_BUILDING);
		List<UserOutput> userOutsputs = getOutput(userInputs);
		System.out.println(userOutsputs);
		assertTrue(userOutsputs.contains(PlotSize.LESS_THAN_500));
		assertTrue(userOutsputs.contains(RealEstateType.FLAT));
		assertTrue(userOutsputs.contains(RealEstateType.TERRACED));
	}

	public List<UserOutput> getOutput(List<UserInput> userInputs) {
		List<UserOutput> userOutputs = new ArrayList<UserOutput>();
		StatefulKnowledgeSession ksession = knowledgeBase
				.newStatefulKnowledgeSession();
		for (UserInput userInput : userInputs) {
			ksession.insert(userInput);
		}

		ksession.insert(userOutputs);
		ksession.fireAllRules();
		return userOutputs;
	}
	
	public static KnowledgeBase getKnowledgeBase() {
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
