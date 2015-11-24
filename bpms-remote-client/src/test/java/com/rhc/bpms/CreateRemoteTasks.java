package com.rhc.bpms;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jbpm.services.task.utils.ContentMarshallerHelper;
import org.junit.Assert;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Content;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskData;
import org.kie.api.task.model.TaskSummary;
import org.kie.remote.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.remote.client.api.RemoteRuntimeEngineFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;

/**
 * This class works like a playbook - execute methods in order. Will move this
 * to a bpm process later on
 */
public class CreateRemoteTasks {

	private String userId = "jboss";
	private String password = "bpmsuite1!";
	private String token = userId + ":" + password;
	private String applicationHttpContext = "http://209.132.179.118:32786/business-central";
	private String deploymentId = "com.rhc:bpms-knowledge:1.0.0";
	private String tradeProcess = "com.rhc.TradeProcess";
	private String systemProcess = "com.rhc.SystemProcess";
	private RemoteRestRuntimeEngineFactory remoteRestSessionFactory;

	/**
	 * Deploy the kjar
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void deployKJar() throws UnsupportedEncodingException {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(applicationHttpContext + "/rest/deployment/" + deploymentId + "/deploy");
		Response response = target.request().header("Authorization", "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"))).post(null);
		Assert.assertEquals(202, response.getStatus());
	}

	/**
	 * Run this in groups of 5 in parallel, twice. This should give you 5000
	 * processes
	 */
	@Test
	public void createTradeTasks() {
		populateSamples(500, 40, tradeProcess);
	}
	
	/**
	 * This test will verify you have the right amount of tasks
	 */
	@Test
	public void listTaskSize() {
		TaskService taskService = getRuntimeEngine().getTaskService();
		Assert.assertEquals(5000, taskService.getTasksAssignedAsPotentialOwner("jboss", null).size());
	}

	// The state all the tasks should be in
	// 1 - 1000 - C
	@Test
	public void claimSomeTasksTest1() {
		claimSomeTasks(500, 1);
	}

	@Test
	public void claimSomeTasksTest2() {
		claimSomeTasks(500, 501);
	}

	// 1001 - 2000 - C
	@Test
	public void claimSomeTasksTest3() {
		claimSomeTasks(500, 1001);
	}

	@Test
	public void claimSomeTasksTest4() {
		claimSomeTasks(500, 1501);
	}

	// 2001 - 3000 - C
	@Test
	public void claimSomeTasksTest5() {
		claimSomeTasks(500, 2001);
	}

	@Test
	public void claimSomeTasksTest6() {
		claimSomeTasks(500, 2501);
	}

	// 3001 - 4000 - C
	@Test
	public void claimSomeTasksTest7() {
		claimSomeTasks(500, 3001);
	}

	@Test
	public void claimSomeTasksTest8() {
		claimSomeTasks(500, 3501);
	}
	// 4001 - 5000 - 1
	// nothing to do

	// 5001 - 6000 - C
	@Test
	public void claimSomeTasksTest9() {
		claimSomeTasks(500, 5001);
	}

	@Test
	public void claimSomeTasksTest10() {
		claimSomeTasks(500, 5501);
	}

	// 6001 - 7000 - C
	@Test
	public void claimSomeTasksTest11() {
		claimSomeTasks(500, 6001);
	}

	@Test
	public void claimSomeTasksTest12() {
		claimSomeTasks(500, 6501);
	}

	// 7001 - 8000 - C
	@Test
	public void claimSomeTasksTest13() {
		claimSomeTasks(500, 7001);
	}

	@Test
	public void claimSomeTasksTest14() {
		claimSomeTasks(500, 7501);
	}

	// 8001 - 9000 - 2
	// nothing to do

	// 9001 - 10000 - C
	@Test
	public void claimSomeTasksTest15() {
		claimSomeTasks(500, 9001);
	}

	@Test
	public void claimSomeTasksTest16() {
		claimSomeTasks(500, 9501);
	}

	// 10001 - 11000 - C
	@Test
	public void claimSomeTasksTest17() {
		claimSomeTasks(500, 10001);
	}

	@Test
	public void claimSomeTasksTest18() {
		claimSomeTasks(500, 10501);
	}

	// 11001 - 12000 - 3
	// nothing to do

	// 12001 - 13000 - C
	@Test
	public void claimSomeTasksTest19() {
		claimSomeTasks(500, 12001);
	}

	@Test
	public void claimSomeTasksTest20() {
		claimSomeTasks(500, 12501);
	}

	// 13001 - 14000 - 4
	// nothing to do

	// 14001 - 15000 - 5
	// nothing to do

	@Test
	public void claimSomeTasksTest() {
		claimSomeTasks(500, 1001);
	}
	
	/**
	 * Now let's create some system tasks
	 */
	@Test
	public void createSystemTasks() {
		populateSamples(200, 40, systemProcess);
	}

