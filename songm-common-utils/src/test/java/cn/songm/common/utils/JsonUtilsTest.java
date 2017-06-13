package cn.songm.common.utils;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

@RunWith(JUnit4.class)
public class JsonUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtilsTest.class);

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
            return "Student [name=" + name + ", created=" + created + ", opn="
                    + opn + "]";
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
        builder.registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(Operation.class,
                        new GsonEnumTypeAdapter<>(Operation.AAA));
        JsonUtils.init(builder);
    }

    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void toJson() {
        Student stu = new Student();
        stu.setCreated(new Date());
        stu.setName("zhangsong");
        stu.setOpn(Operation.AAA);
        LOG.info(JsonUtils.getInstance().toJson(stu));
    }

    @Test
    public void fromJson() {
        // JsonObject jObj = new JsonParser().parse("{}").getAsJsonObject();
        // jObj.addProperty("name", "zhangsong");
        // jObj.addProperty("created", 1496649943522l);
        // LOG.info(jObj.toString());
        String str = "{\"name\":\"zhangsong\",\"created\":1496649943522,\"opn\":1}";
        Student stu = JsonUtils.getInstance().fromJson(str, Student.class);
        LOG.info(stu.toString());
    }
    
}
