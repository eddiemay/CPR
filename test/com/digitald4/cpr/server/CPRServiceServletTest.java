package com.digitald4.cpr.server;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import com.digitald4.common.proto.DD4UIProtos.DateRangeType;
import com.digitald4.cpr.proto.CPRProtos.Trainning;
import com.digitald4.cpr.ui.proto.CPRUIProtos.ListSessionsRequest;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

public class CPRServiceServletTest {

  HttpServletRequest httpRequest = mock(HttpServletRequest.class);

	@Test
	public void testTransform() {
		when(httpRequest.getRequestURI()).thenReturn("http://localhost:8080/sessions");
		Map<String, String[]> paramMap = new HashMap<>();
		paramMap.put("date_range", new String[]{"" + DateRangeType.WEEK.getNumber()});
		paramMap.put("ref_date", new String[]{"1000"});
		when(httpRequest.getParameterMap()).thenReturn(paramMap);
		ListSessionsRequest request = CPRJSONServiceServlet.transformRequest(
				ListSessionsRequest.getDefaultInstance(), httpRequest);
		
		assertEquals(DateRangeType.WEEK, request.getDateRange());
		assertEquals(1000, request.getRefDate());

		Map<String, String[]> paramMap2 = new HashMap<>();
		paramMap2.put("date_range", new String[]{"MONTH"});
		paramMap2.put("ref_date", new String[]{"2016-04-15"});
		when(httpRequest.getParameterMap()).thenReturn(paramMap2);
		request = CPRJSONServiceServlet.transformRequest(
				ListSessionsRequest.getDefaultInstance(), httpRequest);
		
		assertEquals(DateRangeType.MONTH, request.getDateRange());
		assertEquals(DateTime.parse("2016-04-15").getMillis(), request.getRefDate());

		Map<String, String[]> paramMap3 = new HashMap<>();
		paramMap3.put("date_range", new String[]{"1"});
		paramMap3.put("ref_date", new String[]{"2016-05-01"});
		paramMap3.put("trainning_id", new String[]{"1"});
		when(httpRequest.getParameterMap()).thenReturn(paramMap3);
		request = CPRJSONServiceServlet.transformRequest(
				ListSessionsRequest.getDefaultInstance(), httpRequest);
		
		assertEquals(DateRangeType.DAY, request.getDateRange());
		assertEquals(DateTime.parse("2016-05-01").getMillis(), request.getRefDate());
		assertEquals(1, request.getTrainningId());
	}
	
	@Test
	public void testConvertToJSON() throws Exception {
		CPRJSONServiceServlet servlet = new CPRJSONServiceServlet();
		JSONObject json = servlet.convertToJSON(Trainning.newBuilder()
				.setName("Test")
				.setDescription("This is a test, this is only a test")
				.setDurationMins(60)
				.setCost(3.5)
				.build());
		assertEquals("Test", json.get("name"));
		assertEquals(60, json.get("duration_mins"));
		assertEquals(3.5, json.get("cost"));
	}
}