	public void claimSomeTasks(int numberOfTasks, int baseTaskId) {
		TaskService taskService = getRuntimeEngine().getTaskService();

		for (int i = 0; i < numberOfTasks; i++) {
			int derivedId = i + baseTaskId;
			taskService.claim(derivedId, "jboss");
			taskService.start(derivedId, "jboss");
			taskService.complete(derivedId, "jboss", null);
		}
	}

	@Test
	public void getContentForTasks() {
		TaskService taskService = getRuntimeEngine().getTaskService();

		for (TaskSummary summary : taskService.getTasksAssignedAsPotentialOwner("jboss", null)) {
			Task task = taskService.getTaskById(summary.getId());
			Content c = taskService.getContentById(task.getTaskData().getDocumentContentId());
			HashMap<String, Object> d = (HashMap<String, Object>) ContentMarshallerHelper.unmarshall(c.getContent(), null);
			System.out.println(d);
		}
	}

	@Test
	public void claimAllTasks() {
		TaskService taskService = getRuntimeEngine().getTaskService();
		for (TaskSummary summary : taskService.getTasksAssignedAsPotentialOwner("jboss", null)) {
			System.out.println(summary.getId());
			taskService.claim(summary.getId(), "jboss");
			taskService.start(summary.getId(), "jboss");
			taskService.complete(summary.getId(), "jboss", new HashMap<String, Object>());
		}
	}

	public void populateSamples(int numTask, int numProcessVars, String processId) {

		RuntimeEngine runtimeEngine = getRuntimeEngine();

		KieSession kieSession = runtimeEngine.getKieSession();
		Map<String, Object> processVariables;

		// Name, age, email, income, amount, period.
		for (int i = 0; i < numTask; i++) {
			// 1 2 3
			processVariables = getProcessArgs(numProcessVars);
			kieSession.startProcess(processId, processVariables);
		}

		// getTasks();
	}

	private RuntimeEngine getRuntimeEngine() {
		try {
			if (remoteRestSessionFactory == null) {
				URL jbpmURL = new URL(applicationHttpContext);
				remoteRestSessionFactory = RemoteRuntimeEngineFactory.newRestBuilder().addDeploymentId(deploymentId).addUrl(jbpmURL).addUserName(userId).addPassword(password)
						.buildFactory();
			}
			RuntimeEngine runtimeEngine = remoteRestSessionFactory.newRuntimeEngine();
			return runtimeEngine;
		} catch (MalformedURLException e) {
			throw new IllegalStateException("This URL is always expected to be valid!", e);
		}
	}

	private Map<String, Object> getProcessArgs(int numProcessVars) {
		Map<String, Object> processVariables = new HashMap<String, Object>();
		processVariables.put("processVar1", "this is a name");
		processVariables.put("processVar2", new Float(1.23));
		processVariables.put("processVar3", new Integer(4));

		for (int i = 4; i < numProcessVars; i++) {
			String varName = "processVar" + Integer.toString(i);
			processVariables.put(varName, varName);
		}

		return processVariables;
	}

	private void getTasks() {
		RemoteRuntimeEngine engine = remoteRestSessionFactory.newRuntimeEngine();
		// The TaskService class allows we to access the server tasks
		TaskService taskService = engine.getTaskService();
		String USER = "erics";
		List<TaskSummary> tasks = taskService.getTasksAssignedAsPotentialOwner(USER, "en-US");
		if (tasks.size() == 0) {
			System.out.printf("No tasks for user \"%s\" as owner...\n", USER);
		} else {
			System.out.printf("Tasks where user \"%s\" is a an owner...\n", USER);
			for (TaskSummary t : tasks) {
				System.out.printf("ID: %d\n", t.getId());
				System.out.printf("Name: %s\n", t.getName());
				System.out.printf("Actual Owner: %s\n", t.getActualOwner());
				System.out.printf("Created by: %s\n", t.getCreatedBy());
				System.out.printf("Created on: %s\n", t.getCreatedOn());
				System.out.printf("Status: %s\n", t.getStatus());
				System.out.printf("Description: %s\n", t.getDescription());
				// LoanApplication la = (LoanApplication)t.getClass();
				System.out.println("---------------");
				Task userTask = taskService.getTaskById(t.getId());
				TaskData taskData = userTask.getTaskData();

				Map<String, Object> m = taskService.getTaskContent(t.getId());

				StringBuilder sb = new StringBuilder();
				Iterator<Entry<String, Object>> iter = m.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, Object> entry = iter.next();
					sb.append(entry.getKey());
					sb.append('=').append('"');
					sb.append(entry.getValue());
					sb.append('"');
					if (iter.hasNext()) {
						sb.append(',').append(' ');
					}
				}
				System.out.println(sb);
			}
		}

	}
}
