package com.researchspace.zmq.acceptance;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.researchspace.zmq.ZMQConnector;
import com.researchspace.zmq.snapgene.internalrequests.ExportDnaFileRequest;
import com.researchspace.zmq.snapgene.internalrequests.GenerateSVGMapRequest;
import com.researchspace.zmq.snapgene.internalrequests.ImportDnaFileRequest;
import com.researchspace.zmq.snapgene.internalrequests.ReportEnzymesRequest;
import com.researchspace.zmq.snapgene.internalrequests.ReportORFsRequest;
import com.researchspace.zmq.snapgene.requests.EnzymeSet;
import com.researchspace.zmq.snapgene.requests.ExportDnaFileConfig;
import com.researchspace.zmq.snapgene.requests.ExportFilter;
import com.researchspace.zmq.snapgene.requests.GenerateSVGMapConfig;
import com.researchspace.zmq.snapgene.requests.ImportDnaFileConfig;
import com.researchspace.zmq.snapgene.requests.ReadingFrame;
import com.researchspace.zmq.snapgene.requests.ReportEnzymesConfig;
import com.researchspace.zmq.snapgene.requests.ReportFeatures;
import com.researchspace.zmq.snapgene.requests.ReportORFsConfig;
import com.researchspace.zmq.snapgene.requests.StatusRequest;

/*
 * These assume access to a Snapgene daemon server. Also the file paths are those of the Snapgene server.
 
 */
@SpringJUnitConfig(classes = SpringConfig.class)
class ZMQSnapgeneITTest {

	private @Autowired ZMQConnector connector;

	@BeforeEach
	void before() {

	}

	private static final String RESOURCE_DIR = "/opt/gslbiotech/snapgene-server/resources/";
	private static final String VALID_PATH_TO_SNAPGENE_DNA_FILE = RESOURCE_DIR + "pIB2-SEC13-mEGFP.dna";
	private static final String VALID_PATH_TO_GENBANK_FILE = RESOURCE_DIR + "alpha-2-macroglobulin.gb";

	@Test
	void status() throws URISyntaxException {
		String resp = connector.performRequest(new StatusRequest());
		assertThat(resp, hasJsonPath(".serverIndex"));
	}

	@Test
	void features() throws URISyntaxException {
		String resp = connector.performRequest(new ReportFeatures(VALID_PATH_TO_SNAPGENE_DNA_FILE));
		assertResponseCodeIsSuccess(resp);
		assertThat(resp, hasJsonPath("$.features", hasSize(greaterThan(0))));
	}

	@ParameterizedTest()
	@EnumSource(EnzymeSet.class)
	void enzymes(EnzymeSet enzymeSet) throws URISyntaxException {
		String resp = connector.performRequest(
				new ReportEnzymesRequest(VALID_PATH_TO_SNAPGENE_DNA_FILE, new ReportEnzymesConfig(enzymeSet)));
		assertResponseCodeIsSuccess(resp);
		assertThat(resp, hasJsonPath("$.enzymes"));
		assertThat(resp, hasJsonPath("$.enzymes", hasSize(greaterThan(0))));
	}

	@ParameterizedTest()
	@EnumSource(ReadingFrame.class)
	void orfs(ReadingFrame readingFrame) throws URISyntaxException {
		String resp = connector.performRequest(
				new ReportORFsRequest(VALID_PATH_TO_SNAPGENE_DNA_FILE, new ReportORFsConfig(readingFrame)));
		assertResponseCodeIsSuccess(resp);
		assertThat(resp, hasJsonPath("$.ORFs"));
		assertThat(resp, hasJsonPath("$.ORFs", hasSize(greaterThan(0))));
	}

	/*
		this test had previously failed when the file alpha-2-macroglobulin.gb wasn't present in the snapgene server
		docker container. we had to manually add the file (from src/test/resources) to the docker container running
		on howler.
	*/
	@Test
	void importDnaFile() throws URISyntaxException {
		String outputFileName = generateOutputDnaFileNameFromInputName(VALID_PATH_TO_GENBANK_FILE);
		ImportDnaFileRequest importRequest = createImportDnaFileRequest(outputFileName);

		String resp = connector.performRequest(importRequest);
		assertResponseCodeIsSuccess(resp);
		// now do something with the generated file
		String resp2 = connector.performRequest(new ReportORFsRequest(outputFileName));
		assertResponseCodeIsSuccess(resp2);
	}

