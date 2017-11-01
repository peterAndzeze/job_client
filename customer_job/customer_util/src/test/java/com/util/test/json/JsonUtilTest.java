package com.util.test.json;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.customer.json.JsonUtil;

/**
 * 
 * @Title: JsonUtilTest.java
 * @Package: com.util.test.json
 * @Description: json测试类
 * @author: sunwei
 * @date: 2017年5月20日 下午1:30:33
 * @version: V1.0
 */
public class JsonUtilTest {
    @Test
    public void objectToJson(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	System.out.println(JsonUtil.objectToJson(p));
    }
    
    @Test
    public void objectToJsonObject(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	System.out.println(JsonUtil.objectToJSONObject(p));
    }
    
    @Test
    public void listToJson(){
	Person p=null;
	List<Person> persons=new ArrayList<Person>();
	for (int i = 0; i < 2; i++) {
	    p=new Person();
		p.setAge(i);
		p.setBirthDate(new Date());
		p.setName("章三"+i);
	    persons.add(p);
	}
	System.out.println(JsonUtil.objectToJson(persons));
	System.out.println(JsonUtil.objectToJSONOArr(persons));
    }
    
    @Test
    public void removeJsonProperty(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	System.out.println(JsonUtil.objectToJsonExcludeProperty(p,"name","age"));
    }
    @Test
    public void jsonStrToObject(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	String str=JsonUtil.objectToJson(p);
	Person p2=(Person)JsonUtil.jsonToObject(str,Person.class);
	System.out.println(p2.getBirthDate()+":::"+p2.getName());
    }
    
    @Test
    public void jsonStrToList(){
	Person p=null;
	List<Person> persons=new ArrayList<Person>();
	for (int i = 0; i < 2; i++) {
	    p=new Person();
		p.setAge(i);
		p.setBirthDate(new Date());
		p.setName("章三"+i);
	    persons.add(p);
	}
	String str=JsonUtil.objectToJson(persons);
	List<Person> ps=(List<Person>)JsonUtil.jsonToList(str,Person.class);
	System.out.println(ps.get(0).getBirthDate()+":::"+ps.get(1).getName());
    }
    @Test
    public void mapToJson(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	Map<String, Person> maps=new HashMap<String, Person>();
	maps.put("11", p);
	System.out.println(JsonUtil.objectToJson(maps));
    }
    @Test
    public void jsonToMap(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	Map<String, Person> maps=new HashMap<String, Person>();
	maps.put("11", p);
	String str=JsonUtil.objectToJson(maps);
	Map<String, Person> map=(Map<String, Person>)JsonUtil.jsonToMapObject(str, Map.class,Person.class);
	for(Map.Entry<String, Person> entry:map.entrySet()){
	    System.out.println(entry.getKey());
	    Person p1=entry.getValue();
	    System.out.println(p1.getName());
	}
    }
    
    @Test
    public void jsonToMapList(){
	Person p=new Person();
	p.setAge(12);
	p.setBirthDate(new Date());
	p.setName("章三");
	List<Person> persons=new ArrayList<Person>();
	persons.add(p);
	Map<String, List<Person>> maps=new HashMap<String, List<Person>>();
	maps.put("persons", persons);
	String str=JsonUtil.objectToJson(maps);
	Map<String, List<Person>> map=(Map<String, List<Person>>)JsonUtil.jsonToMapList(str, Map.class,Person.class);
	for(Map.Entry<String, List<Person>> entry:map.entrySet()){
	    System.out.println(entry.getKey());
	    List<Person> p1=entry.getValue();
	    System.out.println(p1.get(0).getName());
	}
    }
    
}
