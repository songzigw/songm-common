package cn.songm.common.utils;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RunWith(JUnit4.class)
public class JsonUtilsTest {

	public static class Student {
		private String name;
		private Date created;
		private Operation opn;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getCreated() {
			return created;
		}

		public void setCreated(Date created) {
			this.created = created;
		}

		public Operation getOpn() {
			return opn;
		}

		public void setOpn(Operation opn) {
			this.opn = opn;
		}

		@Override
		public String toString() {
			return "Student [name=" + name + ", created=" + created + ", opn=" + opn + "]";
		}
	}

	public static enum Operation implements GsonEnum<Operation> {
		AAA(1), BBB(2), CCC(3);

		private final int value;

		private Operation(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public static Operation getInstance(int v) {
			for (Operation op : Operation.values()) {
				if (op.getValue() == v) {
					return op;
				}
			}
			return null;
		}

		@Override
		public String serialize() {
			return this.getValue() + "";
		}

		@Override
		public Operation deserialize(String jsonEnum) {
			return getInstance(Integer.parseInt(jsonEnum));
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Date.class, new GsonDateTypeAdapter()).registerTypeAdapter(Operation.class,
				new GsonEnumTypeAdapter<>(Operation.AAA));
		JsonUtils.init(builder);
	}

	@AfterClass
	public static void tearDownAfterClass() {

	}

	private JsonUtils jsonUtils;

	@Before
	public void setUp() {
		jsonUtils = JsonUtils.getInstance();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testInit() {
		// 1.先获取实例，不抛出异常
		JsonUtils.getInstance();
		// 2.再运行init说明走的是if分支
		JsonUtils.init(new GsonBuilder());
	}
	
	@Test
	public void testToJson() {
		Student stu = new Student();
		stu.setCreated(new Date());
		stu.setName("zhangsong");
		stu.setOpn(Operation.AAA);
		JsonObject jObj = new JsonParser().parse(jsonUtils.toJson(stu)).getAsJsonObject();
		Assert.assertEquals(stu.getName(), jObj.get("name").getAsString());
		Assert.assertEquals(stu.getOpn().getValue(), jObj.get("opn").getAsInt());
		Assert.assertEquals(stu.getCreated().getTime(), jObj.get("created").getAsLong());
	}

	@Test
	public void testFromJson() {
		JsonObject jObj = new JsonParser().parse("{}").getAsJsonObject();
		jObj.addProperty("name", "zhangsong");
		jObj.addProperty("created", 1496649943522l);
		jObj.addProperty("opn", 1);
		Student stu = jsonUtils.fromJson(jObj.toString(), Student.class);
		Assert.assertEquals(stu.getName(), jObj.get("name").getAsString());
		Assert.assertEquals(stu.getOpn().getValue(), jObj.get("opn").getAsInt());
		Assert.assertEquals(stu.getCreated().getTime(), jObj.get("created").getAsLong());
	}

}