	private ImportDnaFileRequest createImportDnaFileRequest(String outputFileName) {
		ImportDnaFileConfig importConfig = ImportDnaFileConfig.builder().minORFLen(100).enzymeSet(EnzymeSet.SIX_PLUS)
				.build();
		ImportDnaFileRequest importRequest = new ImportDnaFileRequest(VALID_PATH_TO_GENBANK_FILE, outputFileName,
				importConfig);
		return importRequest;
	}

	// various combinations of arguments
	@ParameterizedTest(name = "{index}: linear:{0}, showPrimers:{1}, rf:{2}, enzymes:{3}")
	@MethodSource
	void exportToSvg(boolean linear, boolean showPrimers, ReadingFrame rf, EnzymeSet set) throws URISyntaxException {
		String outputFileName = generateOutputFileNameFromInputName(VALID_PATH_TO_SNAPGENE_DNA_FILE, "svg");
		GenerateSVGMapRequest request = createSvgRequest(outputFileName);
		String resp = connector.performRequest(request);
		assertResponseCodeIsSuccess(resp);
	}

	private GenerateSVGMapRequest createSvgRequest(String outputFileName) {
		GenerateSVGMapConfig importConfig = GenerateSVGMapConfig.builder().minORFLen(100).enzymeSet(EnzymeSet.SIX_PLUS)
				.build();
		GenerateSVGMapRequest request = new GenerateSVGMapRequest(VALID_PATH_TO_SNAPGENE_DNA_FILE, outputFileName,
				importConfig);
		return request;
	}

	private static Stream<Arguments> exportToSvg() {
		return Stream.of(Arguments.of(true, true, ReadingFrame.FIRST_FORWARD_FRAME, EnzymeSet.UNIQUE_AND_DUAL),
				Arguments.of(true, false, ReadingFrame.NO_FRAMES, EnzymeSet.UNIQUE_SIX_PLUS),
				Arguments.of(false, true, ReadingFrame.FIRST_FORWARD_FRAME, EnzymeSet.UNIQUE_AND_DUAL),
				Arguments.of(false, false, ReadingFrame.FIRST_FORWARD_FRAME, EnzymeSet.UNIQUE_AND_DUAL));
	}

	@ParameterizedTest(name = "{index} Exporting in {0} format to {1}")
	@MethodSource
	void exportDnaFile(ExportFilter exportFilter, String validOutputFileName) throws URISyntaxException {
		ExportDnaFileRequest exportRequest = createExportDnaFileRequest(exportFilter, validOutputFileName);
		String resp = connector.performRequest(exportRequest);
		assertResponseCodeIsSuccess(resp);

	}

	private ExportDnaFileRequest createExportDnaFileRequest(ExportFilter exportFilter, String validOutputFileName) {
		ExportDnaFileConfig importRequest = new ExportDnaFileConfig(exportFilter);
		ExportDnaFileRequest exportRequest = new ExportDnaFileRequest(VALID_PATH_TO_SNAPGENE_DNA_FILE,
				validOutputFileName, importRequest);
		return exportRequest;
	}

	private static Stream<Arguments> exportDnaFile() {
		return Stream.of(
				Arguments.of(ExportFilter.FASTA,
						generateOutputFileNameFromInputName(VALID_PATH_TO_SNAPGENE_DNA_FILE, "fasta")),
				Arguments.of(ExportFilter.GENBANK_SNAPGENE,
						generateOutputFileNameFromInputName(VALID_PATH_TO_SNAPGENE_DNA_FILE, "gb")),
				Arguments.of(ExportFilter.GENBANK_STANDARD,
						generateOutputFileNameFromInputName(VALID_PATH_TO_SNAPGENE_DNA_FILE, "gb")),
				Arguments.of(ExportFilter.TEXT_FILE,
						generateOutputFileNameFromInputName(VALID_PATH_TO_SNAPGENE_DNA_FILE, "txt"))
		// Arguments.of(ExportFilter.SNAPGENE,
		// generateOutputFileNameFromInputName(VALID_PATH_TO_GENBANK_FILE,"dna"))
		);
	}

	private void assertResponseCodeIsSuccess(String resp) {
		assertThat(resp, hasJsonPath("$.code", equalTo(0)));
	}

	// returns writable path on snapgene server when converting
	private String generateOutputDnaFileNameFromInputName(String inputFileName) {
		return generateOutputFileNameFromInputName(inputFileName, "dna");
	}

	// returns writable path on snapgene server when converting
	private static String generateOutputFileNameFromInputName(String inputFileName, String suffix) {
		String outputRandom = RandomStringUtils.randomAlphabetic(5);
		return "/tmp/" + FilenameUtils.getBaseName(inputFileName) + "-" + outputRandom + "." + suffix;
	}

}
